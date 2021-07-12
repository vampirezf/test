package thread;

import java.util.concurrent.CountDownLatch;


public class Demo3 {
    public static void main(String[] args) {
        CountDownLatch c = new CountDownLatch(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                c.countDown();
                System.out.println(2);
                c.countDown();
            }
        }).start();

        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3");
    }
}
