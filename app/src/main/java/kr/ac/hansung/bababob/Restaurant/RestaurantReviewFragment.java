package kr.ac.hansung.bababob.Restaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.hansung.bababob.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantReviewFragment extends Fragment {

    private static RestaurantReviewFragment instance;
    private String restaurantName;
    public RestaurantReviewFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_review, container, false);
    }

}
