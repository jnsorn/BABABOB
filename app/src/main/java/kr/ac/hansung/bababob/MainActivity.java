package kr.ac.hansung.bababob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import kr.ac.hansung.bababob.Restaurant.RestaurantInfoActivity;

public class MainActivity extends AppCompatActivity {

    public static MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        setContentView(R.layout.activity_main);

        myHandler = new MyHandler();
        MyPagerAdapter mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout mTab = (TabLayout) findViewById(R.id.tabs);
        mTab.setupWithViewPager(mViewPager);

        for(int i=0; i<mViewPager.getAdapter().getCount(); i++){
            mTab.getTabAt(i).setIcon(mPagerAdapter.getIcon(i));
        }

    }


     public class MyHandler extends Handler{
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             String message = msg.getData().getString("message");
             if(message.equals("showInfoActivity")){
                 Intent intent = new Intent(getApplicationContext(),RestaurantInfoActivity.class);
                 intent.putExtra("RestaurantName",msg.getData().getString("RestaurantName"));
                 startActivity(intent);
             }
         }
     }
}
