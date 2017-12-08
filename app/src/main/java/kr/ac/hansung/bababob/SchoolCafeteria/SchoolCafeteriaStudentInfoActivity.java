package kr.ac.hansung.bababob.SchoolCafeteria;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kr.ac.hansung.bababob.R;

public class SchoolCafeteriaStudentInfoActivity extends AppCompatActivity {

    public static ArrayList<SchoolCafeteriaMenu> cafeteriaStudentMenusNoodles = new ArrayList<SchoolCafeteriaMenu>();
    public static ArrayList<SchoolCafeteriaMenu> cafeteriaStudentMenusBab = new ArrayList<SchoolCafeteriaMenu>();
    public static ArrayList<SchoolCafeteriaMenu> cafeteriaStudentMenusFry = new ArrayList<SchoolCafeteriaMenu>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        setContentView(R.layout.activity_school_cafeteria_info);
        Intent intent = getIntent();
        int cafeteriaName = intent.getIntExtra("CafeteriaName",SchoolCafeteria.STUDENT_CAFETERIA);

        TextView name = (TextView) findViewById(R.id.cafeteria_name);
        name.setText(SchoolCafeteria.SCHOOLCAFETERIA[cafeteriaName]);

        SchoolCafeteriaStudentPagerAdapter mPagerAdapter = new SchoolCafeteriaStudentPagerAdapter(getSupportFragmentManager(), SchoolCafeteria.SCHOOLCAFETERIA[cafeteriaName]);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.cafeteria_viewpager);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout mTab = (TabLayout) findViewById(R.id.cafeteria_tabs);
        mTab.setupWithViewPager(mViewPager);

        getDataFromFirebase();
    }

    public void getDataFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("SchoolCafeteriaStudent").child("RollNoodles");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cafeteriaStudentMenusNoodles.clear();
                for(DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                    SchoolCafeteriaMenu schoolCafeteriaMenu = messageSnapshot.getValue(SchoolCafeteriaMenu.class);
                    cafeteriaStudentMenusNoodles.add(schoolCafeteriaMenu);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference ref2 = database.getReference("SchoolCafeteriaStudent").child("Bab");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cafeteriaStudentMenusBab.clear();
                for(DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                    SchoolCafeteriaMenu schoolCafeteriaMenu = messageSnapshot.getValue(SchoolCafeteriaMenu.class);
                    cafeteriaStudentMenusBab.add(schoolCafeteriaMenu);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference ref3 = database.getReference("SchoolCafeteriaStudent").child("FryRice");
        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cafeteriaStudentMenusFry.clear();
                for(DataSnapshot messageSnapshot: dataSnapshot.getChildren()){
                    SchoolCafeteriaMenu schoolCafeteriaMenu = messageSnapshot.getValue(SchoolCafeteriaMenu.class);
                    cafeteriaStudentMenusFry.add(schoolCafeteriaMenu);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
