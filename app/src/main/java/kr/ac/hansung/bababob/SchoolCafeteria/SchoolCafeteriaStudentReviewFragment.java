package kr.ac.hansung.bababob.SchoolCafeteria;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.hansung.bababob.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolCafeteriaStudentReviewFragment extends Fragment {


    private static SchoolCafeteriaStudentReviewFragment instance;

    public SchoolCafeteriaStudentReviewFragment() {
        // Required empty public constructor
    }

    public static SchoolCafeteriaStudentReviewFragment getInstance(){
        if(instance == null)
            instance = new SchoolCafeteriaStudentReviewFragment();
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_school_cafeteria_review, container, false);
    }

}
