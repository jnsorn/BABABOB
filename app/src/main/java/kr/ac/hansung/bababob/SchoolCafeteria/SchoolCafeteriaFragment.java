package kr.ac.hansung.bababob.SchoolCafeteria;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kr.ac.hansung.bababob.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolCafeteriaFragment extends Fragment {

    private static SchoolCafeteriaFragment instance;
    private RecyclerView rvSchoolCafeteria;
    public static ArrayList<SchoolCafeteriaMenu> schoolCafeteriaStudentMenus = new ArrayList<SchoolCafeteriaMenu>();

    public SchoolCafeteriaFragment() {
        // Required empty public constructor
    }

    public static SchoolCafeteriaFragment getInstance(){
        if(instance == null)
            instance = new SchoolCafeteriaFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_cafeteria, container, false);
        rvSchoolCafeteria = (RecyclerView) view.findViewById(R.id.school_cafeteria_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SchoolCafeteriaAdapter adapter = new SchoolCafeteriaAdapter(getActivity(),SchoolCafeteria.getCafeterias());
        rvSchoolCafeteria.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("SchoolCafeteriaStudent").child("Menu");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                schoolCafeteriaStudentMenus.clear();
                for(DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                    SchoolCafeteriaMenu schoolCafeteriaMenu = messageSnapshot.getValue(SchoolCafeteriaMenu.class);
                    schoolCafeteriaStudentMenus.add(schoolCafeteriaMenu);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
