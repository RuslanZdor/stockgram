package com.stocker;

import com.stocker.yahoo.data.Day;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class ChartjsUtils {

    public static final String VOLUME_LABEL = "Volume";
    public static final String PRICE_LABEL = "Price";
    public static final String RESISTANCE_LABEL = "Resistance";
    public static final String SUPPORT_LABEL = "Support";

    public static final String SMA_5_LABEL = "SMA 5";
    public static final String SMA_10_LABEL = "SMA 10";
    public static final String SMA_20_LABEL = "SMA 20";
    public static final String SMA_50_LABEL = "SMA 50";
    public static final String SMA_200_LABEL = "SMA 200";

    public static final String EMA_5_LABEL = "EMA 5";
    public static final String EMA_10_LABEL = "EMA 10";
    public static final String EMA_20_LABEL = "EMA 20";
    public static final String EMA_50_LABEL = "EMA 50";
    public static final String EMA_200_LABEL = "EMA 200";

    public static final String MACD_LINE = "MACD Line";
    public static final String MACD_SIGNAL = "MACD Signal";

    public static final String RSI5_LINE = "5 RSI";
    public static final String RSI30_LINE = "30 RSI";

    public static final String VOLUME_Y_AXIS = "volume";
    public static final String PRICE_Y_AXIS = "price";
    public static final String OSCILLATOR_Y_AXIS = "oscillator";

    private static final String TYPE_LINE = "line";
    private static final String TYPE_BAR = "bar";
    private static final String TYPE_CANDLESTICK = "candlestick";

    public static JSONObject createBarChartData(String label, String yAxis, List data) {
        return createChartData(label, yAxis, TYPE_BAR, data);
    }

    public static JSONObject createLineChartData(String label, String yAxis, List data) {
        return createChartData(label, yAxis, TYPE_LINE, data);
    }

    public static JSONObject createCandleStickChartData(String label, String yAxis, List<Day> data) {
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (Day day : data) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("t", day.getDate().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
            jsonObject.put("o", day.getOpenPrice());
            jsonObject.put("h", day.getMaxPrice());
            jsonObject.put("l", day.getMinPrice());
            jsonObject.put("c", day.getPrice());
            jsonObjects.add(jsonObject);
        }
        return createChartData(label, yAxis, TYPE_CANDLESTICK, jsonObjects);
    }

    public static JSONObject hide(JSONObject object) {
        object.put("hidden", true);
        return object;
    }

    public static JSONObject pointRadius(JSONObject object, int value) {
        object.put("pointRadius", value);
        return object;
    }

    public static JSONObject createDashChartData(String label, String yAxis, List data) {
        JSONObject result = createChartData(label, yAxis, TYPE_LINE, data);
        result.put("pointRadius", 0);
        JSONArray dash = new JSONArray();
        dash.add(5);
        dash.add(5);
        result.put("borderDash", dash);
        return result;
    }

    public static JSONObject addColor(JSONObject object, List data) {
        JSONArray array = new JSONArray();
        array.addAll(data);
        object.put("backgroundColor", array);
        return object;
    }

    public static JSONObject addColor(JSONObject object, String data) {
        object.put("borderColor", data);
        return object;
    }

    private static JSONObject createChartData(String label, String yAxis, String type, List data) {
        JSONObject result = new JSONObject();
        result.put("label", label);
        result.put("fill", false);
        result.put("yAxisID", yAxis);
        result.put("type", type);
        result.put("data", data);
        return result;
    }
}
