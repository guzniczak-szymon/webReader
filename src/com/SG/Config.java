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
//TODO:https://plugins.jetbrains.com/plugin/2162?pr=idea  https://plugins.jetbrains.com/plugin/2917?pr=idea https://plugins.jetbrains.com/plugin/7269?pr=idea https://plugins.jetbrains.com/plugin/7495?pr=idea
        try {
            configFile = Paths.get(ReferenceStrings.CURRENTPATH + File.separator + "config.cfg");
            if (!Files.exists(configFile)) {
                Files.createFile(configFile);
                generateConfigFile();
            }
            BufferedReader br;
            br = Files.newBufferedReader(configFile);
            String s=br.readLine();
            while (s != null) {
                String sub_s=s.substring(0, s.indexOf("=") + 1);
                if (sub_s.contentEquals(ReferenceStrings.CUSTOMINPUTSTRING)){
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
                }else if(sub_s.contentEquals(ReferenceStrings.ASSETSTRING)){
                    assetsDirectory = Paths.get(s.substring(s.indexOf(ReferenceStrings.ASSETSTRING) + ReferenceStrings.ASSETSTRING.length()));
                    if (Files.notExists(assetsDirectory)){Files.createDirectory(assetsDirectory);}
                }else if(sub_s.contentEquals(ReferenceStrings.OUTPUTSTRING)){
                    outputDirectory = Paths.get(s.substring(s.indexOf(ReferenceStrings.OUTPUTSTRING) + ReferenceStrings.OUTPUTSTRING.length()));
                    if (Files.notExists(outputDirectory)){Files.createDirectory(outputDirectory);}
                }
                s = br.readLine();
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
            bw.write(pathToUserData+"\n");
            bw.write(pathToAssets+"\n");
            bw.write(pathToOutput+"\n");
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
            jarFile= new JarFile(ReferenceStrings.CURRENTPATH+File.separator+"WebReader.jar");
            Enumeration enu = jarFile.entries();
            while (enu.hasMoreElements()) {
                jarEntry = (JarEntry) enu.nextElement();
                File f = new File(jarEntry.getName());
                if(jarEntry.getName().contains("assets")){
                    if (jarEntry.isDirectory()) {
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
