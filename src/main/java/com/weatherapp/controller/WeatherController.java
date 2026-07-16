package com.weatherapp.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weatherapp.model.WeatherData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherController {
    // ⚠️ Replace with your actual OpenWeatherMap API key
    private static final String API_KEY = ("535ffc358221404c1406cc65124af3f3");

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private final HttpClient httpClient;

    public WeatherController() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public WeatherData getCurrentWeather(String city) throws Exception {
        String url = BASE_URL + "weather?q=" + city + "&appid=" + API_KEY + "&units=metric";
        String response = sendRequest(url);
        return parseCurrentWeather(response);
    }

    public String getForecast(String city) throws Exception {
        String url = BASE_URL + "forecast?q=" + city + "&appid=" + API_KEY + "&units=metric";
        String response = sendRequest(url);
        return parseForecast(response);
    }

    private String sendRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(java.time.Duration.ofSeconds(10))
                .header("User-Agent", "WeatherApp")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("API Error (" + response.statusCode() + "): " + response.body());
        }

        return response.body();
    }

    private WeatherData parseCurrentWeather(String json) {
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();

        WeatherData data = new WeatherData();

        data.setCity(root.get("name").getAsString());
        data.setCountry(root.getAsJsonObject("sys").get("country").getAsString());

        JsonObject main = root.getAsJsonObject("main");
        data.setTemperature(main.get("temp").getAsDouble());
        data.setFeelsLike(main.get("feels_like").getAsDouble());
        data.setHumidity(main.get("humidity").getAsInt());
        data.setPressure(main.get("pressure").getAsInt());

        JsonObject wind = root.getAsJsonObject("wind");
        data.setWindSpeed(wind.get("speed").getAsDouble());

        JsonArray weather = root.getAsJsonArray("weather");
        JsonObject condition = weather.get(0).getAsJsonObject();
        data.setCondition(condition.get("main").getAsString());
        data.setDescription(condition.get("description").getAsString());
        data.setIconCode(condition.get("icon").getAsString());

        return data;
    }

    private String parseForecast(String json) {
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonArray list = root.getAsJsonArray("list");

        StringBuilder forecast = new StringBuilder();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d");

        String lastDate = "";
        int count = 0;

        for (int i = 0; i < list.size() && count < 5; i++) {
            JsonObject item = list.get(i).getAsJsonObject();
            String dtTxt = item.get("dt_txt").getAsString();
            LocalDateTime dateTime = LocalDateTime.parse(dtTxt, inputFormatter);
            String date = dateTime.format(outputFormatter);

            if (!date.equals(lastDate) && dateTime.getHour() >= 11 && dateTime.getHour() <= 14) {
                JsonObject main = item.getAsJsonObject("main");
                JsonArray weather = item.getAsJsonArray("weather");
                JsonObject condition = weather.get(0).getAsJsonObject();

                double temp = main.get("temp").getAsDouble();
                String desc = condition.get("description").getAsString();

                forecast.append(String.format("%s: %.1f°C, %s\n", date, temp, desc));
                lastDate = date;
                count++;
            }
        }

        return forecast.length() > 0 ? forecast.toString() : "Forecast data not available";
    }
}