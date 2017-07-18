package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by colin.hegarty on 17/07/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();

    private Context context;

    private ArrayList<String> listOfReviews;

    private int mNumberItems;



    public ReviewAdapter(ArrayList<String> listOfReviews){
        mNumberItems = listOfReviews.size();
        this.listOfReviews = listOfReviews;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        ReviewAdapter.ReviewViewHolder viewHolder = new ReviewAdapter.ReviewViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView reviewHolder;


        public ReviewViewHolder(View itemView){
            super(itemView);
            reviewHolder = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
        }

        void bind(int listIndex){
            reviewHolder.setText(listOfReviews.get(listIndex));
        }

    }
}
