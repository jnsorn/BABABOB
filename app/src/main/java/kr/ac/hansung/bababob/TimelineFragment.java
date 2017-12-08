package kr.ac.hansung.bababob;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.ac.hansung.bababob.SchoolCafeteria.SchoolCafeteria;
import kr.ac.hansung.bababob.SchoolCafeteria.SchoolCafeteriaAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {

    private static TimelineFragment instance;
    private DatabaseReference mPostReference;
    private RecyclerView rvReview;
    private TimelineAdapter adapter;

    public TimelineFragment() {
        // Required empty public constructor
    }

    public static TimelineFragment getInstance(){
        if(instance == null)
            instance = new TimelineFragment();
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPostReference = FirebaseDatabase.getInstance().getReference("Review");
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        rvReview = (RecyclerView) view.findViewById(R.id.timeline_recycler_view);
        adapter = new TimelineAdapter(getActivity(), mPostReference);
        rvReview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
