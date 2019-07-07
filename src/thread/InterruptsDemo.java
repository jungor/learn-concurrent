package thread;

/**
 * interrupt提示该线程需要停止它当前做的事情。很多方法比如sleep()， join()，检测到有interrupt的时候，会抛出InterruptedException
 */
public class InterruptsDemo {
    public static void main(String[] args) {
        String[] importantInfo = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };

        for (String s : importantInfo) {
            //Pause for 4 seconds
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                // 被中断了，不再输入信息，直接返回
                return;
            }
            //Print a message
            System.out.println(s);
        }
    }
}
