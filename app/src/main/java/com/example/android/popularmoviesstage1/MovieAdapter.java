package com.example.android.popularmoviesstage1;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by colin.hegarty on 23/06/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private Context context;

    final private ListItemClickListener mOnClickListener;

    private Movie[] listOfMovies;

    private int mNumberItems;

    public MovieAdapter(Movie[] listOfMovies, ListItemClickListener listener){
        mNumberItems = listOfMovies.length;
        mOnClickListener = listener;
        this.listOfMovies = listOfMovies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView listItemNumberView;

        TextView viewHolderIndex;

        ImageView posterView;

        public MovieViewHolder(View itemView){
            super(itemView);

            posterView = (ImageView) itemView.findViewById(R.id.movie_posters);
            listItemNumberView = (TextView) itemView.findViewById(R.id.movie_item_list);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex){
            Picasso.with(context).load(context.getResources().getString(R.string.poster_prefix) + listOfMovies[listIndex].getPosterPath()).into(posterView);
        }

        @Override
        public void onClick(View v){
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }
}
