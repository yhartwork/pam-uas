package com.example.myscore;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myscore.dummy.DummyContent.DummyItem;
import com.example.myscore.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    ArrayList<Event> favoriteEventList;
    private Context context;

    public FavoriteAdapter(ArrayList<Event> items, Context context) {
        favoriteEventList = items;
        this.context = context;
    }
    public void updateData(ArrayList<Event> newData) {
        favoriteEventList = newData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = favoriteEventList.get(position);
        holder.mIdView.setText(favoriteEventList.get(position).getStrEvent());
        holder.mContentView.setText(favoriteEventList.get(position).getStrVenue());
    }

    @Override
    public int getItemCount() {
        return favoriteEventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Event mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}