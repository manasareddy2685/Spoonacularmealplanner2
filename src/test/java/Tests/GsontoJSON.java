package Tests;
import com.google.gson.Gson;

import java.util.HashMap;

public class GsontoJSON {

    public static String convertToJSON(HashMap<String, Object> obj) {
        return (new Gson().toJson(obj));
    }


}

