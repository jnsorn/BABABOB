package kr.ac.hansung.bababob;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView restaurantImg;
        public TextView restaurantName;

        public ViewHolder(View itemView) {
            super(itemView);
            restaurantImg = (ImageView) itemView.findViewById(R.id.restaurantImg);
            restaurantName = (TextView) itemView.findViewById(R.id.restaurantName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("RestaurantName",restaurantName.getText().toString());
            bundle.putString("message", "showInfoActivity");
            message.setData(bundle);
            MainActivity.myHandler.sendMessage(message);
        }
    }

    public RestaurantAdapter() {
    }

    List<RestaurantItem> items = new ArrayList<>();

    public void add(RestaurantItem data) {
        items.add(data);
        notifyDataSetChanged();
    }

    /* viewHolder를 어떻게 생성할 것인지?*/
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(v);
    }

    @Override // 뷰홀더의 뷰에 position에 해당하는 데이터를 입력
    public void onBindViewHolder(RestaurantAdapter.ViewHolder viewHolder, int position) {
        RestaurantItem item = items.get(position);
        //viewHolder.restaurantImg.setImageResource();
        viewHolder.restaurantName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
