package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by colin.hegarty on 23/06/2017.
 */

public class NetworkUtils {

//    final static String API_KEY = BuildConfig.MY_MOVIEDB_API_KEY;

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static URL buildUrl(String query){
        Uri builtUri = Uri.parse(query).buildUpon().build();

        URL resultUrl = null;
        try{
            resultUrl = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return resultUrl;
    }

    public static String getResponseFromHttpURL(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");
            //TODO-2 REQUIREMENT Move string literals to strings.xml or use constants as appropriate
            boolean hasInput = sc.hasNext();
            if(hasInput){
                return sc.next();
            }else {
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }
}
