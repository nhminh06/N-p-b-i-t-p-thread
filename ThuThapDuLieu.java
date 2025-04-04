import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ThuThapDuLieu {
    private String trangWeb;

    public ThuThapDuLieu(String trangWeb) {
        this.trangWeb = trangWeb;
    }

    public List<BatDongSan> thuThap() {
        List<BatDongSan> danhSach = new ArrayList<>();
        try {
            Document taiLieu = Jsoup.connect(trangWeb).get();

            Elements cacPhanTuBatDongSan = taiLieu.select(".property-item");

            for (Element phanTu : cacPhanTuBatDongSan) {
                String tieuDe = phanTu.select(".title").text();
                String gia = phanTu.select(".price").text();
                String diaChi = phanTu.select(".address").text();
                String dienTich = phanTu.select(".area").text();
                String moTa = phanTu.select(".description").text();

                BatDongSan batDongSan = new BatDongSan(tieuDe, gia, diaChi, dienTich, moTa);
                danhSach.add(batDongSan);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return danhSach;
    }
}