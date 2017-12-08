package kr.ac.hansung.bababob;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ReviewWriteActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;

    private Button add_photo_btn, pre_btn, upload_btn;
    private ImageView selected_iv;
    private RatingBar total_rat, spicy_rat, amount_rat;
    private EditText review_text;
    int GET_PICTURE_URI;
    float totalScore, spicyScore, amountScore;
    String time, restaurantName, imgURL;
    FirebaseUser user;
    Bitmap bitmap=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);
        Intent intent = getIntent();
        restaurantName = intent.getStringExtra("RestaurantName");
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        // matching view
        pre_btn = (Button) findViewById(R.id.pre_btn);
        add_photo_btn = (Button) findViewById(R.id.add_photo_btn);
        upload_btn = (Button) findViewById(R.id.upload_btn);
        selected_iv = (ImageView) findViewById(R.id.selected_iv);
        total_rat = (RatingBar) findViewById(R.id.total_rat);
        spicy_rat = (RatingBar) findViewById(R.id.spicy_rat);
        amount_rat = (RatingBar) findViewById(R.id.amount_rat);
        review_text = (EditText) findViewById(R.id.review_text);


        // [event] rating bar
        total_rat.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.i("######## total_rat:", "" + v);
                totalScore = v;
            }
        });

        spicy_rat.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.i("######## spicy_rat:", "" + v);
                spicyScore = v;
            }
        });

        amount_rat.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.i("######## amount_rat:", "" + v);
                amountScore = v;
            }
        });

        // [event] button
        pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        add_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_PICTURE_URI);

            }
        });
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 시간 구하기
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                time = sdf.format(date);
                putDataFirebase();
                onBackPressed();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_PICTURE_URI) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    // 이미지를 만들어줌
                    if(bitmap!=null){
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    selected_iv.setImageBitmap(bitmap);}
                } catch (Exception e) {
                    Log.e("test", e.getMessage());
                }
            }
        }
    }

    private void uploadFile(final String postid) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference storageReference1 = storageReference.child("ReviewImg/"+postid+".jpg");
        UploadTask uploadTask = storageReference1.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl  = taskSnapshot.getDownloadUrl();
                myRef.child(postid).child("image").setValue(downloadUrl.getPath());
            }
        });
    }

    private void putDataFirebase() {
        String postid = UUID.randomUUID().toString().replace("-", "");
        myRef = database.getReference("Review");
        myRef.child(postid).child("totalScore").setValue(totalScore);
        myRef.child(postid).child("spicyScore").setValue(spicyScore);
        myRef.child(postid).child("amountScore").setValue(amountScore);
        myRef.child(postid).child("text").setValue(review_text.getText().toString());
        myRef.child(postid).child("time").setValue(time);
        myRef.child(postid).child("email").setValue(user.getEmail());
        myRef.child(postid).child("restaurant").setValue(restaurantName);
        if(bitmap!=null)uploadFile(postid);
        Toast.makeText(getApplicationContext(), "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
    }
}