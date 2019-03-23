package com.example.anonymous.cikgood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kabupaten {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
