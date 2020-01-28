package com.example.serius;

public class Appointment {
    public String uidRequester, uidDonor, tanggal, jam;

    public Appointment() {
    }

    public Appointment(String uidRequester, String uidDonor, String tanggal, String jam) {
        this.uidRequester = uidRequester;
        this.uidDonor = uidDonor;
        this.tanggal = tanggal;
        this.jam = jam;
    }

    public String getUidRequester() {
        return uidRequester;
    }

    public String getUidDonor() {
        return uidDonor;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJam() {
        return jam;
    }
}
