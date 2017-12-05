package kr.ac.hansung.bababob.SchoolCafeteria;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    public static ArrayList<SchoolCafeteriaMenu> schoolCafeteriaStudentMenus = new ArrayList<SchoolCafeteriaMenu>();
    public static SchoolCafeteriaProfessorMenu[] schoolCafeteriaProfessorMenus= new SchoolCafeteriaProfessorMenu[5];

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

        //SchoolCafeteriaStudent
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

        setSchoolCafeteriaProfessorMenus();
    }

    public void setSchoolCafeteriaProfessorMenus(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("http://www.hansung.ac.kr/web/www/life_03_01_t2").get();
                    Element tbody = doc.select("table").first();
                    Elements rows = tbody.select("tr");

                    for(int i=0; i<5; i++) {
                        Elements tds = rows.get(1).select("td");
                        String str = tds.get(i).text();
                        String tmp = "";
                        for (String string : str.split(" ")) {
                            tmp += string + " / ";
                        }
                        schoolCafeteriaProfessorMenus[i] = new SchoolCafeteriaProfessorMenu();
                        schoolCafeteriaProfessorMenus[i].setLunch(new SchoolCafeteriaMenu(tmp, 4500));
                    }

                    for(int i=0; i<5; i++) {
                        Elements tds = rows.get(2).select("td");
                        String str = tds.get(i+1).text();
                        String tmp = "";
                        for (String string : str.split(" ")) {
                            if(!string.equals(""))
                                tmp += string + " / ";
                        }
                        schoolCafeteriaProfessorMenus[i].setDinner(new SchoolCafeteriaMenu(tmp, 4500));
                    }

                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }
            }
        }).start();
    }
}
