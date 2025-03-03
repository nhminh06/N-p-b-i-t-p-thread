import java.util.concurrent.CountDownLatch;

public class HaiThread {
    public static void main(String[] args) {
        CountDownLatch cho = new CountDownLatch(2);
        Thread thread1 = new Thread(()->
        {try{
            System.out.println("thread1 dang chay...");
            Thread.sleep(2000);
            System.out.println("thread1 da chay xong...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            cho.countDown();
        }
        });
        Thread thread2 = new Thread(()->
        {try{
            System.out.println("thread2 dang chay...");
            Thread.sleep(3000);
            System.out.println("thread2 da chay xong...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            cho.countDown();
        }
        });
        thread2.start();
        thread1.start();
        try {
            cho.await();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        System.out.println("ket qua");
    }
}
