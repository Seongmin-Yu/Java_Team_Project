package com.clews.ameokja;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class History {

    String dirpath = Environment.getExternalStorageDirectory() + "/AMeokJa";
    String filename = "History.json";

    static JSONObject jsonObject = new JSONObject();

    static JSONArray foodArray = new JSONArray();

    static JSONArray restaurantArray = new JSONArray();

    JSONObject jsonInfo = new JSONObject();

    void saveHistory(Calendar calendar, String data, EnumHistory form) {

        jsonInfo.put("time", calendar);
        jsonInfo.put("Type", form.toString());
        switch (form) {
            case FOOD:
                jsonInfo.put("food", data);
                foodArray.add(jsonInfo);
                jsonObject.put("foods", foodArray);
                break;
            case RESTAURANT:
                jsonInfo.put("restaurant", data);
                restaurantArray.add(jsonInfo);
                jsonObject.put("restaurants", restaurantArray);
                break;
        }


        try {
            FileWriter file = new FileWriter(dirpath+"\\"+filename);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    String[] readHistory() {

        JSONParser parser = new JSONParser();
        String[] readData = null;


        try {
            Object obj = parser.parse(new FileReader(dirpath+"\\"+filename));

            JSONObject jsonObject = (JSONObject) obj;
            Calendar time = (Calendar) jsonObject.get("time");
            String food = (String)jsonObject.get("food");
            String restaurant = (String)jsonObject.get("restaurant");

            readData = new String[]{time.toString(), food, restaurant};
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return readData;
    }


}
