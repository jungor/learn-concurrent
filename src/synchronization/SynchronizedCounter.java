package synchronization;

/**
 * 同步化方法
 *   使用synchronized，保证互斥性(消除线程间干扰)和可见性(消除内存一致性问题)
 */
public class SynchronizedCounter {
    private int c = 0;

    private synchronized void increment() {
        c++;
    }

    private synchronized void decrement() {
        c--;
    }

    public int value() {
        return c;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter.decrement();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // 一定能得等预期的结果0
        System.out.println(counter.c);
    }
}
