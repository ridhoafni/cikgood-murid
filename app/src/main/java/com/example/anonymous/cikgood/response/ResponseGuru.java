package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.Guru;
import com.example.anonymous.cikgood.models.Matpel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGuru {

    @SerializedName("master")
    @Expose
    private List<Guru> master = null;

    public List<Guru> getMaster() {
        return master;
    }

    public void setMaster(List<Guru> master) {
        this.master = master;
    }

}
