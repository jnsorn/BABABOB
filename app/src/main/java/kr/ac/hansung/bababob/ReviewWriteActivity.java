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
import android.widget.*;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.storage.*;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import kr.ac.hansung.bababob.SchoolCafeteria.SchoolCafeteriaStudentInfoActivity;

public class ReviewWriteActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;

    private Button add_photo_btn, pre_btn, upload_btn;
    private ImageView selected_iv;
    private RatingBar total_rat, spicy_rat, amount_rat;
    private EditText review_text;
    private AutoCompleteTextView menu_tv;
    private TextView textview;
    int GET_PICTURE_URI;
    int totalScore, spicyScore, amountScore;
    String time, restaurantName, imgURL;
    FirebaseUser user;
    Bitmap bitmap = null;
    ArrayList<String> schoolCafeteriaStudentallMenus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);
        Intent intent = getIntent();
        restaurantName = intent.getStringExtra("RestaurantName");

        myRef = database.getReference("Review");

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        schoolCafeteriaStudentallMenus = new ArrayList<>();

        // matching view
        pre_btn = (Button) findViewById(R.id.pre_btn);
        add_photo_btn = (Button) findViewById(R.id.add_photo_btn);
        upload_btn = (Button) findViewById(R.id.upload_btn);
        selected_iv = (ImageView) findViewById(R.id.selected_iv);
        total_rat = (RatingBar) findViewById(R.id.total_rat);
        spicy_rat = (RatingBar) findViewById(R.id.spicy_rat);
        amount_rat = (RatingBar) findViewById(R.id.amount_rat);
        review_text = (EditText) findViewById(R.id.review_text);
        menu_tv = (AutoCompleteTextView) findViewById(R.id.menu_tv);
        textview = (TextView) findViewById(R.id.textview);
        menu_tv.setVisibility(View.GONE);
        textview.setVisibility(View.GONE);

        if (restaurantName.equals("학생식당")) {
            textview.setVisibility(View.VISIBLE);
            menu_tv.setVisibility(View.VISIBLE);

            for (int i = 0; i < SchoolCafeteriaStudentInfoActivity.cafeteriaStudentMenusNoodles.size(); i++) {
                schoolCafeteriaStudentallMenus.add(SchoolCafeteriaStudentInfoActivity.cafeteriaStudentMenusNoodles.get(i).getMenu());
            }
            for (int i = 0; i < SchoolCafeteriaStudentInfoActivity.cafeteriaStudentMenusBab.size(); i++) {
                schoolCafeteriaStudentallMenus.add(SchoolCafeteriaStudentInfoActivity.cafeteriaStudentMenusBab.get(i).getMenu());
            }
            for (int i = 0; i < SchoolCafeteriaStudentInfoActivity.cafeteriaStudentMenusFry.size(); i++) {
                schoolCafeteriaStudentallMenus.add(SchoolCafeteriaStudentInfoActivity.cafeteriaStudentMenusFry.get(i).getMenu());
            }
        }
        menu_tv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, schoolCafeteriaStudentallMenus));

        // [event] rating bar
        total_rat.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.i("######## total_rat:", "" + v);
                totalScore = (int) v;
            }
        });

        spicy_rat.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.i("######## spicy_rat:", "" + v);
                spicyScore = (int) v;
            }
        });

        amount_rat.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.i("######## amount_rat:", "" + v);
                amountScore = (int) v;
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
                if (checkData()) {
                    String postid = UUID.randomUUID().toString().replace("-", "");
                    uploadFile(postid);
                }
                Toast.makeText(getApplicationContext(), "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
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
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    Log.i("###bitmap", bitmap.toString());
                    selected_iv.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e("test", e.getMessage());
                }
            }
        }
    }

    Uri downloadUrl;

    private void uploadFile(final String postid) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);}
        byte[] data = baos.toByteArray();
        final StorageReference storageReference1 = storageReference.child("ReviewImg/" + postid + ".jpg");
        UploadTask uploadTask = storageReference1.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUrl = taskSnapshot.getDownloadUrl();
                putDataFirebase(postid, downloadUrl);
            }
        });

    }

    public void putDataFirebase(String postid, Uri downloadUrl) {
        myRef.child(postid).child("totalScore").setValue(totalScore);
        myRef.child(postid).child("spicyScore").setValue(spicyScore);
        myRef.child(postid).child("amountScore").setValue(amountScore);
        myRef.child(postid).child("text").setValue(review_text.getText().toString());
        myRef.child(postid).child("time").setValue(time);
        myRef.child(postid).child("email").setValue(user.getEmail());
        myRef.child(postid).child("restaurant").setValue(restaurantName);
        myRef.child(postid).child("image").setValue("" + downloadUrl);

        if (restaurantName.equals("학생식당")) {
            if (menu_tv.getText().toString().equals("") || menu_tv.getText().toString().equals(null)) {
                Toast.makeText(getApplicationContext(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show();

            } else myRef.child(postid).child("menu").setValue(menu_tv.getText().toString());
        } else myRef.child(postid).child("menu").setValue("null");

    }

    private boolean checkData() {

        if (restaurantName.equals("학생식당")) {
            if (menu_tv.getText().toString().equals("") || menu_tv.getText().toString().equals(null)) {
                Toast.makeText(getApplicationContext(), "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (review_text.getText().toString().equals("") || review_text.getText().toString().equals(null)) {
            Toast.makeText(getApplicationContext(), "리뷰를 작성해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}