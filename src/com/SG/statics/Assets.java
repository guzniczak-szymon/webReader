package com.SG.statics;

/**
 * Created by WhoCares on 2016-03-12.
 */
public enum Assets {
    SUN(ReferenceStrings.ASSETS + "old_assets/sun.gif"),
    RAIN(ReferenceStrings.ASSETS + "old_assets/rain.gif"),
    CLOUD(ReferenceStrings.ASSETS + "cloud.gif"),
    SNOW(ReferenceStrings.ASSETS + "old_assets/snow.gif"),
    CLOUDSUN(ReferenceStrings.ASSETS + "old_assets/cas.gif"),
    SMALLRAINSUN(ReferenceStrings.ASSETS + "sras.gif"),
    ARROWUP(ReferenceStrings.ASSETS + "old_assets/aup.gif"),
    ARROWDOWN(ReferenceStrings.ASSETS + "old_assets/adown.gif"),
    NOCHANGE(ReferenceStrings.ASSETS + "snochange.gif");

    private String path;

    Assets(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
