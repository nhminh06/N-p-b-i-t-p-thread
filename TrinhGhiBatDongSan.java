import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TrinhGhiBatDongSan {

    public static synchronized void luuVaoCsv(String tenFileKetQua, BatDongSan batDongSan) {
        try (BufferedWriter trinhGhi = new BufferedWriter(new FileWriter(tenFileKetQua, true))) {
            trinhGhi.write(String.format("%s,%s,%s,%s,%s\n",
                    batDongSan.getTieude(),
                    batDongSan.getGia(),
                    batDongSan.getDiachi(),
                    batDongSan.getDientich(),
                    batDongSan.getMota()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
