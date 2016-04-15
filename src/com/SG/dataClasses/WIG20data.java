package com.SG.dataClasses;

import com.SG.statics.Assets;
import com.SG.statics.ReferenceStrings;

/**
 * Created by WhoCares on 2016-03-12.
 */
public class WIG20data {

    String name;
    double value;
    double change;


    public WIG20data(String _name, double _value, double _change) {
        this.name = _name;
        this.value = _value;
        this.change = _change;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public double getChange() {
        return change;
    }

    public String getSaveable() {
        String asset;
        if (change > 0) {
            asset = Assets.ARROWUP.getPath();
        } else if (change < 0) {
            asset = Assets.ARROWDOWN.getPath();
        } else {
            asset = Assets.NOCHANGE.getPath();
        }
        return name + ":\t" + value + "\t" + ReferenceStrings.MOV_CAPTIONS[0] + asset + ReferenceStrings.MOV_CAPTIONS[1] + change + "%";
    }

    @Override
    public String toString() {

        return "WIG20data{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", change=" + change +
                '}';
    }
}
