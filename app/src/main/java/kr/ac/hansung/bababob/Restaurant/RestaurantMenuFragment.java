package kr.ac.hansung.bababob.Restaurant;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.*;

import org.w3c.dom.Text;

import kr.ac.hansung.bababob.R;

public class RestaurantMenuFragment extends Fragment implements OnMapReadyCallback {

    private static RestaurantMenuFragment instance;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private double latitude, longitude;
    private GoogleMap googleMap;
    private Button tel_btn;
    private String address, tel;
    static private String restaurantName;
    private TextView infoAddress;

    public RestaurantMenuFragment() {
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;

    }

    public static RestaurantMenuFragment getInstance() {
        if (instance == null)
            instance = new RestaurantMenuFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_restaurnt_menu, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //MapsInitializer.initialize( getView().getContext());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurant").child(restaurantName);
        infoAddress = (TextView)rootView.findViewById(R.id.infoAddress);
        tel_btn = (Button)rootView.findViewById(R.id.tel_btn);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                restaurantName = snapshot.child("name").getValue(String.class);
                address = snapshot.child("address").getValue(String.class);
                tel = snapshot.child("tel").getValue(String.class);
                latitude = snapshot.child("latitude").getValue(double.class);
                longitude = snapshot.child("longitude").getValue(double.class);
                infoAddress.setText(address);

                LatLng pos = new LatLng(latitude, longitude);
                makeMarker(googleMap, pos);
                // move the camera
                googleMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        tel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tel));
                startActivity(intent);
            }
        });
        //return inflater.inflate(R.layout.fragment_restaurnt_menu, container, false);
        return rootView;
    }

    public void makeMarker(GoogleMap googleMap, LatLng latLng) {
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



