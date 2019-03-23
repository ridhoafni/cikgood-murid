package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.Jadwal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseJadwal {
    @SerializedName("master")
    @Expose
    private List<Jadwal> master = null;

    public List<Jadwal> getMaster() {
        return master;
    }

    public void setMaster(List<Jadwal> master) {
        this.master = master;
    }
}
