package com.SG.statics;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReferenceStrings {
    public static final Path CURRENTPATH = Paths.get("").toAbsolutePath();
    public static final String[] CITIES = {
            "konin",
            "kalisz",
            "leszno",
            "pila",
            "poznan",
            "gniezno"
    };
    public static final String[] HTTP_URL = {
            "https://www.gpw.pl/tickerxml.php",
            "https://m.gpw.pl/m_notowania_indeksy",
            "http://m.twojapogoda.pl/polska/wielkopolskie/",
            "http://www.nbp.pl/kursy/xml/a052z160316.xml"
    };

    public static final String[] MOV_CAPTIONS = {"<MOVIE>", "</MOVIE>"};
    public static final String INTRO = "Kolektor indeksów oraz pogody. v0.3\n" +
            "SG. Licencja MIT.\n";

    public static final Path USERDATA = Paths.get(CURRENTPATH.toString() + File.separator + "custom.txt");
    public static final Path ASSETS = Paths.get(CURRENTPATH.toString() + File.separator + "assets" + File.separator);
    public static final Path OUTPUT = Paths.get(CURRENTPATH.toString() + File.separator + "output" + File.separator);

    public static final String CUSTOMINPUTSTRING = "custom input=";
    public static final String ASSETSTRING = "assets path=";
    public static final String OUTPUTSTRING = "output path=";
    public static final String SEPARATOR = ";";
}
