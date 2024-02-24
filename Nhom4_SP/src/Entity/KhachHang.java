package Entity;

public class KhachHang {
    
    private String maKh; 
    private String hoTen;
    private String ngaySinh;
    private String diaChi;
    private String soDT;
    private String email;
    private String khoa; 
    private String lop;
    private String avatar;

    public KhachHang() {
        this.avatar = "avatar.jpg";
    }

    public KhachHang(String maKh, String hoTen, String ngaySinh, String diaChi, String soDT, String email, String khoa, String lop, String avatar) {
        this.maKh = maKh;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.email = email;
        this.khoa = khoa;
        this.lop = lop;
        this.avatar = avatar;
    }

    public KhachHang(String maKh) {
        this.maKh = maKh;
        this.hoTen = "username";
        this.avatar = "avatar.png";
    }

    public KhachHang(String maKh, String hoTen) {
        this.maKh = maKh;
        this.hoTen = hoTen;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "KhachHang{" + "maKh=" + maKh + ", hoTen=" + hoTen + ", ngaySinh=" + ngaySinh + ", diaChi=" + diaChi + ", soDT=" + soDT + ", email=" + email + ", khoa=" + khoa + ", lop=" + lop + ", avatar=" + avatar + '}';
    }
    
    
}
