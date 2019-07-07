package synchronization;

import static java.lang.Thread.sleep;

/**
 * 基于JVM的内存模型（JMM），不同线程在自己的线程局部空间可能会对同一个数据有各自的缓存，
 * 一个线程对该数据的修改，别的线程有可能看不到，导致内存数据不一致错误。
 *
 * Java规范给程序语句定义了一个叫“发生在...之前”的关系，若语句A“发生在”语句B”之前“，那么A对数据的所有更新都能在B执行时可见，
 * 也就不会出现内存一致性错误
 *
 * 同时，也指出了这些语句之间有这种关系：（https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html#MemoryVisibility）
 *     1. 同一个线程里的语句，按照语句的执行顺序，前面的语句发生在后面的语句之前。（很显然）
 *     2. 内置锁的释放发生在这个内置锁的后续的获取之前。（也就是说synchronized保证了可见性）
 *     3. 对volatile类型的字段的写操作发生在后面对这个字段的后续度操作之前。（也就是说volatile保证了可见性，注意和synchronized的区别是不保证互斥性，所以可能会存在“线程间互相干扰问题”）
 *     4. 启动一个线程的语句发生在新线程的所有语句之前
 *     5. 一个线程的所有语句发生在另一个调用该线程的join()并成功返回的线程之前
 *     6. 其他...
 */
public class MemoryConsistency {
    // 保证了可见性
    private volatile int num;

    /**
     * 但仍存在线程干扰问题
     */
    private void incAndPrintHasInterference() {
        num++;
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(num);
    }
    public static void main(String[] args) {
        final MemoryConsistency memoryConsistency = new MemoryConsistency();
        Runnable runnable = () -> {
            for (int i = 0; i < 100; i++) {
                memoryConsistency.incAndPrintHasInterference();
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
