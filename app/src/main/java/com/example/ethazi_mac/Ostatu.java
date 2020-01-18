package com.example.ethazi_mac;

public class Ostatu {

    private String kodea;
    private String izena;
    private String deskrip;
    private String Ostmota;
    private String logelaKop;
    private String kokapena;
    private String telefonoa;
    private String email;
    private String latitudea;
    private String longitudea;

    public Ostatu(String kodea, String izena, String deskrip, String ostmota, String logelaKop, String kokapena, String telefonoa, String email, String latitudea, String longitudea) {
        this.kodea = kodea;
        this.izena = izena;
        this.deskrip = deskrip;
        Ostmota = ostmota;
        this.logelaKop = logelaKop;
        this.kokapena = kokapena;
        this.telefonoa = telefonoa;
        this.email = email;
        this.latitudea = latitudea;
        this.longitudea = longitudea;
    }

    public String getKodea() {
        return kodea;
    }

    public void setKodea(String kodea) {
        this.kodea = kodea;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getDeskrip() {
        return deskrip;
    }

    public void setDeskrip(String deskrip) {
        this.deskrip = deskrip;
    }

    public String getOstmota() {
        return Ostmota;
    }

    public void setOstmota(String ostmota) {
        Ostmota = ostmota;
    }

    public String getLogelaKop() {
        return logelaKop;
    }

    public void setLogelaKop(String logelaKop) {
        this.logelaKop = logelaKop;
    }

    public String getKokapena() {
        return kokapena;
    }

    public void setKokapena(String kokapena) {
        this.kokapena = kokapena;
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

    public String getLatitudea() {
        return latitudea;
    }

    public void setLatitudea(String latitudea) {
        this.latitudea = latitudea;
    }

    public String getLongitudea() {
        return longitudea;
    }

    public void setLongitudea(String longitudea) {
        this.longitudea = longitudea;
    }

    @Override
    public String toString() {
        return izena + "\n" +  Ostmota + "\n" + telefonoa + "\n" +  email + "\n";
    }
}
