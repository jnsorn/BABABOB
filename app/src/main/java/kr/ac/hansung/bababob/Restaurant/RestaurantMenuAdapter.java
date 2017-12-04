package kr.ac.hansung.bababob.Restaurant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.hansung.bababob.R;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.ViewHolder> {

    public Context context;
    List<RestaurantMenuItem> items = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView restaurant_menu, restaurant_menu_price;


        public ViewHolder(View itemView) {
            super(itemView);
            restaurant_menu = (TextView) itemView.findViewById(R.id.restaurant_menu);
            restaurant_menu_price = (TextView) itemView.findViewById(R.id.restaurant_menu_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public void add(RestaurantMenuItem data) {
        items.add(data);
        notifyDataSetChanged();
    }


    @Override
    public RestaurantMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_menu_item, parent, false);
        return new RestaurantMenuAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RestaurantMenuItem item = items.get(position);
        Log.i("dddddddddddddddddddd", item.getMenu());
        Log.i("dddddddddddddddddddd", ""+item.getPrice());
        holder.restaurant_menu.setText(item.getMenu());
        holder.restaurant_menu_price.setText(Integer.toString(item.getPrice()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
