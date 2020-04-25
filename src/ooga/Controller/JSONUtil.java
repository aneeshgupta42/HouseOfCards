package ooga.Controller;


import org.json.simple.JSONArray;
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
        if (dataRequestedFor.equals("")){
            return jo;
        }
        Map m = (Map)jo.get(dataRequestedFor);
        return m;
    }

    public static void modifyHighScore(String gameName, String playerName, String newScore, String path) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(path));
        JSONObject root = (JSONObject) obj;
        Map m = new LinkedHashMap(1);
        m.put(playerName, newScore);
        root.put(gameName, m);
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
