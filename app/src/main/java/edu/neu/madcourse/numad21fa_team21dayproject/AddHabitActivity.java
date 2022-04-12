package edu.neu.madcourse.numad21fa_team21dayproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.blankj.utilcode.util.ToastUtils;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.RemoteMessage;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.Habit;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.User;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.tools;

public class AddHabitActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mUser;
    private String curUser;

    //    private String habitName;
    private int pic_ID = 1;//DEFAULT
    //    private int total_num;
//    private String htext;
    private String createDate;
    private EditText habitNameView;
    private EditText h_text_view;
    private EditText set_total_num_View;
    private String happenAt;
    private ImageView habitImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit);
        mDatabase = tools.mDatabase;
        mUser = mDatabase.child("users");
        habitImg = findViewById(R.id.habit_img);

        // login user
        curUser = (String) getIntent().getSerializableExtra("userName");
        Log.e("AddHabitActivity", curUser);
        habitNameView = findViewById(R.id.name_your_habit);
//        habitName = habitNameView.getText().toString().trim();
//        Log.e("habit name", habitName);

        h_text_view = findViewById(R.id.write);
//        htext = h_text_view.getText().toString();

        set_total_num_View = findViewById(R.id.set_total_num);
//        String tmp_num = set_total_num_View.getText().toString();
//        total_num = Integer.parseInt(tmp_num);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.habit1:
                pic_ID = 1;
                habitImg.setImageResource(R.drawable.habit1);
                break;
            case R.id.habit2:
                pic_ID = 2;
                habitImg.setImageResource(R.drawable.habit2);
                break;
            case R.id.habit3:
                pic_ID = 3;
                habitImg.setImageResource(R.drawable.habit3);
                break;
            case R.id.habit4:
                pic_ID = 4;
                habitImg.setImageResource(R.drawable.habit_4);
                break;
            case R.id.habit5:
                pic_ID = 5;
                habitImg.setImageResource(R.drawable.habit5);
                break;
            case R.id.save_button:
                doUpdate();
                finish();
                break;
            case R.id.back_button:
                finish();
                break;
            case R.id.anytime:
                happenAt = "Any Time";
                break;
            case R.id.morning:
                happenAt = "Morning";
                break;
            case R.id.afternoon:
                happenAt = "Afternoon";
                break;
            case R.id.evening:
                happenAt = "Evening";
                break;
            default:
                break;
        }
    }
    private void doUpdate() {
        String habit_name = habitNameView.getText().toString();
        String h_text = h_text_view.getText().toString();
        String tmp_num = set_total_num_View.getText().toString();
        int total_num_cur = Integer.parseInt(tmp_num);
//        if (habit_name == null) {
//            Toast.makeText(getApplicationContext(),"Please name your habit",Toast.LENGTH_SHORT).show();
//        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        createDate = LocalDate.now().format(formatter);

        if (pic_ID > 0 && total_num_cur >0) {
//            Toast.makeText(getApplicationContext(),"you have chosen " + pic_ID, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), habit_name, Toast.LENGTH_SHORT).show();

            mUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Boolean validCreate = false;
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);

                        if (user.getUserName().equals(curUser)){
                            ArrayList<Habit> tmp = user.getCreatedHabits();
                            tmp.add(new Habit(habit_name, pic_ID, total_num_cur, 0, happenAt, "everyday", h_text, 0, 0, 0, createDate, 1));
                            user.setCreatedHabits(tmp);
                            DatabaseReference ref = postSnapshot.getRef().child("createdHabits");
                            ref.setValue(tmp);
                            Toast.makeText(getApplicationContext(),"New Habit created! Bingo!",Toast.LENGTH_SHORT).show();

                            validCreate = true;
                        }
                        Log.i("new_habit", habit_name);
                    }

//                    if (validCreate) {
//
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Debug", "onCancelled: ");
                }
            });
        }


    }

}
