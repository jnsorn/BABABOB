package kr.ac.hansung.bababob.Restaurant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kr.ac.hansung.bababob.R;

public class RestaurantInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String url, tel, address, restaurantName;
    private Boolean delivery, takeout;
    private double latitude, longitude;
    private GoogleMap googleMap;
    private TextView time_tv, infoName, takeout_tv, delivery_tv, tel_tv, infoAddress;
    private ImageView takeout_img, delivery_img;
    private Button tel_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        MapsInitializer.initialize(getApplicationContext());
        Intent intent = getIntent();
        restaurantName = intent.getStringExtra("RestaurantName");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurant").child(restaurantName);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        infoName = (TextView) findViewById(R.id.infoName);
        infoAddress = (TextView) findViewById(R.id.infoAddress);
        takeout_img = (ImageView) findViewById(R.id.takeout_img);
        delivery_img = (ImageView) findViewById(R.id.delivery_img);
        time_tv = (TextView) findViewById(R.id.time_tv);
        delivery_tv = (TextView) findViewById(R.id.delivery_tv);
        takeout_tv = (TextView) findViewById(R.id.takeout_tv);
        tel_btn = (Button) findViewById(R.id.tel_btn);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                url = snapshot.child("image").getValue(String.class);
                takeout = snapshot.child("takeout").getValue(Boolean.class);
                delivery = snapshot.child("delivery").getValue(Boolean.class);
                address = snapshot.child("address").getValue(String.class);
                time_tv.setText(snapshot.child("time").getValue(String.class));
                tel = snapshot.child("tel").getValue(String.class);
                latitude = snapshot.child("latitude").getValue(double.class);
                longitude = snapshot.child("longitude").getValue(double.class);

//                tel_tv.setText(tel);
                infoAddress.setText(address);
                if (takeout) {
                    takeout_img.setImageResource(R.drawable.takeout_ok);
                    takeout_tv.setText("포장 가능");
                }
                if (delivery) {
                    delivery_img.setImageResource(R.drawable.delivery_ok);
                    delivery_tv.setText("배달 가능");
                }

                Log.i("dddddddd", "lat:" + latitude + ", " + "lon:" + longitude);
                LatLng pos = new LatLng(latitude, longitude);
                makeMarker(googleMap, pos);
                // move the camera
                googleMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
            }
//
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        infoName.setText(restaurantName);

        tel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tel));
                startActivity(intent);
            }
        });
    }

    public void makeMarker(GoogleMap googleMap, LatLng latLng){
        MarkerOptions optFirst = new MarkerOptions();
        optFirst.position(latLng);// 위도 • 경도
        optFirst.title(restaurantName);// 제목 미리보기
        optFirst.snippet(tel);
        optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        googleMap.addMarker(optFirst).showInfoWindow();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }


}
