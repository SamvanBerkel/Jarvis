package model;

import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by wesse on 13-1-2017.
 */
public class Weather {

    public static OpenWeatherMap getWeather() throws IOException, JSONException {
        // declaring object of "OpenWeatherMap" class
        OpenWeatherMap owm = new OpenWeatherMap("bf46a2140c084fc90d31b2447cb3b637");

        return owm;
    }

    public static double fahrenheitToCelsius(float fahrenheit) {
        double celsius = (fahrenheit - 32) / 1.8;

        return celsius;
    }
}


