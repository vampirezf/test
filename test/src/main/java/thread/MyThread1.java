package thread;


public class MyThread1 extends Thread {
    @Override
    /** 重写Thread的run方法 */
    public void run() {
        // 使用Thread.currentThread()自带的getName()
        String name1 = Thread.currentThread().getName();
        //建立一个测试用的for循环
        for(int i = 0;i<50;i++) {
            // 输出
            System.out.println(name1+"打印"+i);
        }
    }
}
