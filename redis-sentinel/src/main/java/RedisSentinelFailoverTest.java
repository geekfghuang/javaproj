/**
 * Redis Sentinel机制会自动维护主从复制的高可用，实现自动故障转移
 * 1. 客户端从Sentinels集合中获取一个可用的Sentinel
 * 2. 从这个可用的Sentinel中获取master的地址信息后，直连，做读写操作
 * 3. 每个Sentinel都会监控所有Redis节点的存活信息
 * 3. 当master挂掉后，Sentinels会投票选举出一个leader，这个leader负
 *    责选举一个新的master节点，并让其他slave复制这个新的master，最
 *    后告知客户端新的master已经变化
 * 4. 客户端在收到master变化消息前，会不断重试，因此会报错；当收到消
 *    息后，会对新的master进行直连。这内部其实是客户端订阅了Sentinel
 *    集合中有关master变更的消息channel
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisSentinelFailoverTest {
    private static Logger logger = LoggerFactory.getLogger(RedisSentinelFailoverTest.class);
    public static void main(String[] args) {
        String masterName = "mymaster";
        Set<String> sentinels = new HashSet<String>();
        sentinels.add("localhost:26379");
        sentinels.add("localhost:26380");
        sentinels.add("localhost:26381");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels);
        int counter = 0;
        while (true) {
            counter ++;
            Jedis jedis = null;
            try {
                jedis = jedisSentinelPool.getResource();
                int index = new Random().nextInt(100000);
                String key = "k-" + index;
                String value = "v-" + index;
                jedis.set(key, value);
                if (counter % 100 == 0) {
                    logger.info("{} value is {}", key, jedis.get(key));
                }
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }
}