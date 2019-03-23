package com.example.anonymous.cikgood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Guru {

    @SerializedName("id_guru")
    @Expose
    private Integer idGuru;
    @SerializedName("nama")
    @Expose
    private String nama;
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
    @SerializedName("prestasi")
    @Expose
    private String prestasi;
    @SerializedName("pengalaman_kerja")
    @Expose
    private String pengalamanKerja;
    @SerializedName("pengalaman_mengajar")
    @Expose
    private String pengalamanMengajar;
    @SerializedName("riwayat_pendidikan")
    @Expose
    private String riwayatPendidikan;
    @SerializedName("photo_ijazah")
    @Expose
    private String photoIjazah;
    @SerializedName("nomor_ktp")
    @Expose
    private String nomorKtp;
    @SerializedName("photo_ktp")
    @Expose
    private String photoKtp;
    @SerializedName("npwp")
    @Expose
    private String npwp;
    @SerializedName("photo_npwp")
    @Expose
    private String photoNpwp;
    @SerializedName("nama_bank")
    @Expose
    private String namaBank;
    @SerializedName("norek")
    @Expose
    private String norek;
    @SerializedName("nama_pemilik")
    @Expose
    private String namaPemilik;
    @SerializedName("photo_cv")
    @Expose
    private String photoCv;
    @SerializedName("photo_sertifikat")
    @Expose
    private String photoSertifikat;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getIdGuru() {
        return idGuru;
    }

    public void setIdGuru(Integer idGuru) {
        this.idGuru = idGuru;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getPrestasi() {
        return prestasi;
    }

    public void setPrestasi(String prestasi) {
        this.prestasi = prestasi;
    }

    public String getPengalamanKerja() {
        return pengalamanKerja;
    }

    public void setPengalamanKerja(String pengalamanKerja) {
        this.pengalamanKerja = pengalamanKerja;
    }

    public String getPengalamanMengajar() {
        return pengalamanMengajar;
    }

    public void setPengalamanMengajar(String pengalamanMengajar) {
        this.pengalamanMengajar = pengalamanMengajar;
    }

    public String getRiwayatPendidikan() {
        return riwayatPendidikan;
    }

    public void setRiwayatPendidikan(String riwayatPendidikan) {
        this.riwayatPendidikan = riwayatPendidikan;
    }

    public String getPhotoIjazah() {
        return photoIjazah;
    }

    public void setPhotoIjazah(String photoIjazah) {
        this.photoIjazah = photoIjazah;
    }

    public String getNomorKtp() {
        return nomorKtp;
    }

    public void setNomorKtp(String nomorKtp) {
        this.nomorKtp = nomorKtp;
    }

    public String getPhotoKtp() {
        return photoKtp;
    }

    public void setPhotoKtp(String photoKtp) {
        this.photoKtp = photoKtp;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getPhotoNpwp() {
        return photoNpwp;
    }

    public void setPhotoNpwp(String photoNpwp) {
        this.photoNpwp = photoNpwp;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getNorek() {
        return norek;
    }

    public void setNorek(String norek) {
        this.norek = norek;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getPhotoCv() {
        return photoCv;
    }

    public void setPhotoCv(String photoCv) {
        this.photoCv = photoCv;
    }

    public String getPhotoSertifikat() {
        return photoSertifikat;
    }

    public void setPhotoSertifikat(String photoSertifikat) {
        this.photoSertifikat = photoSertifikat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
