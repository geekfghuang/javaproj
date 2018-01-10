// https://zhuanlan.zhihu.com/p/27857336

import java.util.concurrent.locks.LockSupport;

public class InterruptTest {
    public static void main(String[] args) {
        Thread parkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "开始LockSupport.park()");
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + "从LockSupport.park()返回");
                System.out.println(Thread.currentThread().getName() + "isInterrupted()情况：" + Thread.currentThread().isInterrupted());
                Thread.interrupted();// 会返回true or false，且清除interrupt标记
                System.out.println(Thread.currentThread().isInterrupted());
            }
        }, "parkThread");
        parkThread.start();

        try {
            Thread.sleep(3000);
            parkThread.interrupt();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread unparkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "开始LockSupport.unpark(parkThread)");
                LockSupport.unpark(parkThread);
            }
        }, "unparkThread");
        unparkThread.start();
    }
}
