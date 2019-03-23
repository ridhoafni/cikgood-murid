package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.CariGuru;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCariGuru {
    @SerializedName("master")
    @Expose
    private List<CariGuru> master = null;

    public List<CariGuru> getMaster() {
        return master;
    }

    public void setMaster(List<CariGuru> master) {
        this.master = master;
    }
}
