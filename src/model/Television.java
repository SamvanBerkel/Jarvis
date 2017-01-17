package model;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by sam on 16/01/2017.
 */
public class Television {
    private static boolean isTvOn;
    private static boolean isStbOn;



    public static void tvActivation() {
        if(isTvOn){
            MainApp.getVoice().say("the television is already on");
        } else {
            MainApp.getVoice().say("turning on the television");
            Arduino.setData("tv on");
            isTvOn = true;
        }
    }

    public static void tvDeactivation() {
        if(!isTvOn){
            MainApp.getVoice().say("the television is already off");
        } else {
            MainApp.getVoice().say("turning off the television");
            Arduino.setData("tv off");
            isTvOn = false;
        }
    }

    public static void stbActivation() {
        if(isStbOn){
            MainApp.getVoice().say("the set top box is already on");
        } else {
            MainApp.getVoice().say("turning on the set top box");
            Arduino.setData("stb on");
            isStbOn = true;
        }
    }

    public static void stbDeactivation() {
        if(!isStbOn){
            MainApp.getVoice().say("the set top box is already off");
        } else {
            MainApp.getVoice().say("turning off the set top box");
            Arduino.setData("stb off");
            isStbOn = true;
        }
    }

    public static void wantToWatchRequest(String speech) throws IOException{
        String media = speech.substring(23, speech.length());
        if(!isTvOn){
            tvActivation();
        }
        if(!isStbOn){
            stbActivation();
            MainApp.getVoice().say("please try again when the set top box has turned on");
        }

        switch(media){
            case "tv":
            case "televion" : Arduino.setData("watch tv");
                break;
            case "netflix": Arduino.setData("watch pc");
                break;
            case "plex": Arduino.setData("watch pc");
                ProcessBuilder plexProcess = new ProcessBuilder("C:/Program Files (x86)/Plex Home Theater/Plex Home Theater.exe");
                plexProcess.start();
                MainApp.getVoice().say("starting plex");
                break;
            case "youtube": Arduino.setData("watch pc");
                openWebpage("https://www.youtube.com/");
                break;
            default: specificChannelRequest(media);
                break;
        }
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void specificChannelRequest(String media){
        if(media.contains("discovery")){
            media = "discovery";
        }
        switch(media){
            case "channel one": Arduino.setData("channel 1");
            break;
            case "channel two": Arduino.setData("channel 2");
            break;
            case "channel three": Arduino.setData("channel 3");
            break;
            case "r t l four": Arduino.setData("RTL 4");
            break;
            case "r t l five": Arduino.setData("RTL 5");
            break;
            case "s b s six": Arduino.setData("SBS 6");
            break;
            case "r t l seven": Arduino.setData("RTL 7");
            break;
            case "veronica": Arduino.setData("veronica");
            break;
            case "discovery":Arduino.setData("discovery");
            break;
            case "comedy central": Arduino.setData("comedy central");
            break;
            case "national geographic": Arduino.setData("national geographic");
            break;
            case "t l c": Arduino.setData("tlc");
            break;
        }
    }
}
