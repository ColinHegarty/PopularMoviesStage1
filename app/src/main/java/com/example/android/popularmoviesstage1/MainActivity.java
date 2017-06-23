package com.example.android.popularmoviesstage1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener{
    private TextView defaultTextView;
    private Movie[] listOfMovies;

    private RecyclerView moviePosterView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultTextView = (TextView) findViewById(R.id.defaultTextViw);
        defaultTextView.setText(R.string.welcome);
        moviePosterView = (RecyclerView)  findViewById(R.id.rv_movieposter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        moviePosterView.setLayoutManager(layoutManager);
        moviePosterView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(20, this);
        moviePosterView.setAdapter(movieAdapter);
//        networkUtilsOperations("popular");
    }

    public void networkUtilsOperations(String query){
        URL resultUrl = NetworkUtils.buildUrl(getString(R.string.connection_string) + query
                + "?api_key=" + getString(R.string.api_key));
        new MovieDatabaseQuery().execute(resultUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.popular:
                                Toast.makeText(this, getResources().getString(R.string.toast_popular),
                                        Toast.LENGTH_SHORT).show();
                                networkUtilsOperations("popular");
                                break;
            case R.id.top_rated:
                                Toast.makeText(this, getResources().getString(R.string.toast_top),
                                        Toast.LENGTH_SHORT).show();
                                networkUtilsOperations("top_rated");
                                break;
        }

        return true;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Toast.makeText(this, "You clicked: "+ clickedItemIndex, Toast.LENGTH_LONG).show();
    }

    public class MovieDatabaseQuery extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String JSONResults = null;
            try{
                JSONResults = NetworkUtils.getResponseFromHttpURL(searchUrl);
            }catch(IOException e){
                e.printStackTrace();
            }

            if(JSONResults != null && !JSONResults.equals("")){
                listOfMovies = JSONUtilities.jsonParser(JSONResults);
            }

            return JSONResults;
        }

        @Override
        protected void onPostExecute(String JSONResults){
            defaultTextView.setText(listOfMovies[0].getTitle());
        }
    }
}
