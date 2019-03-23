package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.PengalamanKerja;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePengalamanKerja {
    @SerializedName("master")
    @Expose
    private List<PengalamanKerja> master = null;

    public List<PengalamanKerja> getMaster() {
        return master;
    }

    public void setMaster(List<PengalamanKerja> master) {
        this.master = master;
    }
}
