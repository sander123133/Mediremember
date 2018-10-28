package Utility;

public class Dokter {
private String inlognaam;
private String wachtwoord;
private String naam;

    public Dokter(String inlognaam, String wachtwoord, String naam) {
        this.inlognaam = inlognaam;
        this.wachtwoord = wachtwoord;
        this.naam = naam;
    }

    public String getInlognaam() {
        return inlognaam;
    }

    public void setInlognaam(String inlognaam) {
        this.inlognaam = inlognaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
