package com.stocker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class ChartjsUtils {

    public static final String VOLUME_LABEL = "Volume";
    public static final String MAX_PRICE_LABEL = "Max Price";
    public static final String MIN_PRICE_LABEL = "Min Price";
    public static final String PRICE_LABEL = "Price";
    public static final String RESISTANCE_LABEL = "Resistance";
    public static final String SUPPORT_LABEL = "Support";

    public static final String FIVE_SMA_LABEL = "Five SMA";
    public static final String TEN_SMA_LABEL = "Ten SMA";
    public static final String FIFTEEN_SMA_LABEL = "Fifteen SMA";
    public static final String TWENTY_SMA_LABEL = "Twenty SMA";
    public static final String TWENTY_FVE_SMA_LABEL = "Twenty FIve SMA";
    public static final String THIRTY_SMA_LABEL = "Thirty SMA";

    public static final String FIVE_EMA_LABEL = "Five EMA";
    public static final String TEN_EMA_LABEL = "Ten EMA";
    public static final String FIFTEEN_EMA_LABEL = "Fifteen EMA";
    public static final String TWENTY_EMA_LABEL = "Twenty EMA";
    public static final String TWENTY_FIVE_EMA_LABEL = "Twenty FIve EMA";
    public static final String THIRTY_EMA_LABEL = "Thirty EMA";

    public static final String MACD_LINE = "MACD Line";
    public static final String MACD_SIGNAL = "MACD Signal";

    public static final String VOLUME_Y_AXIS = "volume";
    public static final String PRICE_Y_AXIS = "price";
    public static final String OSCILLATOR_Y_AXIS = "oscillator";

    public static final String TYPE_LINE = "line";
    public static final String TYPE_BAR = "bar";

    public static JSONObject createBarChartData(String label, String yAxis, List data) {
        return createChartData(label, yAxis, TYPE_BAR, data);
    }

    public static JSONObject createLineChartData(String label, String yAxis, List data) {
        return createChartData(label, yAxis, TYPE_LINE, data);
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

        JSONArray array = new JSONArray();
        array.addAll(data);
        result.put("data", data);

        return result;
    }
}
