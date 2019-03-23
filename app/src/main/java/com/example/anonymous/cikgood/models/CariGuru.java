package com.example.anonymous.cikgood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CariGuru {
    @SerializedName("id_guru_bahan_ajar_matpel")
    @Expose
    private String idGuruBahanAjarMatpel;
    @SerializedName("guru_id")
    @Expose
    private String guruId;
    @SerializedName("matpel_id")
    @Expose
    private String matpelId;
    @SerializedName("tarif")
    @Expose
    private String tarif;
    @SerializedName("id_matpel")
    @Expose
    private String idMatpel;
    @SerializedName("tingkatan")
    @Expose
    private String tingkatan;
    @SerializedName("matpel")
    @Expose
    private String matpel;
    @SerializedName("matpel_detail")
    @Expose
    private String matpelDetail;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_guru")
    @Expose
    private String idGuru;
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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("id_bahan_ajar_lokasi")
    @Expose
    private String idBahanAjarLokasi;
    @SerializedName("provinsi")
    @Expose
    private String provinsi;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("kecamatan")
    @Expose
    private String kecamatan;
    @SerializedName("id_riwayat_pendidikan")
    @Expose
    private String idRiwayatPendidikan;
    @SerializedName("gelar")
    @Expose
    private String gelar;
    @SerializedName("jenis_institusi")
    @Expose
    private String jenisInstitusi;
    @SerializedName("nama_institusi")
    @Expose
    private String namaInstitusi;
    @SerializedName("jurusan")
    @Expose
    private String jurusan;
    @SerializedName("tahun_masuk")
    @Expose
    private String tahunMasuk;
    @SerializedName("tahun_selesai")
    @Expose
    private String tahunSelesai;
    @SerializedName("photo_ijazah")
    @Expose
    private String photoIjazah;

    public String getIdGuruBahanAjarMatpel() {
        return idGuruBahanAjarMatpel;
    }

    public void setIdGuruBahanAjarMatpel(String idGuruBahanAjarMatpel) {
        this.idGuruBahanAjarMatpel = idGuruBahanAjarMatpel;
    }

    public String getGuruId() {
        return guruId;
    }

    public void setGuruId(String guruId) {
        this.guruId = guruId;
    }

    public String getMatpelId() {
        return matpelId;
    }

    public void setMatpelId(String matpelId) {
        this.matpelId = matpelId;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getIdMatpel() {
        return idMatpel;
    }

    public void setIdMatpel(String idMatpel) {
        this.idMatpel = idMatpel;
    }

    public String getTingkatan() {
        return tingkatan;
    }

    public void setTingkatan(String tingkatan) {
        this.tingkatan = tingkatan;
    }

    public String getMatpel() {
        return matpel;
    }

    public void setMatpel(String matpel) {
        this.matpel = matpel;
    }

    public String getMatpelDetail() {
        return matpelDetail;
    }

    public void setMatpelDetail(String matpelDetail) {
        this.matpelDetail = matpelDetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdGuru() {
        return idGuru;
    }

    public void setIdGuru(String idGuru) {
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

    public String getIdBahanAjarLokasi() {
        return idBahanAjarLokasi;
    }

    public void setIdBahanAjarLokasi(String idBahanAjarLokasi) {
        this.idBahanAjarLokasi = idBahanAjarLokasi;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getIdRiwayatPendidikan() {
        return idRiwayatPendidikan;
    }

    public void setIdRiwayatPendidikan(String idRiwayatPendidikan) {
        this.idRiwayatPendidikan = idRiwayatPendidikan;
    }

    public String getGelar() {
        return gelar;
    }

    public void setGelar(String gelar) {
        this.gelar = gelar;
    }

    public String getJenisInstitusi() {
        return jenisInstitusi;
    }

    public void setJenisInstitusi(String jenisInstitusi) {
        this.jenisInstitusi = jenisInstitusi;
    }

    public String getNamaInstitusi() {
        return namaInstitusi;
    }

    public void setNamaInstitusi(String namaInstitusi) {
        this.namaInstitusi = namaInstitusi;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getTahunMasuk() {
        return tahunMasuk;
    }

    public void setTahunMasuk(String tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public String getTahunSelesai() {
        return tahunSelesai;
    }

    public void setTahunSelesai(String tahunSelesai) {
        this.tahunSelesai = tahunSelesai;
    }

    public String getPhotoIjazah() {
        return photoIjazah;
    }

    public void setPhotoIjazah(String photoIjazah) {
        this.photoIjazah = photoIjazah;
    }

}
