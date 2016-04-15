package com.SG;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {


    private static final Logger MYLOGGER = Logger.getLogger(MyLogger.class.getName());
    private static FileHandler fhandler;

    public static void init() {
        try {
            fhandler = Config.getHandler();
            fhandler.setEncoding("UTF-8");
            fhandler.setFormatter(new MyFormatter());

            MYLOGGER.addHandler(fhandler);
            addToLog("Logging started", Level.INFO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void addToLog(T t) {
        MYLOGGER.log(Level.ALL, t.toString(), t);
    }

    public static <T> void addToLog(T t, Level l) {
        MYLOGGER.log(l, t.toString(), t);
    }


}

