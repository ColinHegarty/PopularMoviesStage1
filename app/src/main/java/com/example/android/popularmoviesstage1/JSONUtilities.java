package com.example.android.popularmoviesstage1;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by colin.hegarty on 23/06/2017.
 */

public class JSONUtilities {

    public static Movie[] jsonParser(String JSONResults, Context context){
        JSONObject results = null;
        JSONArray movie = null;
        Movie[] listOfMovies = new Movie[20];
        try{
            results = new JSONObject(JSONResults);
            movie = results.getJSONArray(context.getString(R.string.json_results));
            //TODO-2 REQUIREMENT Move string literals to strings.xml or use constants as appropriate DONE
            for(int i=0; i<movie.length(); i++){
                Movie mov = new Movie(movie.getJSONObject(i), context);
                listOfMovies[i] = mov;
            }
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return listOfMovies;
    }

    public static String[] jsonParserReviews(String JSONResults, Context context, String query, String result){
        JSONObject results = null;
        JSONArray reviews = null;
        String[] listOfReviews = new String[20];
        try{
            results = new JSONObject(JSONResults);
            reviews = results.getJSONArray(context.getString(R.string.json_results));
            //TODO-2 REQUIREMENT Move string literals to strings.xml or use constants as appropriate DONE
            for(int i=0; i<listOfReviews.length; i++){
                String review = reviews.getJSONObject(i).getString(query) + "\n" +
                        reviews.getJSONObject(i).getString(result);
                listOfReviews[i] = review;
            }
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return listOfReviews;
    }
}
