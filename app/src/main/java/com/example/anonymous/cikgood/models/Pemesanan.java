package com.example.anonymous.cikgood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pemesanan {
    @SerializedName("id_pemesanan")
    @Expose
    private String idPemesanan;
    @SerializedName("status_pemesanan")
    @Expose
    private String statusPemesanan;
    @SerializedName("guru_id")
    @Expose
    private String guruId;
    @SerializedName("murid_id")
    @Expose
    private String muridId;
    @SerializedName("matpel")
    @Expose
    private String matpel;
    @SerializedName("jumlah_pertemuan")
    @Expose
    private String jumlahPertemuan;
    @SerializedName("durasi")
    @Expose
    private String durasi;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("jadwal")
    @Expose
    private String jadwal;
    @SerializedName("pesan_tambahan")
    @Expose
    private String pesanTambahan;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("harga_total")
    @Expose
    private String hargaTotal;
    @SerializedName("tgl_pemesanan")
    @Expose
    private String tglPemesanan;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("id_guru")
    @Expose
    private String idGuru;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("photo_profile")
    @Expose
    private String photoProfile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("no_hp")
    @Expose
    private String noHp;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("tgl_lahir")
    @Expose
    private String tglLahir;
    @SerializedName("jk")
    @Expose
    private String jk;
    @SerializedName("provinsi_ktp")
    @Expose
    private String provinsiKtp;
    @SerializedName("kota_ktp")
    @Expose
    private String kotaKtp;
    @SerializedName("kecamatan_ktp")
    @Expose
    private String kecamatanKtp;
    @SerializedName("alamat_ktp")
    @Expose
    private String alamatKtp;
    @SerializedName("provinsi_domisili")
    @Expose
    private String provinsiDomisili;
    @SerializedName("kota_domisili")
    @Expose
    private String kotaDomisili;
    @SerializedName("kecamatan_domisili")
    @Expose
    private String kecamatanDomisili;
    @SerializedName("alamat_domisili")
    @Expose
    private String alamatDomisili;
    @SerializedName("biodata")
    @Expose
    private String biodata;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_chat")
    @Expose
    private String statusChat;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(String idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public String getStatusPemesanan() {
        return statusPemesanan;
    }

    public void setStatusPemesanan(String statusPemesanan) {
        this.statusPemesanan = statusPemesanan;
    }

    public String getGuruId() {
        return guruId;
    }

    public void setGuruId(String guruId) {
        this.guruId = guruId;
    }

    public String getMuridId() {
        return muridId;
    }

    public void setMuridId(String muridId) {
        this.muridId = muridId;
    }

    public String getMatpel() {
        return matpel;
    }

    public void setMatpel(String matpel) {
        this.matpel = matpel;
    }

    public String getJumlahPertemuan() {
        return jumlahPertemuan;
    }

    public void setJumlahPertemuan(String jumlahPertemuan) {
        this.jumlahPertemuan = jumlahPertemuan;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getPesanTambahan() {
        return pesanTambahan;
    }

    public void setPesanTambahan(String pesanTambahan) {
        this.pesanTambahan = pesanTambahan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(String hargaTotal) {
        this.hargaTotal = hargaTotal;
    }

    public String getTglPemesanan() {
        return tglPemesanan;
    }

    public void setTglPemesanan(String tglPemesanan) {
        this.tglPemesanan = tglPemesanan;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIdGuru() {
        return idGuru;
    }

    public void setIdGuru(String idGuru) {
        this.idGuru = idGuru;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhotoProfile() {
        return photoProfile;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getProvinsiKtp() {
        return provinsiKtp;
    }

    public void setProvinsiKtp(String provinsiKtp) {
        this.provinsiKtp = provinsiKtp;
    }

    public String getKotaKtp() {
        return kotaKtp;
    }

    public void setKotaKtp(String kotaKtp) {
        this.kotaKtp = kotaKtp;
    }

    public String getKecamatanKtp() {
        return kecamatanKtp;
    }

    public void setKecamatanKtp(String kecamatanKtp) {
        this.kecamatanKtp = kecamatanKtp;
    }

    public String getAlamatKtp() {
        return alamatKtp;
    }

    public void setAlamatKtp(String alamatKtp) {
        this.alamatKtp = alamatKtp;
    }

    public String getProvinsiDomisili() {
        return provinsiDomisili;
    }

    public void setProvinsiDomisili(String provinsiDomisili) {
        this.provinsiDomisili = provinsiDomisili;
    }

    public String getKotaDomisili() {
        return kotaDomisili;
    }

    public void setKotaDomisili(String kotaDomisili) {
        this.kotaDomisili = kotaDomisili;
    }

    public String getKecamatanDomisili() {
        return kecamatanDomisili;
    }

    public void setKecamatanDomisili(String kecamatanDomisili) {
        this.kecamatanDomisili = kecamatanDomisili;
    }

    public String getAlamatDomisili() {
        return alamatDomisili;
    }

    public void setAlamatDomisili(String alamatDomisili) {
        this.alamatDomisili = alamatDomisili;
    }

    public String getBiodata() {
        return biodata;
    }

    public void setBiodata(String biodata) {
        this.biodata = biodata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusChat() {
        return statusChat;
    }

    public void setStatusChat(String statusChat) {
        this.statusChat = statusChat;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
