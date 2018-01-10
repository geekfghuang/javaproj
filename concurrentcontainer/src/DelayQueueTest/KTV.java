package DelayQueueTest;

import java.util.concurrent.DelayQueue;

public class KTV implements Runnable {
    private DelayQueue<KTVConsumer> delayQueue = new DelayQueue<>();
    private void begin(String name, int money, String boxName) {
        KTVConsumer ktvConsumer = new KTVConsumer(name, System.currentTimeMillis() + money * 10, boxName);
        delayQueue.add(ktvConsumer);
    }
    private void end(KTVConsumer ktvConsumer) {
        System.out.println(ktvConsumer.getName() + "在包厢号：" + ktvConsumer.getBoxNum() + "时间到...");
    }

    @Override
    public void run() {
        while (true) {
            try {
                KTVConsumer ktvConsumer = delayQueue.take();
                end(ktvConsumer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        KTV ktv = new KTV();
        Thread ktvThread = new Thread(ktv);
        ktvThread.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ktv.begin("huangfugui", 2000, "NO1");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ktv.begin("gopher", 1000, "NO2");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ktv.begin("marcus", 500, "NO3");
            }
        }).start();
    }
}
