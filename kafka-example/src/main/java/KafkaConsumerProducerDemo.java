public class KafkaConsumerProducerDemo {
    public static void main(String[] args) {
        // 默认异步发送
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        Producer producerThread = new Producer(KafkaProperties.TOPIC, isAsync);
        producerThread.start();

        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();
    }
    /* 控制台输出：
     message(21, hello geekfghuang! This is No:21 message) sent to partition(0), offset(56) in 32 ms
     Received message: (21, hello geekfghuang! This is No:21 message) at offset 56
     message(22, hello geekfghuang! This is No:22 message) sent to partition(0), offset(57) in 32 ms
     Received message: (22, hello geekfghuang! This is No:22 message) at offset 57
     message(23, hello geekfghuang! This is No:23 message) sent to partition(0), offset(58) in 31 ms
     Received message: (23, hello geekfghuang! This is No:23 message) at offset 58
     message(24, hello geekfghuang! This is No:24 message) sent to partition(0), offset(59) in 31 ms
     Received message: (24, hello geekfghuang! This is No:24 message) at offset 59
     message(25, hello geekfghuang! This is No:25 message) sent to partition(0), offset(60) in 41 ms
     Received message: (25, hello geekfghuang! This is No:25 message) at offset 60
     */
}
