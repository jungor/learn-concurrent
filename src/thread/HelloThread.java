package thread;

/**
 * 继承Thread类创建线程
 *
 * 创建线程的第二种方法：
 *   继承Thread类，重写run()方法，实例化该子类，调用实例对象的start()方法启动线程
 */
public class HelloThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String[] args) {
        (new HelloThread()).start();
    }
}
