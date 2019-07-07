package thread;

/**
 * 实现Runnable接口创建线程
 *
 * 创建线程的第一种方法（首选这种方法，更灵活）：
 *   实现Runnable接口，重写run()方法，实例化该类并把实例对象注入到一个新的Thread实例对象，调用Thread实例对象的start()方法启动线程
 */
public class HelloRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String[] args) {
        (new Thread(new HelloRunnable())).start();
    }
}
