package com.negativ.travelServiceTelegramBot;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class CityService {

    @Value("${api.url}")
    private String apiUrl;

    public City loadCityByName(String name) throws IOException, NotFoundException {
        URL url = new URL("http://" + apiUrl + "/api/city/" + name);
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
