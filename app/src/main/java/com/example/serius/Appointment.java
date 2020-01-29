package com.example.serius;

public class Appointment {
    public String uidRequester, uidDonor, tanggal, jam;

    public Appointment() {
    }

    public Appointment(String _uidRequester, String _uidDonor, String _tanggal, String _jam) {
        this.uidRequester = _uidRequester;
        this.uidDonor = _uidDonor;
        this.tanggal = _tanggal;
        this.jam = _jam;
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
