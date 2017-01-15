package model;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by wesse on 13-1-2017.
 */
public class Weather {

    public static OpenWeatherMap getWeatherMap() {
        // declaring object of "OpenWeatherMap" class
        OpenWeatherMap owm = new OpenWeatherMap("bf46a2140c084fc90d31b2447cb3b637");

        return owm;
    }

    public static CurrentWeather getCurrentWeather() throws IOException, JSONException {
        // declaring object of "OpenWeatherMap" class
        OpenWeatherMap owm = getWeatherMap();
        CurrentWeather cw = owm.currentWeatherByCityName("Rijpwetering");

        // return the weather map
        return cw;
    }

    public static DailyForecast getWeatherForecast() throws IOException, JSONException {
       return getWeatherMap().dailyForecastByCityName("Rijpwetering", new Byte("0"));
    }

    public static double fahrenheitToCelsius(float fahrenheit) {
        double celsius = (fahrenheit - 32) / 1.8;

        return celsius;
    }

    public static int getTemperature() throws IOException, JSONException {
        float fahrenheit = getCurrentWeather().getMainInstance().getTemperature();
        int celsius = (int) fahrenheitToCelsius(fahrenheit);

        return celsius;
    }

    public static String getWeatherDescription() throws IOException, JSONException {
        String description = getCurrentWeather().getWeatherInstance(0).getWeatherDescription();
        System.out.print(description);

        return description;
    }

    public static String getWindSpeed() throws IOException, JSONException {
        return String.valueOf(getCurrentWeather().getWindInstance().getWindSpeed());
    }

    public static String getWeatherTomorrow() throws IOException, JSONException {
        String description = getWeatherForecast().getForecastInstance(0).getWeatherInstance(0).getWeatherDescription();

        return description;
    }
}


