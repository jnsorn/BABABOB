package kr.ac.hansung.bababob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantInfoActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    String url;
    Boolean  delivery;
    Boolean takeout;

    TextView time_tv;
    TextView infoName;
    TextView infoAddress;
    ImageView takeout_img;
    ImageView delivery_img;
    TextView takeout_tv;
    TextView delivery_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("RestaurantName");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurant").child(restaurantName);

        infoName = (TextView) findViewById(R.id.infoName);
        infoAddress = (TextView) findViewById(R.id.infoAddress);
        takeout_img = (ImageView)findViewById(R.id.takeout_img);
        delivery_img = (ImageView)findViewById(R.id.delivery_img);
        time_tv = (TextView)findViewById(R.id.time_tv);
        delivery_tv = (TextView)findViewById(R.id.delivery_tv);
        takeout_tv = (TextView)findViewById(R.id.takeout_tv);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                    url = snapshot.child("image").getValue(String.class);
                    takeout = snapshot.child("takeout").getValue(Boolean.class);
                    delivery = snapshot.child("delivery").getValue(Boolean.class);
                    infoAddress.setText(snapshot.child("address").getValue(String.class));
                    time_tv.setText(snapshot.child("time").getValue(String.class));

                    if(takeout) {
                        takeout_img.setImageResource(R.drawable.takeout_ok);
                        takeout_tv.setText("포장 가능");
                    }
                    if(delivery) {
                        delivery_img.setImageResource(R.drawable.delivery_ok);
                        delivery_tv.setText("배달 가능");
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        infoName.setText(restaurantName);

    }


}
