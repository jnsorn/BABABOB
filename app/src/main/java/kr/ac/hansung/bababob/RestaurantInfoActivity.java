package kr.ac.hansung.bababob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    String address;
    TextView infoName;
    TextView infoAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("RestaurantName");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurant");

        infoName = (TextView) findViewById(R.id.infoName);
        infoAddress = (TextView) findViewById(R.id.infoAddress);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    url = snapshot.child("image").getValue(String.class);
                    address = snapshot.child("address").getValue(String.class);
                    infoAddress.setText(address);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        infoName.setText(restaurantName);

    }


}
