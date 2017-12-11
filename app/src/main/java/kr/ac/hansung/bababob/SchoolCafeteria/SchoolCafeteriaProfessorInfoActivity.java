package kr.ac.hansung.bababob.SchoolCafeteria;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kr.ac.hansung.bababob.R;

public class SchoolCafeteriaProfessorInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton mBtnPrev;
    private ImageButton mBtnNext;
    private TextView mTextDate;
    private RecyclerView rvSchoolCafeteriaProfessor;
    private SchoolCafeteriaMenuAdapter adapter;

    private Calendar calendar = Calendar.getInstance();
    private DateFormat dateFormat = new SimpleDateFormat("MM/dd E");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        setContentView(R.layout.activity_school_cafeteria_professor_info);
        Intent intent = getIntent();
        int cafeteriaName = intent.getIntExtra("CafeteriaName", SchoolCafeteria.PROFESSOR_CAFETERIA);

        TextView name = (TextView) findViewById(R.id.cafeteria_name);
        name.setText(SchoolCafeteria.SCHOOLCAFETERIA[cafeteriaName]);

        mTextDate = (TextView) findViewById(R.id.date);
        mBtnPrev = (ImageButton) findViewById(R.id.prev_btn);
        mBtnNext = (ImageButton) findViewById(R.id.next_btn);
        mBtnPrev.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);

        mTextDate.setText(dateFormat.format(calendar.getTime()));

        rvSchoolCafeteriaProfessor = (RecyclerView) findViewById(R.id.school_cafeteria_professor_recycler_view);
        adapter = new SchoolCafeteriaMenuAdapter(this, SchoolCafeteriaMenu.getMenus(SchoolCafeteria.PROFESSOR_CAFETERIA));
        rvSchoolCafeteriaProfessor.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int i = 0;
        switch (id){
            case R.id.prev_btn: {
                i--;
                break;
            }
            case R.id.next_btn: {
                i++;
                break;
            }
        }

        calendar.add(Calendar.DATE, i);
        if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY) {
            mBtnNext.setClickable(false);
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY) {
            mBtnPrev.setClickable(false);
        }else{
            mBtnNext.setClickable(true);
            mBtnPrev.setClickable(true);
        }
        adapter.changeData(calendar.get(Calendar.DAY_OF_WEEK));
        mTextDate.setText(dateFormat.format(calendar.getTime()));
    }
}
