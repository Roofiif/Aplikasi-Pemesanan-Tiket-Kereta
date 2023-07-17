package com.example.trainpedia;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Tiket{

    private String argo, kelas, jurusan, jadwal, key;
    private Integer harga;

    public Tiket(String argo, String kelas, String jurusan, String jadwal, Integer harga) {
        this.argo = argo;
        this.kelas = kelas;
        this.jurusan =jurusan;
        this.jadwal = jadwal;
        this.harga = harga;
    }
    public Tiket(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getArgo() {
        return argo;
    }

    public void setArgo(String argo) {
        this.argo = argo;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

}
