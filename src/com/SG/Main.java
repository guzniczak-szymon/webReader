package com.SG;


import com.SG.statics.ReferenceStrings;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by WhoCares on 2016-03-12.
 */
public class Main {


    public static void main(String[] args) {
        Config.preinit();
        MyLogger.init();
        Config.init();
        Config.postInit();

        ProcessString ps = new ProcessString();
        System.out.println(ReferenceStrings.INTRO);
        int amount = 0;
        int time;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        try {
            time = Integer.parseInt(args[0]);
            if (time <= 0) {
                throw new Exception("negative time value");
            }
        } catch (Exception e) {
            time = 30;
        }

        while (true) {
            ps.processWIG20();
            ps.ProcessIndex();
            ps.processWaluty();
            for (String city : ReferenceStrings.CITIES) {
                ps.processWeather(city);
            }
            try {
                SaveToFiles.saveWIG20(ps.getWig20dataArrayList());
                if (!ps.getIndexYdataArrayList().isEmpty()) {
                    SaveToFiles.saveIndex(ps.getIndexYdataArrayList());
                }
                SaveToFiles.savePogoda(ps.getPogodAdataArrayList());
                SaveToFiles.saveWaluta(ps.getWalutAdataArrayList());
                SaveToFiles.saveCompound(ps.getWig20dataArrayList(), ps.getIndexYdataArrayList(), ps.getPogodAdataArrayList(), ps.getWalutAdataArrayList());
                ps.cleanArrays();

                System.out.println(dateFormat.format(new Date()) + "   Dotychczas zebrałem dane: " + ++amount + " razy.\nKolejny zbiór za 30 min.");
                TimeUnit.MINUTES.sleep(30);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}


