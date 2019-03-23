package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.Pendidikan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePendidikan {
    @SerializedName("master")
    @Expose
    private List<Pendidikan> master = null;

    public List<Pendidikan> getMaster() {
        return master;
    }

    public void setMaster(List<Pendidikan> master) {
        this.master = master;
    }
}
