package com.example.serius;

import java.util.Date;

public class RequestBlood {
    public String name;
    public String goldar;
    public String jumlah;
    public String hape;
    public String namaDokter;
    public String noTelpRumahSakit;
    public String date;
    public String uId;
    public Boolean approved;

    public RequestBlood() {
    }

    public RequestBlood(String _name, String _goldar, String _jumlah, String _hape, String _namaDokter, String _noTelpRumahSakit, String _date, String _uId, Boolean _approved) {
        this.name = _name;
        this.goldar = _goldar;
        this.jumlah = _jumlah;
        this.hape = _hape;
        this.namaDokter = _namaDokter;
        this.noTelpRumahSakit = _noTelpRumahSakit;
        this.date = _date;
        this.uId = _uId;
        this.approved = _approved;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getGoldar() { return goldar; }

    public void setGoldar(String goldar) { this.goldar = goldar; }

    public String getHape() { return hape; }

    public void setHape(String hape) { this.hape = hape; }

    public String getJumlah() { return jumlah; }

    public void setJumlah(String jumlah) { this.jumlah = jumlah; }

    public String getNamaDokter() {
        return namaDokter;
    }

    public String getNoTelpRumahSakit() {
        return noTelpRumahSakit;
    }
}
