package com.example.serius;

public class RequestBlood {
    public String name;
    public String goldar;
    public String jumlah;
    public String hape;

    public RequestBlood() {
    }

    public RequestBlood(String name, String goldar, String jumlah, String hape) {
        this.name = name;
        this.goldar = goldar;
        this.jumlah = jumlah;
        this.hape = hape;
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
