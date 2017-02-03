package kidsuke.shoppingapp.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 03-Feb-17.
 */

public class ConverterUtil {
    private static Gson gson = new Gson();

    public static <T> String convertToJson(T objectOfT){
        return gson.toJson(objectOfT);
    }

    public static <T> List<T> convertFromJson(String json, Class<T> classOfT){
        List<T> resultList = new ArrayList<>();
        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);

        if (json != null ) {
            for (int i = 0; i < jsonArray.size(); i++) {
                T objectT = gson.fromJson(jsonArray.get(i), classOfT);
                resultList.add(objectT);
            }
        }
        return resultList;
    }
}
