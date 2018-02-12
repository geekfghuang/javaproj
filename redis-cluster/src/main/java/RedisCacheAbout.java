public class RedisCacheAbout {
    /**
     * 缓存穿透优化
     */
    private UserDAO userDAO;
    private LocalCache localCache;

    // 普通缓存代码：可能会有缓存穿透风险
    public User getUser(String userNick) {
        User user = (User)localCache.get(userNick);
        if (user == null) {
            user = userDAO.getUser(userNick);
            if (user != null) {
                localCache.put(userNick,user);
            }
        }
        return user;
    }

    // 缓存穿透优化示例伪代码
    private static class NullValueResult implements Serializable {
        private static final long serialVersionUID = -6550539547145486005L;
    }
    public User getUser(String userNick) {
        Object object = localCache.get(userNick);
        if (object != null) {
            if (object instanceof NullValueResult) {
                return null;
            }
            return (User)object;
        } else {
            User user = userDAO.getUser(userNick);
            if(user != null) {
                localCache.put(userNick,user);
            } else {
                localCache.put(userNick, new NullValueResult());
                // 如果存储数据为空，需要设置一个过期时间（300秒）
                localCache.expire(userNick, 60 * 5);
            }
            return user;
        }
    }


    /**
     * 热点Key重建优化
     */
    public String get(String key) {
        int retry = 5;
        for (int i = 0; i < retry; i++) {
            String value = redis.get(key);
            if (value == null) {
                String mutexKey = "mutex:key:" + key;
                if (redis.set(mutexKey, "1", "ex 180", "nx")) {// 原子操作
                    value = db.get(key);
                    redis.set(key, value);
                    redis.delete(mutexKey);
                } else {
                    // 其他线程休息50毫秒后重试
                    Thread.sleep(50);
                    continue;
                }
            }
            return value;
        }
        return null;
    }
}