package kr.ac.hansung.bababob.SchoolCafeteria;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import kr.ac.hansung.bababob.R;

public class SchoolCafeteriaInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        setContentView(R.layout.activity_school_cafeteria_info);
        Intent intent = getIntent();
        int cafeteriaName = intent.getIntExtra("CafeteriaName",SchoolCafeteria.PROFESSOR_CAFETERIA);

        TextView name = (TextView) findViewById(R.id.cafeteria_name);
        name.setText(SchoolCafeteria.SCHOOLCAFETERIA[cafeteriaName]);
    }
}
