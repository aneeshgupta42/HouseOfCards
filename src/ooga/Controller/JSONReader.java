package ooga.Controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONReader {
    public static Map<String, Object> getData(String dataRequestedFor, String path) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(path));
        JSONObject jo = (JSONObject) obj;
        Map m = (Map)jo.get(dataRequestedFor);
        return m;
    }


}
