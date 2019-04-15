package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    /**
     * This method parses JSON and returns an Sandwich Object
     * containing the different properties of a sandwich.
     *
     * @param json JSON string
     * @return Sandwich object describing an sandwich
     * @throws JSONException If JSON data cannot be properly parsed
     */

    public static Sandwich parseSandwichJson(String json){

        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";

        try {
            /* Get the JSON object representing the sandwich */
            JSONObject jsonObject = new JSONObject(json);

            /* Gen the JSON name object representing all the sandwich names*/
            JSONObject name = jsonObject.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);
            JSONArray alsoKnowsAsJsonArray = name.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnowsAs = new ArrayList<>();

            for (int i = 0; i < alsoKnowsAsJsonArray.length(); i++) {
                alsoKnowsAs.add(alsoKnowsAsJsonArray.get(i).toString());
            }


            String placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN);
            String description = jsonObject.getString(DESCRIPTION);
            String image = jsonObject.getString(IMAGE);


            /*Get a JsonArray of all the ingredients of the sandwich*/
            JSONArray ingredientsJson = jsonObject.getJSONArray(INGREDIENTS);
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJson.length(); i++) {
                ingredients.add(ingredientsJson.get(i).toString());
            }

            return new Sandwich(mainName, alsoKnowsAs, placeOfOrigin, description, image, ingredients);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }


}
