package kr.ac.hansung.bababob.SchoolCafeteria;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.hansung.bababob.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolCafeteriaStudentMenuFragment extends Fragment {

    private RecyclerView rvSchoolCafeteria;
    private SchoolCafeteriaStudentAdapter adapter;

    private static SchoolCafeteriaStudentMenuFragment instance;

    public SchoolCafeteriaStudentMenuFragment() {
        // Required empty public constructor
    }

    public static SchoolCafeteriaStudentMenuFragment getInstance(){
        if(instance == null)
            instance = new SchoolCafeteriaStudentMenuFragment();
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_cafeteria_menu, container, false);
        rvSchoolCafeteria = (RecyclerView) view.findViewById(R.id.cafeteria_student_recycler_view);
        adapter = new SchoolCafeteriaStudentAdapter(getActivity());
        rvSchoolCafeteria.setAdapter(adapter);
        return view;
    }

}
