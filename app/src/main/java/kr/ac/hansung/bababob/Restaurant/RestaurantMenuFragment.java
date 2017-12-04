package kr.ac.hansung.bababob.Restaurant;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

import kr.ac.hansung.bababob.R;

public class RestaurantMenuFragment extends Fragment implements OnMapReadyCallback {

    private static RestaurantMenuFragment instance;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private double latitude, longitude;
    private GoogleMap googleMap;
    private Button tel_btn;
    private String address, tel, menu;
    private int price;
    static private String restaurantName;
    private TextView infoAddress;
    private RecyclerView recyclerView;
    List<RestaurantItem> listItem;

    private RestaurantMenuAdapter restaurantMenuAdapter;

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
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.menuList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurant").child(restaurantName);
        infoAddress = (TextView) rootView.findViewById(R.id.infoAddress);
        tel_btn = (Button) rootView.findViewById(R.id.tel_btn);

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


        listItem = new ArrayList<>();
        restaurantMenuAdapter = new RestaurantMenuAdapter();
        recyclerView.setAdapter(restaurantMenuAdapter);
        GetDataFirebase();


        return rootView;
    }

    void GetDataFirebase() {
        myRef = database.getReference("Restaurant").child(restaurantName).child("Menu");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    menu = snapshot.child("menu").getValue(String.class);
                    price = snapshot.child("price").getValue(Integer.class);
                    restaurantMenuAdapter.add(new RestaurantMenuItem(menu, price));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void makeMarker(GoogleMap googleMap, LatLng latLng) {
        MarkerOptions optFirst = new MarkerOptions();
        optFirst.position(latLng);// 위도 • 경도
        optFirst.title(restaurantName);
        optFirst.snippet(tel);
        optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        googleMap.addMarker(optFirst).showInfoWindow();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

}



