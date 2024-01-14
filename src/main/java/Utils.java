import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Utils {
    private final static Gson gson = new Gson();

    public static String saveToJson(ArrayList<ArrayList<LinePoint>> lines) {
        return gson.toJson(lines);
    }

    public static ArrayList<ArrayList<LinePoint>> readFromJson(String json) {
        return gson.fromJson(json, new TypeToken<ArrayList<ArrayList<LinePoint>>>() {}.getType());
    }
}
