package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JsonReader {
    public JSONObject jsonReader() throws IOException {
        File file = new File("resources/TestData/testdata.json");
        String json;

        json = FileUtils.readFileToString(file, "UTF-8");
        Object obj = JSONValue.parse(json);
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }

    public String gettestdata(String input) throws IOException {
        String data;
        data = (String) jsonReader().get(input);
        return data;
    }

    public JSONArray getjsonarray(String key) throws IOException {
        JSONObject array = jsonReader();
        JSONArray jsonarray = (JSONArray) array.get(key);
        return jsonarray;
    }

    public Object getjsonarraydata(String key, int index) throws IOException {
        JSONArray array = getjsonarray(key);
        Object data = array.get(index);
        return data;

    }

}