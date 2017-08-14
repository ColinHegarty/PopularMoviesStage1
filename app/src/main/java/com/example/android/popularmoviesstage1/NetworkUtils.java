package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public static String getResponseFromHttpURL(URL url, Context context) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner sc = new Scanner(in);
            sc.useDelimiter(context.getString(R.string.parser_delimeter));
            //TODO-2 REQUIREMENT Move string literals to strings.xml or use constants as appropriate DONE
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

    //Method taken from Stackoverflow
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    //TODO-3 AWESOME These small touches can make a big difference in UX and UR (User Rating)
}
