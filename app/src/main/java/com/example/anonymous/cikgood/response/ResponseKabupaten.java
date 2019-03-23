package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.Kabupaten;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKabupaten {
    @SerializedName("master")
    @Expose
    private List<Kabupaten> master = null;

    public List<Kabupaten> getMaster() {
        return master;
    }

    public void setMaster(List<Kabupaten> master) {
        this.master = master;
    }
}
