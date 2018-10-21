package com.example.ado.ahmacja;

import android.os.Environment;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import android.content.Context;

public class History {
    JSONObject jsonMain = new JSONObject();
    JSONArray jsonArray;
    JSONObject obj = new JSONObject();
    String filename = "/history.json";

    public void saveHistroy(String name, String type) {
        Calendar calendar = Calendar.getInstance();

        String date = calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DATE);
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        obj.put("date", date);
        obj.put("time", time);
        obj.put("name", name);
        obj.put("type", type);
        //FileOutputStream outputStream;
        File f = new File(Environment.getExternalStorageDirectory() + filename);
        try {
            if (f.isFile()) {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader(Environment.getExternalStorageDirectory() + filename));
                JSONObject object = (JSONObject) obj;
                jsonArray = (JSONArray) object.get("History");
            } else {
                jsonArray = new JSONArray();
            }
            jsonArray.add(obj);
            jsonMain.put("History", jsonArray);
            FileWriter fileWriter = new FileWriter(Environment.getExternalStorageDirectory() + filename);
            fileWriter.write(jsonMain.toJSONString());
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {
            Log.d("debugfor", e.toString());
            e.printStackTrace();
        }
    }

    ArrayList<HistoryInfo> readHistory() {
        //Log.d("aa", "start");
        JSONParser parser = new JSONParser();
        ArrayList<HistoryInfo> historyInfos = new ArrayList<>();
        try {
            Object obj = parser.parse(new FileReader(Environment.getExternalStorageDirectory() + filename));
            JSONObject object = (JSONObject) obj;
            JSONArray array = (JSONArray) object.get("History");
            for (int i = 0; i < array.size(); i++) {
                JSONObject getobject = (JSONObject) array.get(i);
                int type;
                if (getobject.get("type").equals("food")) {
                    type = R.drawable.food_history;
                } else {
                    type = R.drawable.store_history;
                }
                historyInfos.add(new HistoryInfo(type, (String) getobject.get("name"), (String) getobject.get("date"), (String) getobject.get("time")));
            }
        } catch (Exception e) {
            Log.d("debugfor", e.toString());
            e.printStackTrace();
        }
        return historyInfos;
    }
}
