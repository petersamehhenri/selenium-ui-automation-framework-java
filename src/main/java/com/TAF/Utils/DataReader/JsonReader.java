package com.TAF.Utils.DataReader;

import com.TAF.Utils.Logs.LogsManager;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {
    String jsonReader;
    String jsonFileName;
    private final String Test_data_path = System.getProperty("user.dir") + "/src/test/resources/Test-Data/";

    public JsonReader(String jsonFileName) {
        this.jsonFileName = jsonFileName;
        try {
            JSONObject data = (JSONObject) new JSONParser()
                    .parse(new FileReader(Test_data_path + jsonFileName + ".json"));
            jsonReader = data.toString();
        } catch (Exception e) {
            LogsManager.error("Error reading json file" ,jsonFileName,e.getMessage());
            // Intialize to empty JSON to avoid null pointer exceptions
            jsonReader = "{}";
        }
    }

    public String getJsonData(String jsonPath) {
        try {
            return JsonPath.read(jsonReader, jsonPath);
        } catch (Exception e) {
            LogsManager.error("Error reading File for path",jsonPath,e.getMessage());
            return "";
        }
    }
}