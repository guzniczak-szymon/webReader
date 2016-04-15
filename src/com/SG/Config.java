package com.SG;

import com.SG.statics.ReferenceStrings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.logging.FileHandler;

public class Config {

    static Path configFile, logFile, assetsDirectory, outputDirectory;

    static BufferedWriter logWriter;

    static FileHandler handler;

    public static ArrayList<String> inputPaths= new ArrayList<>(5);

    public static void preinit() {
        logFile = Paths.get(ReferenceStrings.CURRENTPATH + File.separator + "logger.log");
        try {
            if (!Files.exists(logFile)) {
                Files.createFile(logFile);

            }
            handler = new FileHandler(logFile.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void init() {

        try {
            configFile = Paths.get(ReferenceStrings.CURRENTPATH + File.separator + "config.cfg");
            if (!Files.exists(configFile)) {
                Files.createFile(configFile);
                generateConfigFile();
            }
            BufferedReader br;
            br = Files.newBufferedReader(configFile);
            String s;
            while (br.readLine() != null) {
                s = br.readLine();

                switch (s.substring(0, s.indexOf("=") + 1)) {
                    case ReferenceStrings.CUSTOMINPUTSTRING:
                        if (s.contains(ReferenceStrings.SEPARATOR)) {
                            int _index = s.indexOf(";");
                            int i = 0;
                            while (_index != -1) {
                                String _s=s.substring(i, _index);
                                if (_s.endsWith(".txt")){
                                    inputPaths.add(_s);
                                    Path _p=Paths.get(_s);
                                    if (!Files.exists(_p)) {
                                        Files.createFile(_p);
                                    }
                                }
                                i = _index + 1;
                                _index = s.indexOf(";", i);

                            }
                        } else {
                            inputPaths.add(s.substring(s.indexOf(ReferenceStrings.CUSTOMINPUTSTRING) + ReferenceStrings.CUSTOMINPUTSTRING.length()));
                        }
                        break;
                    case ReferenceStrings.ASSETSTRING:
                        assetsDirectory = Paths.get(s.substring(s.indexOf(ReferenceStrings.ASSETSTRING) + ReferenceStrings.ASSETSTRING.length()));
                        break;
                    case ReferenceStrings.OUTPUTSTRING:
                        outputDirectory = Paths.get(s.substring(s.indexOf(ReferenceStrings.OUTPUTSTRING) + ReferenceStrings.OUTPUTSTRING.length()));
                        break;

                    default:
                        throw new Exception("Corrupted config file!, delete it and start application again.");
                }
            }
        } catch (Exception e) {

            MyLogger.addToLog(e);
        }

    }

    private static void generateConfigFile() {
        BufferedWriter bw;
        String pathToUserData, pathToAssets, pathToOutput;
        pathToUserData = ReferenceStrings.CUSTOMINPUTSTRING + ReferenceStrings.USERDATA.toString();
        pathToAssets = ReferenceStrings.ASSETSTRING + ReferenceStrings.ASSETS.toString();
        pathToOutput = ReferenceStrings.OUTPUTSTRING + ReferenceStrings.OUTPUT.toString();
        try {
            bw = Files.newBufferedWriter(configFile);
            bw.write(pathToUserData);
            bw.newLine();
            bw.write(pathToAssets);
            bw.newLine();
            bw.write(pathToOutput);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            MyLogger.addToLog(e);
        }
    }

    public static void postInit() {
        JarEntry jarEntry;
        JarFile jarFile;
        InputStream is;
        FileOutputStream fos;

        try {
            jarFile= new JarFile(ReferenceStrings.CURRENTPATH+"WebReader.jar");
            Enumeration enu = jarFile.entries();
            while (enu.hasMoreElements()) {
                jarEntry = (JarEntry) enu.nextElement();
                File f = new File(Config.getAssetsDirectory() + jarEntry.getName());
                if(jarEntry.getName().contains("assets")){
                    if (jarEntry.isDirectory() ) {
                        f.mkdir();
                        continue;
                    }
                    is = jarFile.getInputStream(jarEntry);
                    fos = new FileOutputStream(f);
                    while (is.available() > 0) {
                        fos.write(is.read());
                    }
                    fos.flush();
                    fos.close();
                    is.close();
                }
            }
        } catch (Exception e) {MyLogger.addToLog(e);
        }

    }

    public static FileHandler getHandler() {
        return handler;
    }

    public static BufferedWriter getLogWriter() {
        return logWriter;
    }

    public static Path getConfigFile() {
        return configFile;
    }

    public static Path getLogFile() {
        return logFile;
    }

    public static Path getAssetsDirectory() {
        return assetsDirectory;
    }

    public static Path getOutputDirectory() {
        return outputDirectory;
    }

    public static ArrayList<String> getInputPaths() {
        return inputPaths;
    }

}
