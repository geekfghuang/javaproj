import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisClusterTest {
    private static Logger logger = LoggerFactory.getLogger(RedisClusterTest.class);

    public static void main(String[] args) {
        List<String> hostPortList = new ArrayList<String>();
        hostPortList.add("127.0.0.1:7000");
        hostPortList.add("127.0.0.1:7001");
        hostPortList.add("127.0.0.1:7002");
        hostPortList.add("127.0.0.1:7003");
        hostPortList.add("127.0.0.1:7004");
        hostPortList.add("127.0.0.1:7005");

        JedisClusterFactory jedisClusterFactory = new JedisClusterFactory(hostPortList, 3000);

        JedisCluster jedisCluster = jedisClusterFactory.getJedisCluster();

//        System.out.println(jedisCluster.set("hello", "geekfghuang"));
//        System.out.println(jedisCluster.get("hello"));

        // 集群自动故障转移演练
        int i = 0;
        while (i < Integer.MAX_VALUE) {
            try {
                String key = "key-" + i;
                String value = "value-" + i;
                jedisCluster.set(key, value);
                logger.info(jedisCluster.get(key));
                TimeUnit.MILLISECONDS.sleep(1000);
                i += 1;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        jedisClusterFactory.destory();
    }
}