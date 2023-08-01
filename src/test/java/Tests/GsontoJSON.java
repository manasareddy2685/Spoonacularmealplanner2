package Tests;
import com.google.gson.Gson;
public class GsontoJSON {

    public static String convertToJSON(Object obj) {
        return (new Gson().toJson(obj));
    }
}

