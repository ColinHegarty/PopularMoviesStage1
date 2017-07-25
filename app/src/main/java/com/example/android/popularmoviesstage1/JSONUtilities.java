package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public static ArrayList<String> jsonParserReviews(String JSONResults, Context context, String query, String result){
        JSONObject results = null;
        JSONArray reviews = null;
        Log.e("JSON Results:", JSONResults);
        ArrayList<String> listOfReviews = new ArrayList<String>();
        try{
            results = new JSONObject(JSONResults);
            reviews = results.getJSONArray(context.getString(R.string.json_results));
            //TODO-2 REQUIREMENT Move string literals to strings.xml or use constants as appropriate DONE
            for(int i=0; i<reviews.length(); i++){
                Log.e("Inside For loop", ""+query + result);
                String review = reviews.getJSONObject(i).getString(query) + "\n"+ "\n" +
                        reviews.getJSONObject(i).getString(result)+ "\n"+ "\n";
                Log.e("Review", review);
                listOfReviews.add(review);
                Log.e("listOfReviews Size:", ""+ listOfReviews.size());
            }
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return listOfReviews;
    }

    public static ArrayList<String> jsonParserTrailers(String JSONResults, Context context, String query){
        JSONObject results = null;
        JSONArray trailers = null;
        Log.e("JSON Results:", JSONResults);
        ArrayList<String> listOfTrailers = new ArrayList<String>();
        try{
            results = new JSONObject(JSONResults);
            trailers = results.getJSONArray(context.getString(R.string.json_youtube));
            //TODO-2 REQUIREMENT Move string literals to strings.xml or use constants as appropriate DONE
            for(int i=0; i<trailers.length(); i++){
                Log.e("Inside For loop", ""+query );
                String trailer = trailers.getJSONObject(i).getString(context.getString(R.string.json_source)) + "\n";
                Log.e("Trailer", trailer);
                listOfTrailers.add(trailer);
                Log.e("listOfReviews Size:", ""+ listOfTrailers.size());
            }
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return listOfTrailers;
    }
}
