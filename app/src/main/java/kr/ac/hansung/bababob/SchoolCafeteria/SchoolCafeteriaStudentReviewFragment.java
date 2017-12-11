package kr.ac.hansung.bababob.SchoolCafeteria;


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
import com.google.firebase.database.FirebaseDatabase;

import kr.ac.hansung.bababob.MainActivity;
import kr.ac.hansung.bababob.R;
import kr.ac.hansung.bababob.TimelineAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolCafeteriaStudentReviewFragment extends Fragment {

    private Button cafeteria_review_btn;
    private RecyclerView rvReview;
    private TimelineAdapter adapter;
    private FirebaseAuth mAuth;

    private static SchoolCafeteriaStudentReviewFragment instance;

    public SchoolCafeteriaStudentReviewFragment() {
        // Required empty public constructor
    }

    public static SchoolCafeteriaStudentReviewFragment getInstance() {
        if (instance == null)
            instance = new SchoolCafeteriaStudentReviewFragment();
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

        View view = inflater.inflate(R.layout.fragment_school_cafeteria_review, container, false);
        cafeteria_review_btn = (Button) view.findViewById(R.id.cafeteria_review_btn);
        cafeteria_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("change", "ReviewWriteActivity");
                bundle.putString("RestaurantName", "학생식당");
                Message message = new Message();
                message.setData(bundle);
                MainActivity.myHandler.sendMessage(message);
            }
        });

        rvReview = (RecyclerView) view.findViewById(R.id.cafeteria_review_recycler_view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter = new TimelineAdapter(getActivity(), "학생식당");
        rvReview.setAdapter(adapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(500);
        rvReview.setItemAnimator(itemAnimator);
    }
}
