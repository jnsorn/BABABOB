package kr.ac.hansung.bababob.Restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import com.google.firebase.database.*;

import kr.ac.hansung.bababob.MainActivity;
import kr.ac.hansung.bababob.MyPagerAdapter;
import kr.ac.hansung.bababob.R;

public class RestaurantInfoActivity extends AppCompatActivity  {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String restaurantName;
    private Boolean delivery, takeout;
    private TextView time_tv, infoName, takeout_tv, delivery_tv;
    private ImageView takeout_img, delivery_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        Intent intent = getIntent();
        restaurantName = intent.getStringExtra("RestaurantName");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurant").child(restaurantName);
        infoName = (TextView) findViewById(R.id.infoName);
        takeout_img = (ImageView) findViewById(R.id.takeout_img);
        delivery_img = (ImageView) findViewById(R.id.delivery_img);
        time_tv = (TextView) findViewById(R.id.time_tv);
        delivery_tv = (TextView) findViewById(R.id.delivery_tv);
        takeout_tv = (TextView) findViewById(R.id.takeout_tv);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                takeout = snapshot.child("takeout").getValue(Boolean.class);
                delivery = snapshot.child("delivery").getValue(Boolean.class);
                time_tv.setText(snapshot.child("time").getValue(String.class));

                if (takeout) {
                    takeout_img.setImageResource(R.drawable.takeout_ok);
                    takeout_tv.setText("포장 가능");
                }
                if (delivery) {
                    delivery_img.setImageResource(R.drawable.delivery_ok);
                    delivery_tv.setText("배달 가능");
                }

            }
//
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        infoName.setText(restaurantName);

        RestaurantPagerAdapter mPagerAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(), restaurantName);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.restaurantViewpager);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout mTab = (TabLayout) findViewById(R.id.restaurantTabs);
        mTab.setupWithViewPager(mViewPager);
}}
