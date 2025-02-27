public class SOCHANSOLE {
    private static final int number_max=10;
    private static final Object lock = new Object();
    private static boolean even = true;

    public static void main(String[] args) {
        Thread Chanthread = new Thread(new chanprint());
        Thread Lethread = new Thread(new leprint());
        Chanthread.start();
        Lethread.start();

    }
    static class chanprint implements Runnable{
        @Override
        public void run() {
            for(int i=0; i<= number_max;i+=2){
                synchronized (lock){
                    while (!even){
                        try{
                            lock.wait();

                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    System.out.println("chẵn="+i);
                    even= false;
                    lock.notify();
                }

            }
        }
    }
    static class leprint implements Runnable{
        @Override
        public void run() {
            for (int i = 1; i<=number_max;i+=2){
                synchronized (lock){
                    while (even){
                        try{
                            lock.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    System.out.println("lẻ="+i);
                    even= true;
                    lock.notify();
                }
            }
        }
    }
}
