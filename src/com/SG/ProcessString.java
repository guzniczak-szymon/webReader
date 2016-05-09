package com.SG;

import com.SG.dataClasses.*;
import com.SG.statics.ReferenceStrings;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;

/**
 * Created by WhoCares on 2016-03-07.
 */
public class ProcessString {

    private ArrayList<WIG20data> wig20dataArrayList;
    private ArrayList<POGODAdata> pogodAdataArrayList;
    private ArrayList<INDEXYdata> indexYdataArrayList;
    private ArrayList<WALUTAdata> walutAdataArrayList;

    public ProcessString() {
        wig20dataArrayList = new ArrayList<>();
        pogodAdataArrayList = new ArrayList<>();
        indexYdataArrayList = new ArrayList<>();
        walutAdataArrayList = new ArrayList<>();
    }

    public void processWIG20() {
        String https_url = ReferenceStrings.HTTP_URL[0];
        URL url;
        try {
            url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            if (con != null) {
                try {
                    BufferedReader br =
                            new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));
                    String input = br.readLine();
                    br.close();
                    int _index = 0;
                    ArrayList<String> splited = new ArrayList<>(20);
                    while (_index != -1) {
                        int _start = input.indexOf("<text>", _index);
                        int _end = input.indexOf("</text>", _index + 1);
                        if (_start != -1) splited.add(input.substring(_start + 6, _end));
                        _index = _end;
                    }

                    for (String split : splited) {
                        String _name = split.substring(split.indexOf("<b>") + 3, split.indexOf("</b>"));
                        double _val = Double.parseDouble(split.substring(split.indexOf("<font color=\"#000000\">") + "<font color=\"#000000\">".length(), split.indexOf("</font>")));
                        double _change;
                        if (split.substring(split.indexOf("</font>") + 9, split.indexOf("</font>") + 10).equals("0")) {
                            _change = 0.0D;
                        } else {
                            _change = Double.parseDouble(split.substring(split.indexOf("</font>") + 31, split.indexOf("%")));
                        }
                        wig20dataArrayList.add(new WIG20data(_name, _val, _change));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ProcessIndex() {
        String https_url = ReferenceStrings.HTTP_URL[1];
        URL url;
        try {
            url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            if (con != null) {
                try {
                    BufferedReader br =
                            new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));
                    String input, name, wartosc;
                    double obrot, zmiana;
                    while ((input = br.readLine()) != null) {

                        if (input.contains("<tr>")) {

                            input = br.readLine();

                            if (input.contains("<td class=\"col_120 left\">")) {
                                name = input.substring(input.indexOf("<td class=\"col_120 left\">") + "<td class=\"col_120 left\">".length(), input.indexOf("</td>"));

                            } else {
                                name = "missing";
                            }

                            input = br.readLine();

                            if (input.contains("<td class=\"right\">")) {
                                wartosc = input.substring(input.indexOf("<td class=\"right\">") + "<td class=\"right\">".length(), input.indexOf("</td>")).replace(',', '.').replace(" ", "").replace("&nbsp;", "");
                            } else {
                                wartosc = "0.0";
                            }

                            input = br.readLine();

                            if (input.contains("<td class=\"right\">")) {
                                obrot = Double.parseDouble(input.substring(input.indexOf("<td class=\"right\">") + "<td class=\"right\">".length(), input.indexOf("</td>")).replace(",", ".").replace(" ", "").replace("&nbsp;", "").replace("---", "0.0"));
                            } else {
                                obrot = 0.0D;
                            }

                            input = br.readLine();

                            if (input.contains("<td class=\"col_50 green right\">")) {
                                zmiana = Double.parseDouble(input.substring(input.indexOf("<td class=\"col_50 green right\">") + "<td class=\"col_50 green right\">".length(), input.indexOf("</td>") - 1).replace(",", ".").replace(" ", "").replace("&nbsp;", "").replace("---", "0.0"));
                            } else if (input.contains("<td class=\"col_50 red right\">")) {
                                zmiana = Double.parseDouble(input.substring(input.indexOf("<td class=\"col_50 red right\">") + "<td class=\"col_50 red right\">".length(), input.indexOf("</td>") - 1).replace(",", ".").replace(" ", "").replace("&nbsp;", "").replace("---", "0.0"));
                            } else {
                                zmiana = 0.0D;
                            }
                            if (!wartosc.contains("---"))
                                indexYdataArrayList.add(new INDEXYdata(name, Double.parseDouble(wartosc), obrot, zmiana));
                        }

                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void processWeather(String city) {
        String https_url = ReferenceStrings.HTTP_URL[2] + city;
        URL url;
        try {
            url = new URL(https_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (con != null) {
                try {
                    BufferedReader br =
                            new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));
                    String input;
                    input = br.readLine();
                    br.close();
                    String _testAgainst1 = "<img src=\"/images/icons/weather/medium/dark/";
                    String _testAgainst2 = "<span class=\"C\">";

                    /* Only needed if weather from more than one day is needed - not finished

                    ArrayList<String> splitedWeather= new ArrayList<>(20);
                    ArrayList<String> splitedCelcius= new ArrayList<>(20);
                    int _index=0;
                    while(_index !=-1)
                    {
                        int _start=input.indexOf(_testAgainst1,_index);
                        int _end = input.indexOf(".png",_start);
                        if (_start!=-1)splitedWeather.add(input.substring(_start+_testAgainst1.length(),_end));
                        _start=input.indexOf(_testAgainst2,_index);
                        _end = input.indexOf("</span>",_start);
                        if (_start!=-1)splitedCelcius.add(input.substring(_start+_testAgainst2.length(),_end));

                        _index=_end;
                    }
                    */
                    String weather = "";
                    int celcius = 0;

                    int _start = input.indexOf(_testAgainst1);
                    int _end = input.indexOf(".png", _start);
                    if (_start != -1) weather = input.substring(_start + _testAgainst1.length(), _end);

                    int _start2 = input.indexOf(_testAgainst2);
                    int _end2 = input.indexOf("</span>", _start2);
                    if (_start2 != -1)
                        celcius = Integer.parseInt(input.substring(_start2 + _testAgainst2.length(), _end2));

                    String icon;
                    switch (weather) {

                        case "sch":
                        case "schm":
                        case "schlsh":
                        case "sjhaa":
                        case "schmd":
                        case "schd":
                        case "schds":
                        case "schmds":
                        case "ch":
                        case "chm":
                        case "chd":
                        case "chmd":
                        case "schs":
                        case "chs":
                        case "schms":
                        case "chms":
                        case "s":
                        case "schl":
                        case "schmdb":
                            icon = weather;
                            break;
                        default:
                            icon = "default";
                            break;

                    }
                    pogodAdataArrayList.add(new POGODAdata(city, celcius, icon));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processWaluty() {
        String https_url = ReferenceStrings.HTTP_URL[3];
        URL url;
        try {
            url = new URL(https_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (con != null) {
                try {
                    BufferedReader br =
                            new BufferedReader(
                                    new InputStreamReader(con.getInputStream(), "ISO-8859-2"));
                    String input, _nazwa, _kod;
                    int _przelicznik;
                    double _val;
                    //ISO-8859-2

                    Charset charset = Charset.forName("ISO-8859-2");
                    ByteBuffer bb;
                    CharBuffer cb;
                    CharsetDecoder chd = charset.newDecoder();
                    CharsetEncoder che = charset.newEncoder();


                    while ((input = br.readLine()) != null) {

                        if (input.contains("<pozycja>")) {

                            input = br.readLine();

                            if (input.contains("<nazwa_waluty>")) {
                                _nazwa = input.substring(input.indexOf("<nazwa_waluty>") + "<nazwa_waluty>".length(), input.indexOf("</nazwa_waluty>"));
                               /* try{
                                    bb = che.encode(CharBuffer.wrap(_nazwa));
                                    cb = chd.decode(bb);
                                    _nazwa=cb.toString();
                                }catch(Exception e){}*/

                            } else {
                                _nazwa = "missing";
                            }

                            input = br.readLine();
                            if (input.contains("<przelicznik>")) {
                                _przelicznik = Integer.parseInt(input.substring(input.indexOf("<przelicznik>") + "<przelicznik>".length(), input.indexOf("</przelicznik>")));
                            } else {
                                _przelicznik = 0;
                            }

                            input = br.readLine();

                            if (input.contains("<kod_waluty>")) {
                                _kod = input.substring(input.indexOf("<kod_waluty>") + "<kod_waluty>".length(), input.indexOf("</kod_waluty>"));
                            } else {
                                _kod = "missing";
                            }

                            input = br.readLine();

                            if (input.contains("<kurs_sredni>")) {
                                _val = Double.parseDouble(input.substring(input.indexOf("<kurs_sredni>") + "<kurs_sredni>".length(), input.indexOf("</kurs_sredni>")).replace(",", "."));
                            } else {
                                _val = 0.0D;
                            }
                            walutAdataArrayList.add(new WALUTAdata(_nazwa, _kod, _przelicznik, _val));
                        }

                    }
                    br.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<WALUTAdata> getWalutAdataArrayList() {
        return walutAdataArrayList;
    }

    public ArrayList<WIG20data> getWig20dataArrayList() {
        return wig20dataArrayList;
    }

    public ArrayList<POGODAdata> getPogodAdataArrayList() {
        return pogodAdataArrayList;
    }

    public ArrayList<INDEXYdata> getIndexYdataArrayList() {
        return indexYdataArrayList;
    }

    public void cleanArrays() {
        wig20dataArrayList.clear();
        pogodAdataArrayList.clear();
        indexYdataArrayList.clear();
        walutAdataArrayList.clear();
    }
}
