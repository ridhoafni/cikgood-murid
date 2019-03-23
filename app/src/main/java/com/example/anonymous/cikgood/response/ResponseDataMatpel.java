package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.DataMatpel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDataMatpel {
    @SerializedName("master")
    @Expose
    private List<DataMatpel> master = null;

    public List<DataMatpel> getMaster() {
        return master;
    }

    public void setMaster(List<DataMatpel> master) {
        this.master = master;
    }
}
