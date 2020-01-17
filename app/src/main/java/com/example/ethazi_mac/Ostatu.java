package com.example.ethazi_mac;

public class Ostatu {

    private String izena;
    private String Ostmota;
    private String telefonoa;
    private String email;

    public Ostatu(String izena, String ostmota, String telefonoa, String email) {
        this.izena = izena;
        this.Ostmota = ostmota;
        this.telefonoa = telefonoa;
        this.email = email;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getOstmota() {
        return Ostmota;
    }

    public void setOstmota(String ostmota) {
        Ostmota = ostmota;
    }

    public String getTelefonoa() {
        return telefonoa;
    }

    public void setTelefonoa(String telefonoa) {
        this.telefonoa = telefonoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return izena + "\n" +  Ostmota + "\n" + telefonoa + "\n" +  email + "\n";
    }
}
