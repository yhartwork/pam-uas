package com.example.myscore;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myscore.dummy.DummyContent.DummyItem;
import com.example.myscore.model.Event;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {
    ArrayList<Event> oncomingEventList;
    private Context context;

    public UpcomingAdapter(ArrayList<Event> items, Context context) {
        oncomingEventList = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_upcoming, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = oncomingEventList.get(position);
        holder.mIdView.setText(oncomingEventList.get(position).getStrEvent());
        holder.mContentView.setText(oncomingEventList.get(position).getStrVenue());
        holder.mCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("idEvent", oncomingEventList.get(position).getIdEvent());
                intent.putExtra("StrThumb", oncomingEventList.get(position).getStrThumb());
                intent.putExtra("IntAwayScore", oncomingEventList.get(position).getIntAwayScore());
                intent.putExtra("IntHomeScore", oncomingEventList.get(position).getIntHomeScore());
                intent.putExtra("StrAwayTeam", oncomingEventList.get(position).getStrAwayTeam());
                intent.putExtra("StrHomeTeam", oncomingEventList.get(position).getStrHomeTeam());
                intent.putExtra("DateEvent", oncomingEventList.get(position).getDateEvent());
                intent.putExtra("StrVenue", oncomingEventList.get(position).getStrVenue());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return oncomingEventList.size();
    }

    public void updateData(ArrayList<Event> newData) {
        oncomingEventList = newData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final CardView mCardView;
        public Event mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mCardView = (CardView) view.findViewById(R.id.event_item);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}