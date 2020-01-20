package com.negativ.travelServiceTelegramBot;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class CityService {

    private String API_URL;

    private static class SingletonHandler {
        static final CityService INSTANCE = new CityService();
    }

    private CityService() {
        init();
    }

    private void init() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File("src/main/resources/bot.properties")));
            API_URL = props.getProperty("api.url");
        } catch (IOException e) {
        }
    }

    public static CityService getInstance() {
        return CityService.SingletonHandler.INSTANCE;
    }

    public City loadCityByName(String name) throws IOException, NotFoundException {
        URL url = new URL("http://" + API_URL + "/api/city/" + name);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn.getResponseCode() == 404) {
            throw new NotFoundException("City '" + name + "' not found");
        } else {
            Scanner in = new Scanner((InputStream) url.getContent());
            StringBuilder str = new StringBuilder();
            while (in.hasNext()) {
                str.append(in.nextLine());
            }
            JSONObject jsonObject = new JSONObject(str.toString());
            City city = new City();
            city.setId((Integer) jsonObject.get("id"));
            city.setName((String) jsonObject.get("name"));
            city.setDescription((String) jsonObject.get("description"));
            return city;
        }
    }

}
