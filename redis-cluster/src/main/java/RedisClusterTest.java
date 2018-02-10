import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

public class RedisClusterTest {
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

        System.out.println(jedisCluster.set("hello", "geekfghuang"));
        System.out.println(jedisCluster.get("hello"));

        jedisClusterFactory.destory();
    }
}