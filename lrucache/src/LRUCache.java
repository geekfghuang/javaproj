import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int MAX_SIZE;

    public LRUCache(int size) {
        super(size, 0.75f, true);
        this.MAX_SIZE = size;
    }

    public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_SIZE;
    }

    public static void main(String[] args) {
        LRUCache<String, String> lruCache = new LRUCache<String, String>(4);
        lruCache.put("k1", "s1");
        lruCache.put("k2", "s2");
        lruCache.put("k3", "s3");
        lruCache.put("k4", "s4");
        lruCache.get("k2");// 访问k2
        lruCache.put("k5", "s5");
        lruCache.put("k6", "s6");
        for (Map.Entry<String, String> entry : lruCache.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}