package com.example.anonymous.cikgood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Saldo {
    @SerializedName("id_saldo")
    @Expose
    private Integer idSaldo;
    @SerializedName("pengguna_id")
    @Expose
    private Integer penggunaId;
    @SerializedName("total_saldo")
    @Expose
    private Integer totalSaldo;

    public Integer getIdSaldo() {
        return idSaldo;
    }

    public void setIdSaldo(Integer idSaldo) {
        this.idSaldo = idSaldo;
    }

    public Integer getPenggunaId() {
        return penggunaId;
    }

    public void setPenggunaId(Integer penggunaId) {
        this.penggunaId = penggunaId;
    }

    public Integer getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(Integer totalSaldo) {
        this.totalSaldo = totalSaldo;
    }
}
