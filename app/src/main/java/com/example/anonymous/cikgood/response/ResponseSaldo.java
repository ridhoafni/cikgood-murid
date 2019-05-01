package com.example.anonymous.cikgood.response;

import com.example.anonymous.cikgood.models.Saldo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSaldo {

    @SerializedName("master")
    @Expose
    private Saldo master;

    public Saldo getMaster() {
        return master;
    }

    public void setMaster(Saldo master) {
        this.master = master;
    }
}
