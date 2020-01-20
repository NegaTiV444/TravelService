package com.negativ.travelServiceTelegramBot;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class CityService {

    private static class SingletonHandler {
        static final CityService INSTANCE = new CityService();
    }

    private CityService() {
    }

    public static CityService getInstance() {
        return CityService.SingletonHandler.INSTANCE;
    }

    public City loadCityByName(String name) throws IOException {
        URL url = new URL(""); //TODO get url from properties
        Scanner in = new Scanner((InputStream) url.getContent());
        StringBuilder str = new StringBuilder();
        while (in.hasNext()) {
            str.append(in.nextLine());
        }
        JSONObject jsonObject = new JSONObject(str.toString());
        City city = new City();
        city.setId((long) jsonObject.get("id"));
        city.setName((String) jsonObject.get("name"));
        city.setDescription((String) jsonObject.get("description"));
        return city;
    }

}
