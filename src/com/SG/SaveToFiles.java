package com.SG;

import com.SG.dataClasses.INDEXYdata;
import com.SG.dataClasses.POGODAdata;
import com.SG.dataClasses.WALUTAdata;
import com.SG.dataClasses.WIG20data;
import com.SG.statics.ReferenceStrings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SaveToFiles {

    static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void saveWIG20(ArrayList<WIG20data> data) throws IOException {
        BufferedWriter bw;
        Path wig20file = Paths.get(Config.getOutputDirectory() + File.separator + "wig20_spolki.txt");
        Path f;

        try {
           if (!Files.exists(wig20file)) {
                f = Files.createFile(wig20file);
            } else {
                f = wig20file;
            }
            bw = Files.newBufferedWriter(f, StandardOpenOption.DSYNC, StandardOpenOption.WRITE);
            for (WIG20data d : data) {
                bw.write(d.getSaveable() + "\t");
            }

            bw.flush();
            bw.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }

    public static void saveIndex(ArrayList<INDEXYdata> data) throws IOException {
        BufferedWriter bw;

        Path indexfile = Paths.get(Config.getOutputDirectory() + File.separator + "indexy.txt");
        Path f;
        try {
            if (!Files.exists(indexfile)) {
                f = Files.createFile(indexfile);
            } else {
                f = indexfile;
            }
            bw = Files.newBufferedWriter(f, StandardOpenOption.DSYNC, StandardOpenOption.WRITE);
            for (INDEXYdata d : data) {
                if (!d.getName().contentEquals("missing"))bw.write(d.getSaveable() + "\t");
            }

            bw.flush();
            bw.close();

        } catch (Exception exc) {
            MyLogger.addToLog(exc);
            exc.printStackTrace();
        }
    }

    public static void savePogoda(ArrayList<POGODAdata> data) throws IOException {
        BufferedWriter bw;

        Path[] pogodafiles = new Path[ReferenceStrings.CITIES.length + 1];
        pogodafiles[0] = Paths.get(Config.getOutputDirectory() + File.separator + "pogoda.txt");

        for (int i = 1; i < ReferenceStrings.CITIES.length + 1; i++) {
            pogodafiles[i] = Paths.get(Config.getOutputDirectory()+ File.separator + "temp_" + ReferenceStrings.CITIES[i - 1] + ".txt");
        }
        Path[] f = new Path[pogodafiles.length];


        try {
           for (int i = 0; i < pogodafiles.length; i++) {
                if (!Files.exists(pogodafiles[i])) {
                    f[i] = Files.createFile(pogodafiles[i]);
                } else {
                    f[i] = pogodafiles[i];
                }
            }

            bw = Files.newBufferedWriter(f[0], StandardOpenOption.DSYNC, StandardOpenOption.WRITE);
            bw.write("Pogoda na " + dateFormat.format(new Date()) + ":\t");
            for (POGODAdata d : data) {
                bw.write(d.getSaveable() + "\t");
            }
            bw.flush();
            bw.close();
            int j = 0;
            for (int i = 1; i < f.length; i++) {
                bw = Files.newBufferedWriter(f[i], StandardOpenOption.DSYNC, StandardOpenOption.WRITE);
                bw.write(Double.toString(data.get(j).getTemperatura()) + "\u00B0" + "C\t");
                bw.flush();
                bw.close();
                j++;
            }


        } catch (Exception exc) {
            MyLogger.addToLog(exc);
            exc.printStackTrace();
        }
    }

    public static void saveWaluta(ArrayList<WALUTAdata> data) throws IOException {
        BufferedWriter bw;

        Path currency = Paths.get(Config.getOutputDirectory() + File.separator + "waluta.txt");
        Path f;
        try {
            if (!Files.exists(currency)) {
                f = Files.createFile(currency);
            } else {
                f = currency;
            }
            bw = Files.newBufferedWriter(f, StandardOpenOption.DSYNC, StandardOpenOption.WRITE);
            for (WALUTAdata d : data) {
                bw.write(d.getSaveable() + "\t");

            }

            bw.flush();
            bw.close();

        } catch (Exception exc) {
            MyLogger.addToLog(exc);
            exc.printStackTrace();
        }
    }

    public static void saveCompound(ArrayList<WIG20data> data1, ArrayList<INDEXYdata> data2, ArrayList<POGODAdata> data3, ArrayList<WALUTAdata> data4) throws IOException {
        BufferedWriter bw;
        BufferedReader br;
        Path compound = Paths.get(Config.getOutputDirectory()+ File.separator + "compound.txt");
        String compSeparator = ReferenceStrings.MOV_CAPTIONS[0] + Config.getAssetsDirectory()+File.separator+"separator.gif" + ReferenceStrings.MOV_CAPTIONS[1];
        Path f;

        try {
            if (!Files.exists(compound)) {
                    f = Files.createFile(compound);
            } else {
                f = compound;
            }
            bw = Files.newBufferedWriter(f, StandardOpenOption.DSYNC, StandardOpenOption.TRUNCATE_EXISTING);
            bw.write("Indeksy giełdowe:\t\t");
            for (INDEXYdata d : data2) {
                if (!d.getName().contentEquals("missing"))bw.write(d.getSaveable() + "\t");

            }

            bw.newLine();
            bw.write(compSeparator);
            bw.newLine();

            bw.write("Spółki WIG20:\t\t");
            for (WIG20data d : data1) {
                bw.write(d.getSaveable() + "\t");
            }

            bw.newLine();
            bw.write(compSeparator);
            bw.newLine();

            bw.write("Pogoda na " + dateFormat.format(new Date()) + ":\t");
            for (POGODAdata d : data3) {
                bw.write(d.getSaveable() + "\t");
            }

            bw.newLine();
            bw.write(compSeparator);
            bw.newLine();

            bw.write("Kursy walut:\t\t");
            for (WALUTAdata d : data4) {
                bw.write(d.getSaveable() + "\t");
            }
            bw.newLine();
            bw.write(compSeparator);
            bw.newLine();
            for (String input:Config.getInputPaths()){
                try {
                    br = Files.newBufferedReader(Paths.get(input), Charset.forName("ISO-8859-2"));
                    boolean test = true;
                    while (test) {
                        String _s = br.readLine();
                        if (_s == null) {
                            test = false;
                        } else {
                            bw.write(_s);
                            bw.newLine();
                            bw.write(compSeparator);
                            bw.newLine();
                        }
                    }
                }catch(Exception e){MyLogger.addToLog(e);}
            }

            bw.flush();
            bw.close();
        } catch (Exception exc) {
            MyLogger.addToLog(exc);
            exc.printStackTrace();
        }


    }


}
