package DelayQueueTest;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class KTVConsumer implements Delayed {
    // 客户名
    private String name;
    // 截止时间
    private long endTime;
    // 包厢号
    private String boxNum;

    public KTVConsumer(String name, long endTime, String boxNum) {
        this.name = name;
        this.endTime = endTime;
        this.boxNum = boxNum;
    }

    public String getName() {
        return name;
    }

    public String getBoxNum() {
        return boxNum;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(endTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        KTVConsumer ktvConsumer = (KTVConsumer) o;
        return endTime < ktvConsumer.endTime ? -1 : (endTime == ktvConsumer.endTime ? 0 : 1);
    }
}
