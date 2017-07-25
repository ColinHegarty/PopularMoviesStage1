package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 25-Jul-17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{
    private static final String TAG = MovieAdapter.class.getSimpleName();

    private Context context;

    private ArrayList<String> listOfTrailers;

    private int mNumberItems;

    private final TrailerAdapterOnClickListener onClickHandler;

    public TrailerAdapter(TrailerAdapterOnClickListener onClickHandler, ArrayList<String> listOfTrailers){
        this.listOfTrailers = listOfTrailers;
        this.mNumberItems = listOfTrailers.size();
        this.onClickHandler = onClickHandler;
    }

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        TrailerAdapter.TrailerViewHolder viewHolder = new TrailerAdapter.TrailerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }



    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView trailerHolder;


        public TrailerViewHolder(View itemView){
            super(itemView);
            trailerHolder = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex){
            trailerHolder.setText(context.getString(R.string.movie_trailer) + context.getString(R.string.space) +(listIndex+1));
        }

        @Override
        public void onClick(View v) {
            onClickHandler.onClick(getAdapterPosition());
        }
    }

    public interface TrailerAdapterOnClickListener{
        void onClick(int position);
    }
}
