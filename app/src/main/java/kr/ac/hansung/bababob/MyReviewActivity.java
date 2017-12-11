package kr.ac.hansung.bababob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyReviewActivity extends AppCompatActivity {

    private RecyclerView rvReview;
    private TimelineAdapter adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            finish();
            return;
        }

        setContentView(R.layout.activity_my_review);

        rvReview = (RecyclerView) findViewById(R.id.my_review_recycler_view);
        adapter = new TimelineAdapter(this, "내리뷰");
        rvReview.setAdapter(adapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(500);
        rvReview.setItemAnimator(itemAnimator);
    }
}
