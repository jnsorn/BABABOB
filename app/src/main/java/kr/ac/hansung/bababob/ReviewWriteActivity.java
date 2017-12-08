package kr.ac.hansung.bababob;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ReviewWriteActivity extends AppCompatActivity {

    private Button add_photo_btn, pre_btn;
    private ImageView selected_iv;
    int GET_PICTURE_URI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        pre_btn =(Button)findViewById(R.id.pre_btn);
        add_photo_btn = (Button)findViewById(R.id.add_photo_btn);
        selected_iv = (ImageView)findViewById(R.id.selected_iv);

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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_PICTURE_URI) {
            if (resultCode == Activity.RESULT_OK) {
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

//                    배치해놓은 ImageView에 이미지를 넣어봅시다.
                    selected_iv.setImageBitmap(bitmap);
//                    Glide.with(mContext).load(data.getData()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView); // OOM 없애기위해 그레들사용



                } catch (Exception e) {
                    Log.e("test", e.getMessage());
                }
            }
        }
    }


}