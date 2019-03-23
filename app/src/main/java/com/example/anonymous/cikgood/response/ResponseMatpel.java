package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.Matpel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMatpel {
    @SerializedName("master")
    @Expose
    private List<Matpel> master = null;

    public List<Matpel> getMaster() {
        return master;
    }

    public void setMaster(List<Matpel> master) {
        this.master = master;
    }
}
