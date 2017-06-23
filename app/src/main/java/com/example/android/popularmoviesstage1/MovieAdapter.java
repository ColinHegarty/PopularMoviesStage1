package com.example.android.popularmoviesstage1;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by colin.hegarty on 23/06/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    private int mNumberItems;

    public MovieAdapter(int numberOfItems, ListItemClickListener listener){
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        MovieViewHolder viewHolder = new MovieViewHolder(view);

        viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);

        viewHolderCount++;

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

        public MovieViewHolder(View itemView){
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.movie_item_list);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex){listItemNumberView.setText(String.valueOf(listIndex));}

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
