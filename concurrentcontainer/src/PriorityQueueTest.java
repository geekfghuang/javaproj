import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueTest {
    public static void main(String[] args) {
        Comparator<ITer> comparator = new Comparator<ITer>() {
            @Override
            public int compare(ITer o1, ITer o2) {
                if (o1.level < o2.level) {
                    return -1;
                } else if (o1.level == o2.level) {
                    return 0;
                } else {
                    return 1;
                }
            }
        };
        Queue<ITer> queue = new PriorityQueue<>(comparator);
        queue.add(new ITer("A", 2.3));
        queue.add(new ITer("B", 2.2));
        queue.add(new ITer("C", 4.1));
        queue.add(new ITer("D", 4.3));
        queue.add(new ITer("E", 3.2));
        queue.add(new ITer("F", 1.3));

        for (;;) {
            ITer iTer = queue.poll();
            if (iTer == null) {
                break;
            }
            System.out.println(iTer);
        }

    }
    private static class ITer {
        private String name;
        private double level;

        public ITer(String name, double level) {
            this.name = name;
            this.level = level;
        }

        @Override
        public String toString() {
            return "ITer{" +
                    "name='" + name + '\'' +
                    ", level=" + level +
                    '}';
        }
    }
}