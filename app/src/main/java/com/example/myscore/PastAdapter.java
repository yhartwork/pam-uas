package com.example.myscore;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myscore.model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PastAdapter extends RecyclerView.Adapter<PastAdapter.ViewHolder> {

    ArrayList<Event> pastEventList;
    private Context context;

    public PastAdapter(ArrayList<Event> items, Context context) {
        pastEventList = items;
        this.context = context;
    }

    public void updateData(ArrayList<Event> newData) {
        pastEventList = newData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        SimpleDateFormat month_date = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        String nice_date = pastEventList.get(position).getDateEvent();

        try {
            date = sdf.parse(nice_date);
            nice_date = month_date.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.mItem = pastEventList.get(position);
        holder.mIdView.setText(pastEventList.get(position).getStrEvent());
        holder.mContentView.setText(pastEventList.get(position).getStrVenue());
        holder.mDateView.setText( nice_date + " " + pastEventList.get(position).getStrTime());

        holder.mCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("strEventName", pastEventList.get(position).getStrEvent());
                intent.putExtra("idEvent", pastEventList.get(position).getIdEvent());
                intent.putExtra("StrThumb", pastEventList.get(position).getStrThumb());
                intent.putExtra("IntAwayScore", pastEventList.get(position).getIntAwayScore());
                intent.putExtra("IntHomeScore", pastEventList.get(position).getIntHomeScore());
                intent.putExtra("StrAwayTeam", pastEventList.get(position).getStrAwayTeam());
                intent.putExtra("StrHomeTeam", pastEventList.get(position).getStrHomeTeam());
                intent.putExtra("DateEvent", pastEventList.get(position).getDateEvent());
                intent.putExtra("StrVenue", pastEventList.get(position).getStrVenue());
                intent.putExtra("saved", "false");

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pastEventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final CardView mCardView;
        public final TextView mDateView;

        public Event mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.venue);
            mCardView = (CardView) view.findViewById(R.id.item_event);
            mDateView = (TextView) view.findViewById(R.id.date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}