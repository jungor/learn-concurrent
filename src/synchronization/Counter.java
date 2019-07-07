package synchronization;

/**
 * 线程干扰
 *
 * 若两个线程并发调用Counter的方法，会出现线程干扰(Thread Interference)现象。
 * 比如线程A在increment()的执行步骤中，线程B同时执行decrement()，
 * 并且步骤相互交错，比如：
 *   Thread A: Retrieve c.
 *   Thread B: Retrieve c.
 *   Thread A: Increment retrieved value; result is 1.
 *   Thread B: Decrement retrieved value; result is -1.
 *   Thread A: Store result in c; c is now 1.
 *   Thread B: Store result in c; c is now -1.
 * 线程A的计算结果被B的结果覆盖率。现实情况可能更复杂，因为以上的三步骤在JVM底层可能是更多的步骤，而且交错的情况也有很多种
 */
public class Counter {
    private int c = 0;

    private void increment() {
        c++;
    }

    private void decrement() {
        c--;
    }

    public int value() {
        return c;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
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
        // 由于线程干扰，不一定能得等预期的结果0
        System.out.println(counter.c);
    }
}
