package com.SG.dataClasses;

import com.SG.Config;
import com.SG.statics.Assets;
import com.SG.statics.ReferenceStrings;

/**
 * Created by WhoCares on 2016-03-12.
 */
public class INDEXYdata {

    private String name;
    private double wartosc;
    private double obrot;
    private double zmiana;

    public INDEXYdata(String name, double wartosc, double obrot, double zmiana) {
        this.name = name;
        this.wartosc = wartosc;
        this.obrot = obrot;
        this.zmiana = zmiana;
    }

    public String getName() {
        return name;
    }

    public double getWartosc() {
        return wartosc;
    }

    public double getObrot() {
        return obrot;
    }

    public double getZmiana() {
        return zmiana;
    }

    public String getSaveable() {
        String asset;
        if (zmiana > 0) {
            asset = Config.getAssetsDirectory().toString()+"arrowup.gif";
        } else if (zmiana < 0) {
            asset = Config.getAssetsDirectory().toString()+"arrowdown.gif";
        } else {
            asset = Config.getAssetsDirectory().toString()+"nochange.gif";
        }
        return name + ":\t" + ReferenceStrings.MOV_CAPTIONS[0] + asset + ReferenceStrings.MOV_CAPTIONS[1] + zmiana + "%";
    }

    @Override
    public String toString() {
        return "INDEXYdata{" +
                "name='" + name + '\'' +
                ", wartosc=" + wartosc +
                ", obrot=" + obrot +
                ", zmiana=" + zmiana +
                '}';
    }
}
