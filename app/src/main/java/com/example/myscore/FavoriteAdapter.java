package com.example.myscore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myscore.model.Event;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    Context context;
    private ArrayList<Event> listRecyclerItem;

    public FavoriteAdapter(Context ct, ArrayList<Event> listRecyclerItem) {
        context = ct;
        this.listRecyclerItem = listRecyclerItem;
    }

    interface ListItemClickListener {
        void onListItemClick(int position);
    }

    public void updateData(ArrayList<Event> newData) {
        listRecyclerItem = newData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.event_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = (Event) listRecyclerItem.get(position);

        holder.eventName.setText(event.getStrEvent());
        holder.eventVenue.setText(event.getStrVenue());
        holder.eventItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("strEventName", event.getStrEvent());
                intent.putExtra("idEvent", event.getIdEvent());
                intent.putExtra("StrThumb", event.getStrThumb());
                intent.putExtra("IntAwayScore", event.getIntAwayScore());
                intent.putExtra("IntHomeScore", event.getIntHomeScore());
                intent.putExtra("StrAwayTeam", event.getStrAwayTeam());
                intent.putExtra("StrHomeTeam", event.getStrHomeTeam());
                intent.putExtra("DateEvent", event.getDateEvent());
                intent.putExtra("StrVenue", event.getStrVenue());
                intent.putExtra("saved", "true");

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventVenue;
        CardView eventItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.title);
            eventVenue = itemView.findViewById(R.id.venue);
            eventItem = itemView.findViewById(R.id.item_event);
        }
    }
}
