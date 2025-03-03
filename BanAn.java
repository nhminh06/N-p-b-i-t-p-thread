import java.util.concurrent.Semaphore;

public class BanAn implements Runnable {
    private int id;
    private Semaphore doiua;
    private BanAn doiduabencanh;

    public BanAn(int id, Semaphore doiua) {
        this.id = id;
        this.doiua = doiua;
    }

    public void setDoiduabencanh(BanAn doiduabencanh) {
        this.doiduabencanh = doiduabencanh;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 3; i++) {
                nghi();
                an();
                chuyendua();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void nghi() throws InterruptedException {
        System.out.println("Người thứ " + (id+1) + " đang nghĩ");
        Thread.sleep(1000);
    }

    public void an() throws InterruptedException {
        doiua.acquire();
        System.out.println("Người thứ " + (id+1) + " đang ăn");
        Thread.sleep(2000);
        doiua.release();
    }

    public void chuyendua() {
        System.out.println("Người thứ " + (id+1) + " đang chuyển đũa");
        doiduabencanh.doiua.release();
    }
}


class AnUong {
    public static void main(String[] args) {
        int sodua = 5;
        Semaphore[] sochiecdua = new Semaphore[sodua];

        for (int i = 0; i < sodua; i++) {
            sochiecdua[i] = new Semaphore(1);
        }

        BanAn[] Nguoi = new BanAn[sodua];
        Thread[] threads = new Thread[sodua];

        for (int i = 0; i < sodua; i++) {
            Nguoi[i] = new BanAn(i, sochiecdua[i]);
        }

        for (int i = 0; i < sodua; i++) {
            Nguoi[i].setDoiduabencanh(Nguoi[(i + 1) % sodua]);
        }

        for (int i = 0; i < sodua; i++) {
            threads[i] = new Thread(Nguoi[i]);
            threads[i].start();
        }

        for (int i = 0; i < sodua; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
