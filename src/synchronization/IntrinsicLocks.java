package synchronization;

import static java.lang.Thread.sleep;

/**
 * 内置锁与同步
 *     Java的同步机制是基于对象的内置锁（又叫监视器）的。内置锁在同步的两个方面都起到作用： 1. 互斥性 2. 可见性
 *
 *     一个线程若需要互斥并内存一致地访问一个对象，就需要在访问之前“申请”这个对象的内置锁，访问完后“释放”这个对象的内置锁，
 *     线程在申请和释放一个对象的内置锁的期间，该线程就说是“拥有”该对象的内置锁。
 *
 *     当一个线程调用一个同步方法，它就会自动去申请该方法所属对象的内置锁，并且在方法返回或者抛出异常后释放该锁。
 *     若同步方法是静态方法，那么它对于的内置锁是该方法所属的类的类对象的内置锁。
 *
 *     同步语句块允许程序员手动指定申请哪个对象的内置锁。这可以细化锁的粒度，提高并发性。
 *
 *     内置锁是可重入锁。也就是说，一个锁可以申请一个它已经拥有的锁，比如一个同步方法里可以调用同一对象的另一个同步方法。
 *     可重入锁使得同步代码不用采取额外的预防措施来避免自己被阻塞
 */
public class IntrinsicLocks {
}


/**
 * 同步语句块允许程序员手动指定申请哪个对象的内置锁。这可以细化锁的粒度，提高并发性。
 * c1和c2不会同时被使用，就可以使用下面的范式提高并发性。
 * 使用这个范式一定要很小心，一定要保证c1和c2不会同时被使用，否则会有出现死锁的可能。
 */
class MsLunch {
    private long c1 = 0;
    private long c2 = 0;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    private void inc1() {
        synchronized(lock1) {
            c1++;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": " + c1);
        }
    }

    private void inc2() {
        synchronized(lock2) {
            c2++;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": " + c2);
        }
    }

    public static void main(String[] args) {
        final MsLunch msLunch = new MsLunch();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                msLunch.inc1();
            }
        }, "t1").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                msLunch.inc2();
            }
        }, "t2").start();
    }
}