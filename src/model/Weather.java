package model;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by wesse on 13-1-2017.
 */
public class Weather {

    public static CurrentWeather getWeather() throws IOException, JSONException {
        // declaring object of "OpenWeatherMap" class
        OpenWeatherMap owm = new OpenWeatherMap("bf46a2140c084fc90d31b2447cb3b637");
        CurrentWeather cw = owm.currentWeatherByCityName("Rijpwetering");

        // return the weather map
        return cw;
    }

    public static double fahrenheitToCelsius(float fahrenheit) {
        double celsius = (fahrenheit - 32) / 1.8;

        return celsius;
    }

    public static int getTemperature() throws IOException, JSONException {
        float fahrenheit = getWeather().getMainInstance().getTemperature();
        int celsius = (int) fahrenheitToCelsius(fahrenheit);

        return celsius;
    }

    public static String getWeatherDescription() throws IOException, JSONException {
        String description = getWeather().getWeatherInstance(0).getWeatherDescription();

        return description;
    }
}


