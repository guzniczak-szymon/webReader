package com.SG.dataClasses;

import com.SG.Config;
import com.SG.statics.Assets;
import com.SG.statics.ReferenceStrings;

/**
 * Created by WhoCares on 2016-03-12.
 */
public class POGODAdata {
    private String miasto;
    private int temperatura;
    private String zachmurzenie;

    public POGODAdata(String miasto, int temperatura, String zachmurzenie) {
        this.miasto = miasto;
        this.temperatura = temperatura;
        this.zachmurzenie = zachmurzenie;
    }

    public String getMiasto() {
        return miasto;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public String getZachmurzenie() {
        return zachmurzenie;
    }

    public String getSaveable() {

        return miasto + ":\t" + ReferenceStrings.MOV_CAPTIONS[0] + Config.getAssetsDirectory().toString()+zachmurzenie+".gif" + ReferenceStrings.MOV_CAPTIONS[1] + "\t" + temperatura + "\u00B0" + "C";
    }


    @Override
    public String toString() {
        return "POGODAdata{" +
                "zachmurzenie='" + zachmurzenie + '\'' +
                ", temperatura=" + temperatura +
                ", miasto='" + miasto + '\'' +
                '}';
    }

    //2014/08/06 15:59:48

}
