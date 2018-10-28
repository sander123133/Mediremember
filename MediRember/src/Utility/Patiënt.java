package Utility;

public class Patiënt {
    private String inlognaam;
    private String wachtwoord;
    private String naam;
    private String naamDokter;
    private String medicijnNaam;

    public Patiënt(String inlognaam, String wachtwoord, String naam, String naamDokter, String medicijnNaam) {
        this.inlognaam = inlognaam;
        this.wachtwoord = wachtwoord;
        this.naam = naam;
        this.naamDokter = naamDokter;
        this.medicijnNaam = medicijnNaam;
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

    public String getNaamDokter() {
        return naamDokter;
    }

    public void setNaamDokter(String naamDokter) {
        this.naamDokter = naamDokter;
    }

    public String getMedicijnNaam() {
        return medicijnNaam;
    }

    public void setMedicijnNaam(String medicijnNaam) {
        this.medicijnNaam = medicijnNaam;
    }
}
