package Utility;

public class MedicatieIneemMoment {
    String patiënt;
    String  medicijn;
    String tijd;
    int aantal;

    public MedicatieIneemMoment(String patiënt, String medicijn, String tijd, int aantal) {
        this.patiënt = patiënt;
        this.medicijn = medicijn;
        this.tijd = tijd;
        this.aantal = aantal;
    }

    public String getPatiënt() {
        return patiënt;
    }

    public void setPatiënt(String patiënt) {
        this.patiënt = patiënt;
    }

    public String getMedicijn() {
        return medicijn;
    }

    public void setMedicijn(String medicijn) {
        this.medicijn = medicijn;
    }

    public String getTijd() {
        return tijd;
    }

    public void setTijd(String tijd) {
        this.tijd = tijd;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }
}
