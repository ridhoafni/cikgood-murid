package com.example.anonymous.cikgood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengalamanKerja {

    @SerializedName("id_pengalaman_kerja")
    @Expose
    private String idPengalamanKerja;
    @SerializedName("guru_id")
    @Expose
    private String guruId;
    @SerializedName("jabatan")
    @Expose
    private String jabatan;
    @SerializedName("perusahaan")
    @Expose
    private String perusahaan;
    @SerializedName("tgl_masuk")
    @Expose
    private String tglMasuk;
    @SerializedName("tgl_selesai")
    @Expose
    private String tglSelesai;

    public String getIdPengalamanKerja() {
        return idPengalamanKerja;
    }

    public void setIdPengalamanKerja(String idPengalamanKerja) {
        this.idPengalamanKerja = idPengalamanKerja;
    }

    public String getGuruId() {
        return guruId;
    }

    public void setGuruId(String guruId) {
        this.guruId = guruId;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(String tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public String getTglSelesai() {
        return tglSelesai;
    }

    public void setTglSelesai(String tglSelesai) {
        this.tglSelesai = tglSelesai;
    }
}
