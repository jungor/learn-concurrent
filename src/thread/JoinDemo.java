package thread;

/**
 * 使用join()方法，可以使调用线程等待被调用线程执行完成
 */
public class JoinDemo {
    public static void main(String[] args) {
        System.out.println("main thread begin");
        Runnable r1 = () -> {
            System.out.println("t1 begin");
            System.out.println("t1 start sleeping...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1 wake up");
            System.out.println("t1 end");
        };
        System.out.println("main kit off t1");
        Thread t1= new Thread(r1);
        t1.start();
        System.out.println("main wating for t1 finish...");
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main continue because t1 finish");
        System.out.println("main end");
    }
}
