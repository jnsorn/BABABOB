package kr.ac.hansung.bababob.SchoolCafeteria;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import kr.ac.hansung.bababob.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolCafeteriaFragment extends Fragment {

    private static SchoolCafeteriaFragment instance;
    private RecyclerView rvSchoolCafeteria;
    private SchoolCafeteriaAdapter adapter;
    public static ArrayList<SchoolCafeteriaMenu> schoolCafeteriaStudentMenus = new ArrayList<SchoolCafeteriaMenu>();

    public SchoolCafeteriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SchoolCafeteriaFragment getInstance(){
        if(instance == null)
            instance = new SchoolCafeteriaFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_cafeteria, container, false);
        rvSchoolCafeteria = (RecyclerView) view.findViewById(R.id.school_cafeteria_recycler_view);
        adapter = new SchoolCafeteriaAdapter(getActivity(), SchoolCafeteria.getCafeterias());
        rvSchoolCafeteria.setAdapter(adapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(500);
        rvSchoolCafeteria.setItemAnimator(itemAnimator);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //SchoolCafeteriaStudent
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("SchoolCafeteriaStudent").child("RollNoodles");
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
        adapter.notifyDataSetChanged();
    }
}
