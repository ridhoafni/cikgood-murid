package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.Guru;
import com.example.anonymous.cikgood.models.Matpel;
import com.example.anonymous.cikgood.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGuru {

    @SerializedName("master")
    @Expose
    public List<User> master = null;

    public List<User> getMaster() {
        return master;
    }

    public void setMaster(List<User> master) {
        this.master = master;
    }

}
