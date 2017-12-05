package kr.ac.hansung.bababob.Restaurant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kr.ac.hansung.bababob.R;

import static java.security.AccessController.getContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private static RestaurantFragment instance;
    private RestaurantAdapter restaurantAdapter;
    private RecyclerView recyclerView;
    private Spinner spinner;
    List<RestaurantItem> listItem;
    FirebaseDatabase database;
    DatabaseReference myRef;


    public static RestaurantFragment getInstance() {
        if (instance == null)
            instance = new RestaurantFragment();
        return instance;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_restaurant, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.restaurantList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listItem = new ArrayList<>();
        restaurantAdapter = new RestaurantAdapter();
        recyclerView.setAdapter(restaurantAdapter);
        database = FirebaseDatabase.getInstance();
        GetDataFirebase();


    }

    void GetDataFirebase() {
        myRef = database.getReference("Restaurant");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String url = snapshot.child("image").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    restaurantAdapter.add(new RestaurantItem(name, url));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //정렬 새로하기
        switch (i) {
            case 1: {
                //이름 순
            }
            break;
            case 2: {
                //거리 순
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
