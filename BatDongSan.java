class BatDongSan{
    private String tieude;
    private String gia;
    private String diachi;
    private String dientich;
    private String mota;

    public BatDongSan(String tieude, String gia, String diachi, String dientich, String mota) {
        this.tieude = tieude;
        this.gia = gia;
        this.diachi = diachi;
        this.dientich = dientich;
        this.mota = mota;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDientich() {
        return dientich;
    }

    public void setDientich(String dientich) {
        this.dientich = dientich;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    @Override
    public String toString() {
        return "BatDongSan{" +
                "tieude='" + tieude + '\'' +
                ", gia='" + gia + '\'' +
                ", diachi='" + diachi + '\'' +
                ", dientich='" + dientich + '\'' +
                ", mota='" + mota + '\'' +
                '}';
    }
}
