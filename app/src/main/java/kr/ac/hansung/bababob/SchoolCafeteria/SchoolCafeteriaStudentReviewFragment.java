package kr.ac.hansung.bababob.SchoolCafeteria;


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
public class SchoolCafeteriaStudentReviewFragment extends Fragment {

    private Button cafeteria_review_btn;

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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_school_cafeteria_review, container, false);
        cafeteria_review_btn = (Button) rootView.findViewById(R.id.cafeteria_review_btn);
        cafeteria_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("change", "ReviewWriteActivity");
                bundle.putString("RestaurantName","학생식당");
                Message message = new Message();
                message.setData(bundle);
                MainActivity.myHandler.sendMessage(message);
            }
        });
        return rootView;
    }

}
