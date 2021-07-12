package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Demo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i=0;i<10000;i++){
            list.add(i);
        }
        int totalCount = list.size();
        int pageCount = Double.valueOf(Math.ceil(totalCount/(1000d))).intValue();
        CountDownLatch countDownLatch = new CountDownLatch(pageCount);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for(int i=0;i<pageCount;i++) {
            System.out.println("当前页数==="+(i+1));
            List<Integer> dataList;
            if((i+1)*1000 < totalCount){
                dataList = list.subList(i*1000,(i+1)*1000);
            }else{
                dataList = list.subList(i*1000,totalCount);
            }
            threadPool.execute(() -> {
                try {
                    for (Integer integer : dataList) {
                        System.out.println(integer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        System.out.println("等待子线程运行结束");
        try {
            countDownLatch.await();
            //countDownLatch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("子线程结束");
        threadPool.shutdown();
    }
}
