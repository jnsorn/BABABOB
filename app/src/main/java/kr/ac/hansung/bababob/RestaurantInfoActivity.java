package kr.ac.hansung.bababob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RestaurantInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("Restaurant");

    }


}
