package com.example.serius;

import java.util.Date;

public class RequestBlood {
    public String name;
    public String goldar;
    public String jumlah;
    public String hape;
    public String uId;
    public String date;

    public RequestBlood() {
    }

    public RequestBlood(String _name, String _goldar, String _jumlah, String _hape, String _uId, String _date) {
        this.name = _name;
        this.goldar = _goldar;
        this.jumlah = _jumlah;
        this.hape = _hape;
        this.uId = _uId;
        this.date = _date;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getGoldar() { return goldar; }

    public void setGoldar(String goldar) { this.goldar = goldar; }

    public String getHape() { return hape; }

    public void setHape(String hape) { this.hape = hape; }

    public String getJumlah() { return jumlah; }

    public void setJumlah(String jumlah) { this.jumlah = jumlah; }
}
