package kr.ac.hansung.bababob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kr.ac.hansung.bababob.Restaurant.RestaurantInfoActivity;
import kr.ac.hansung.bababob.SchoolCafeteria.SchoolCafeteria;
import kr.ac.hansung.bababob.SchoolCafeteria.SchoolCafeteriaStudentInfoActivity;
import kr.ac.hansung.bababob.SchoolCafeteria.SchoolCafeteriaProfessorInfoActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static MyHandler myHandler;
    private FirebaseAuth mAuth;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            finish();
            return;
        }

        myHandler = new MyHandler();
        MyPagerAdapter mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout mTab = (TabLayout) findViewById(R.id.tabs);
        mTab.setupWithViewPager(mViewPager);

        for(int i=0; i<mViewPager.getAdapter().getCount(); i++){
            mTab.getTabAt(i).setIcon(mPagerAdapter.getIcon(i));
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView textView = (TextView) headerView.findViewById(R.id.user_email);
        textView.setText(mAuth.getCurrentUser().getEmail());
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else {
            moveTaskToBack(true);
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.my_review){

        }else if(id == R.id.setting){

        } else if(id == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent itent = new Intent(MainActivity.this, ConnectionActivity.class);
            finish();
            startActivity(itent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MyHandler extends Handler{
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             Bundle bundle = msg.getData();
             String activity = bundle.getString("change");
             if(activity.equals("RestaurantInfoActivity")){
                 Intent intent = new Intent(getApplicationContext(), RestaurantInfoActivity.class);
                 intent.putExtra("RestaurantName", bundle.getString("RestaurantName"));
                 startActivity(intent);
             }
             else if(activity.equals("SchoolCafeteriaInfoActivity")){
                 int cafeteriaName = bundle.getInt("CafeteriaName");
                 Intent intent = null;
                 switch (cafeteriaName){
                     case SchoolCafeteria.PROFESSOR_CAFETERIA :
                         intent = new Intent(getApplicationContext(), SchoolCafeteriaProfessorInfoActivity.class);
                         break;
                     case SchoolCafeteria.STUDENT_CAFETERIA:
                         intent = new Intent(getApplicationContext(), SchoolCafeteriaStudentInfoActivity.class);
                         break;
                 }
                 intent.putExtra("CafeteriaName", bundle.getInt("CafeteriaName"));
                 startActivity(intent);
             }
             else if(activity.equals("ReviewWriteActivity")){
                 Intent intent = new Intent(getApplicationContext(), ReviewWriteActivity.class);
                 startActivity(intent);
             }
         }
     }
}
