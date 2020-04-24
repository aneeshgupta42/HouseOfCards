package ooga.Controller;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONUtil {
    public static Map<String, Object> getData(String dataRequestedFor, String path) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(path));
        JSONObject jo = (JSONObject) obj;
        Map m = (Map)jo.get(dataRequestedFor);
        return m;
    }

    public static void modifyField(String dataToBeModified, String newData, String path) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(path));
        JSONObject root = (JSONObject) obj;
        root.put(dataToBeModified, newData);
//        for (Object s : root.keySet()){
//            System.out.println(s.toString());
//            System.out.println(root.get(s).toString());
//        }

       // System.out.println(root.toJSONString());

        FileWriter file = new FileWriter(path);
        file.write(root.toJSONString());
        file.flush();
    }


}
