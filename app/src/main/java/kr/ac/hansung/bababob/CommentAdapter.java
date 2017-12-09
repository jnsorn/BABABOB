package kr.ac.hansung.bababob;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jina on 2017-12-09.
 */

public class CommentAdapter  extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private DatabaseReference mCommentReference;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private String mPostId;
    private List<Comment> mComments = new ArrayList<Comment>();

    public CommentAdapter(Context context, String postId){
        mContext = context;
        mPostId = postId;
        mCommentReference = database.getReference("Comments").child(mPostId);
        mCommentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comment comment = snapshot.getValue(Comment.class);
                    mComments.add(comment);
                    notifyItemChanged(mComments.size()-1);
                }
                //Log.e("jina","Comment"+Integer.toString(mComments.size())+mComments.get(0).getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View commentView = inflater.inflate(R.layout.timeline_comment_item, parent, false);
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(commentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.emailTextView.setText(comment.getEmail());
        holder.textTextView.setText(comment.getText());
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView emailTextView;
        private TextView textTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            emailTextView = (TextView) itemView.findViewById(R.id.comment_email);
            textTextView =(TextView) itemView.findViewById(R.id.comment_text);
        }
    }
}
