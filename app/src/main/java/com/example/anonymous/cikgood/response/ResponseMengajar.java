package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.Mengajar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMengajar {
    @SerializedName("master")
    @Expose
    private List<Mengajar> master = null;

    public List<Mengajar> getMaster() {
        return master;
    }

    public void setMaster(List<Mengajar> master) {
        this.master = master;
    }
}
