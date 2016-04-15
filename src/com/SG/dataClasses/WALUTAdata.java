package com.SG.dataClasses;

/**
 * Created by Bartosh on 17.03.16.
 */
public class WALUTAdata {
    private String nazwa, kod;
    private int przelicznik;
    private double kurs;

    public WALUTAdata(String nazwa, String kod, int przelicznik, double kurs) {
        this.nazwa = nazwa;

        this.kod = kod;
        this.przelicznik = przelicznik;
        this.kurs = kurs;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getKod() {
        return kod;
    }

    public int getPrzelicznik() {
        return przelicznik;
    }

    public double getKurs() {
        return kurs;
    }

    public String getSaveable() {

        return nazwa + ":\t" + przelicznik + kod + "  =  " + kurs + "PLN";
    }

    @Override
    public String toString() {
        return "WALUTAdata{" +
                "nazwa='" + nazwa + '\'' +
                ", kod='" + kod + '\'' +
                ", przelicznik=" + przelicznik +
                ", kurs=" + kurs +
                '}';
    }
}
