package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.GuruDataMatpel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGuruDataMatpel {
    @SerializedName("master")
    @Expose
    private List<GuruDataMatpel> master = null;

    public List<GuruDataMatpel> getMaster() {
        return master;
    }

    public void setMaster(List<GuruDataMatpel> master) {
        this.master = master;
    }
}
