package kr.ac.hansung.bababob.Restaurant;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import kr.ac.hansung.bababob.MainActivity;
import kr.ac.hansung.bababob.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantReviewFragment extends Fragment {


    private static RestaurantReviewFragment instance;
    private String restaurantName;
    private Button write_btn;

    public RestaurantReviewFragment() {
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;

    }
    public static RestaurantReviewFragment getInstance() {
        if (instance == null)
            instance = new RestaurantReviewFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_restaurant_review, container, false);

        write_btn = (Button)rootView.findViewById(R.id.write_btn);
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("change", "ReviewWriteActivity");
                bundle.putString("RestaurantName",restaurantName);
                Message message = new Message();
                message.setData(bundle);
                MainActivity.myHandler.sendMessage(message);
            }
        });
        return rootView;



    }

}
