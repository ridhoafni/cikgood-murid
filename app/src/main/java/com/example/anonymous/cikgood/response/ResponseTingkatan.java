package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.Tingkatan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTingkatan {
    @SerializedName("master")
    @Expose
    private List<Tingkatan> master = null;

    public List<Tingkatan> getMaster() {
        return master;
    }

    public void setMaster(List<Tingkatan> master) {
        this.master = master;
    }
}
