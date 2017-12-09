package kr.ac.hansung.bababob;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kr.ac.hansung.bababob.SchoolCafeteria.SchoolCafeteriaMenu;

/**
 * Created by Jina on 2017-12-09.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder>{

    private Context mContext;
    private DatabaseReference mPostReference;

    private List<String> mReviewIds = new ArrayList<String>();
    private List<Review> mReviews = new ArrayList<Review>();

    public TimelineAdapter(Context context, DatabaseReference postReference) {
        mContext = context;
        mPostReference = postReference;

        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review review = snapshot.getValue(Review.class);
                    mReviewIds.add(snapshot.getKey());
                    mReviews.add(review);

                    notifyItemChanged(mReviews.size()-1);
                }
                Log.e("jina",Integer.toString(mReviews.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public TimelineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.timeline_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.emailTextView.setText(review.getEmail());
        holder.textTextView.setText(review.getText());
        Glide.with(mContext).load(review.getImage()).into(holder.image);
        CommentAdapter adapter = new CommentAdapter(mContext, mReviewIds.get(position));
        holder.rvComment.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView emailTextView;
        private ImageView image;
        private TextView textTextView;
        private ImageButton commentBtn;
        private ImageButton likeBtn;
        private RecyclerView rvComment;

        public ViewHolder(View itemView) {
            super(itemView);
            emailTextView = (TextView) itemView.findViewById(R.id.review_email);
            image = (ImageView) itemView.findViewById(R.id.review_image);
            textTextView = (TextView) itemView.findViewById(R.id.review_text);
            commentBtn = (ImageButton) itemView.findViewById(R.id.comment_btn);
            likeBtn = (ImageButton) itemView.findViewById(R.id.like_btn);
            rvComment = (RecyclerView) itemView.findViewById(R.id.comment_recycler_view);
            commentBtn.setOnClickListener(this);
            likeBtn.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.comment_btn:

                    break;
                case R.id.like_btn:

                    break;
            }
        }

    }
}
