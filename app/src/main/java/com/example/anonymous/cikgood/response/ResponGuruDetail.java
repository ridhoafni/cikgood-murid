package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.Guru;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponGuruDetail {
    @SerializedName("master")
    @Expose
    private Guru master;

    public Guru getMaster() {
        return master;
    }

    public void setMaster(Guru master) {
        this.master = master;
    }
}
