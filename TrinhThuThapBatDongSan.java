import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TrinhThuThapBatDongSan {

    private static AtomicInteger soLuongBatDongSan = new AtomicInteger(0);

    public static void main(String[] args) {
        String[] danhSachTrangWeb = {
                "https://batdongsan.com.vn/nha-dat-ban-da-nang",
                "https://cafeland.vn/chu-de-nong/bat-dong-san-da-nang-257",
                "https://www.nhatot.com/mua-ban-bat-dong-san-da-nang"
        };
        int soLuongLuong = 3;
        String tenFileKetQua = "C:/Users/LENOVO/OneDrive/Documents/batdongsan.csv";

        ExecutorService boQuanLyLuong = Executors.newFixedThreadPool(soLuongLuong);

        for (String trangWeb : danhSachTrangWeb) {
            boQuanLyLuong.submit(() -> {
                ThuThapDuLieu thuThap = new ThuThapDuLieu(trangWeb);
                List<BatDongSan> danhSachBatDongSan = thuThap.thuThap();
                for (BatDongSan batDongSan : danhSachBatDongSan) {
                    TrinhGhiBatDongSan.luuVaoCsv(tenFileKetQua, batDongSan);
                    soLuongBatDongSan.incrementAndGet();
                }
            });
        }

        boQuanLyLuong.shutdown();
        try {
            boQuanLyLuong.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Hoàn tất thu thập và lưu dữ liệu. Số lượng bất động sản: " + soLuongBatDongSan.get());
    }
}