package edu.neu.madcourse.numad21fa_team21dayproject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class CheckHabit extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mUser;
    private String curUser;
    private int selectedID;
    private TextView useView;
    private TextView idView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curUser = (String) getIntent().getSerializableExtra("curUserName");
        selectedID = (Integer) getIntent().getSerializableExtra("selectedHabit");
        useView = findViewById(R.id.userView);
        idView = findViewById(R.id.selected_ID);
        useView.setText(curUser);
        idView.setText(selectedID);

    }
}
