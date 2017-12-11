package kr.ac.hansung.bababob;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import kr.ac.hansung.bababob.SchoolCafeteria.SchoolCafeteriaMenu;

/**
 * Created by Jina on 2017-12-09.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private Context mContext;
    private DatabaseReference mPostReference;
    private DatabaseReference mLikeReference;
    private FirebaseUser user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private List<Review> mReviews = new ArrayList<Review>();

    public TimelineAdapter(Context context, final String restaurant) {
        mContext = context;
        mPostReference = database.getReference("Review");
        user = FirebaseAuth.getInstance().getCurrentUser();

        ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mReviews.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Review review = snapshot.getValue(Review.class);
                        if (review.getRestaurant()!=null && review.getRestaurant().equals(restaurant)) {
                            review.setId(snapshot.getKey());
                            mReviews.add(review);
                        } else if (restaurant.equals("")) {
                            review.setId(snapshot.getKey());
                            mReviews.add(review);
                        } else if (review.getEmail() != null && restaurant.equals("내리뷰") && review.getEmail().equals(user.getEmail())) {
                            review.setId(snapshot.getKey());
                            mReviews.add(review);
                        }

                        Collections.sort(mReviews, new Comparator<Review>() {
                            @Override
                            public int compare(Review o1, Review o2) {
                                if(o2.getTime() != null && o1.getTime() != null)
                                    return o2.getTime().compareTo(o1.getTime());
                                else
                                    return 0;
                            }
                        });
                        notifyItemChanged(mReviews.size() - 1);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        mPostReference.addValueEventListener(valueEventListener);

    }

    @Override
    public TimelineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.timeline_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.emailTextView.setText(review.getEmail());
        holder.timeTextView.setText(review.getTime());
        holder.restaurantTextView.setText(review.getRestaurant());
        holder.menuTextView.setText(review.getMenu());
        holder.amountScoreTextView.setText(Float.toString(review.getAmountScore()));
        holder.spicyScoreTextView.setText(Float.toString(review.getSpicyScore()));
        holder.totalScoreTextView.setText(Float.toString(review.getTotalScore()));
        holder.textTextView.setText(review.getText());
        if(review.getImage()!=null)
            Glide.with(mContext).load(review.getImage()).into(holder.image);
        CommentAdapter adapter = new CommentAdapter(mContext, mReviews.get(position).getId());
        holder.rvComment.setAdapter(adapter);

        mLikeReference = database.getReference("Likes").child(user.getUid()).child(mReviews.get(position).getId());
        mLikeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    holder.likeBtn.setImageResource(R.drawable.heartfilled);
                else
                    holder.likeBtn.setImageResource(R.drawable.heart);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView emailTextView;
        private TextView timeTextView;
        private TextView restaurantTextView;
        private TextView menuTextView;
        private TextView amountScoreTextView;
        private TextView spicyScoreTextView;
        private TextView totalScoreTextView;
        private ImageView image;
        private TextView textTextView;
        private ImageButton commentBtn;
        private ImageButton likeBtn;
        private RecyclerView rvComment;
        private LinearLayout commentLayout;
        private EditText commentEditText;
        private ImageButton commentPostBtn;
        private TextWatcher commentTextWatcher;
        private boolean isCommentEditTextOpen=false;

        private DatabaseReference commentReference;

        private DatabaseReference likeReference;
        private Boolean isClicked = false;

        public ViewHolder(View itemView) {
            super(itemView);
            emailTextView = (TextView) itemView.findViewById(R.id.review_email);
            timeTextView = (TextView) itemView.findViewById(R.id.review_time);
            restaurantTextView = (TextView) itemView.findViewById(R.id.review_restaurant);
            menuTextView = (TextView) itemView.findViewById(R.id.review_menu);
            amountScoreTextView = (TextView) itemView.findViewById(R.id.review_amount_score);
            spicyScoreTextView = (TextView) itemView.findViewById(R.id.review_spicy_score);
            totalScoreTextView = (TextView) itemView.findViewById(R.id.review_total_score);

            image = (ImageView) itemView.findViewById(R.id.review_image);
            textTextView = (TextView) itemView.findViewById(R.id.review_text);
            commentBtn = (ImageButton) itemView.findViewById(R.id.comment_btn);
            likeBtn = (ImageButton) itemView.findViewById(R.id.like_btn);
            rvComment = (RecyclerView) itemView.findViewById(R.id.comment_recycler_view);
            commentBtn.setOnClickListener(this);
            likeBtn.setOnClickListener(this);

            commentLayout = (LinearLayout) itemView.findViewById(R.id.comment_layout);
            commentEditText = (EditText) itemView.findViewById(R.id.comment_edit_text);
            commentPostBtn = (ImageButton) itemView.findViewById(R.id.comment_post_btn);
            commentPostBtn.setOnClickListener(this);
            commentTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!commentEditText.getText().toString().trim().isEmpty()) {
                        commentPostBtn.setImageResource(R.drawable.send_enable);
                    }else
                        commentPostBtn.setImageResource(R.drawable.send_disable);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            commentEditText.addTextChangedListener(commentTextWatcher);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.comment_btn:
                    if (!isCommentEditTextOpen) {
                        commentLayout.setVisibility(View.VISIBLE);
                        isCommentEditTextOpen = true;
                    } else if (isCommentEditTextOpen) {
                        commentEditText.setText("");
                        commentLayout.setVisibility(View.GONE);
                        isCommentEditTextOpen = false;
                    }
                    break;
                case R.id.like_btn:
                    postLike();
                    break;
                case R.id.comment_post_btn:
                    postComment();
                    commentEditText.setText("");
                    break;
            }
        }

        public void postLike(){
            likeReference = database.getReference("Likes").child(user.getUid()).child(mReviews.get(getAdapterPosition()).getId());
            if (!isClicked) {
                //만약 좋아요를 누르지 않은 상태라면
                //데이터베이스에 추가 후 이미지를 변경한다.
                likeReference.setValue(mReviews.get(getAdapterPosition()).getId());
                isClicked = true;
            } else {
                //만약 좋아요를 누른 상태라면
                //데이터베이스 삭제 후 이미지를 변경한다.
                likeReference.removeValue();
                isClicked = false;
            }
        }


        private void postComment() {
            commentReference = database.getReference().child("Comments").child(mReviews.get(getAdapterPosition()).getId()).child(UUID.randomUUID().toString().replace("-",""));
            Comment comment = new Comment(user.getEmail(), commentEditText.getText().toString(), getTime());
            commentReference.setValue(comment);
        }

        public String getTime(){
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.d d HH:mm:ss");
            return sdf.format(date);
        }
    }
}
