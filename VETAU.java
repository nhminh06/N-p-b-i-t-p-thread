import java.util.HashSet;
import java.util.Set;

public class VETAU {
    private static final int VE_MAX= 50;
    private static final Object lock = new Object();
    private Set<Integer> vedaban = new HashSet<>();

    public static void main(String[] args) {
        VETAU ve = new VETAU();
      Thread dl1 = new Thread(new hethong(ve), "đại lý 1");
      Thread dl2 = new Thread(new hethong(ve),"đại lý 2");
      dl1.start();
      dl2.start();

    }
    public void datve(int sove){
        synchronized (lock){
            if (vedaban.contains(sove)){
                System.out.println("vé " + sove + " đã bán.");
            }else{
                vedaban.add(sove);
                System.out.println("vé " + sove + " chưa được bán.");
            }
        }
    }
    static class hethong implements Runnable{
        private VETAU ve;
        public hethong(VETAU ve){
            this.ve=ve;
        }

        @Override
        public void run() {
            for (int i = 1; i<=VE_MAX; i++){
                ve.datve(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

