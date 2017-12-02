package kr.ac.hansung.bababob.Restaurant;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import kr.ac.hansung.bababob.MainActivity;
import kr.ac.hansung.bababob.R;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private Context context;

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
            Bundle bundle = new Bundle();
            bundle.putString("change", "RestaurantInfoActivity");
            bundle.putString("RestaurantName",restaurantName.getText().toString());
            Message message = new Message();
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(v);
    }

    @Override // 뷰홀더의 뷰에 position에 해당하는 데이터를 입력
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        RestaurantItem item = items.get(position);
        Glide.with(context).load(item.getImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                .into(viewHolder.restaurantImg);
        viewHolder.restaurantName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

