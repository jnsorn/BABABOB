package kr.ac.hansung.bababob.Restaurant;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kr.ac.hansung.bababob.MainActivity;
import kr.ac.hansung.bababob.R;
import kr.ac.hansung.bababob.TimelineAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantReviewFragment extends Fragment {


    private static RestaurantReviewFragment instance;
    private String restaurantName;
    private Button write_btn;
    private RecyclerView rvReview;
    private TimelineAdapter adapter;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_restaurant_review, container, false);

        write_btn = (Button)view.findViewById(R.id.write_btn);
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

        rvReview = (RecyclerView) view.findViewById(R.id.restaurant_review_recycler_view);
        adapter = new TimelineAdapter(getActivity(), restaurantName);
        rvReview.setAdapter(adapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(500);
        rvReview.setItemAnimator(itemAnimator);
        return view;
    }

}
