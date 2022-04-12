package edu.neu.madcourse.numad21fa_team21dayproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;


import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.User;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.tools;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.Habit;
import edu.neu.madcourse.numad21fa_team21dayproject.fragment.AllFragment;
import edu.neu.madcourse.numad21fa_team21dayproject.fragment.ProfileFragment;
import edu.neu.madcourse.numad21fa_team21dayproject.fragment.TodayFragment;
import edu.neu.madcourse.numad21fa_team21dayproject.fragment.ZooFragment;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar topToolBar;

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private FrameLayout frameLayout;
    private DatabaseReference mUser;
    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseReference;

    private String curUserName;
    private String curUserPath;
    private User curUser;
//    private Button add;

    private AllFragment mAllFragment;
    private TodayFragment mTodayFragment;

    private ArrayList<Habit> mHabitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init Firebase Reference
        mDatabase = tools.mDatabase;
        mUser = mDatabase.child("users");

        curUserName = (String) getIntent().getSerializableExtra("userName");
        Log.e("Main", curUserName);

        // get the login user class
        getDbUser();

        // set default fragment
        setDefaultFragment();

        bottomNavigationView =  findViewById(R.id.bottom_navigation);
        topToolBar = findViewById(R.id.topAppBar);
        topToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.icon_add:
                        //TODO: add habit intent
//                        startCreateNewHabit(item, curUserName);
//                        Log.e("start create", "error");
                        Toast.makeText(getApplicationContext(),"add", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AddHabitActivity.class);
                        intent.putExtra("userName", curUserName);
                        startActivity(intent);

                        return true;
                    default: return true;
                }
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentManager = getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.page_today:
                        topToolBar.setTitle("Today");
//                        Fragment fragToday = new TodayFragment();
//                        transaction.replace(R.id.content, fragToday);
                        if (mTodayFragment == null) {
                            mTodayFragment = new TodayFragment();
                        }
                        if (mTodayFragment != null) {
                            mTodayFragment.setHabit(mHabitList, curUserName);
                        }
                        transaction.replace(R.id.content, mTodayFragment);
                        transaction.commit();
                        return true;
                    case R.id.page_all:
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("curUserName", curUserName);
                        topToolBar.setTitle("All");
                        ///Fragment fragAll = new AllFragment();
                        ///
                        mAllFragment = new AllFragment();
                        if (mAllFragment != null) {
                            mAllFragment.setHabit(mHabitList);
                        }
                        mAllFragment.setArguments(bundle2);
                        transaction.replace(R.id.content, mAllFragment);
                        transaction.commit();
                        return true;
                    ///
//                        fragAll.setArguments(bundle2);
//                        transaction.replace(R.id.content, fragAll);
//                        transaction.commit();
//                        return true;
                    case R.id.page_zoo:
                        Bundle bundle = new Bundle();
                        bundle.putString("curUserName", curUserName);
                        topToolBar.setTitle("Zoo");
                        Fragment fragZoo = new ZooFragment();
                        fragZoo.setArguments(bundle);
                        transaction.replace(R.id.content, fragZoo);
                        transaction.commit();
                        return true;
                    case R.id.page_home:
                        topToolBar.setTitle("Home");
                        ProfileFragment fragProfile = new ProfileFragment();
                        // pass a user class to the fragment
                        fragProfile.setUser(curUser);
                        transaction.replace(R.id.content, fragProfile);
                        transaction.commit();
                        return true;
                    default:
                        return true;
                }
            }
        });
        ///
        getFirebaseDB();
        ///
    }
//    public void onClick(View view) {
//        add.setOnClickListener(v -> startCreateNewHabit(v, curUserName));
////        switch (view.getId()) {
////            case R.id.create_new_button:
////                startCreateNewHabit(view, curUserName);
////                break;
////
////            default:
////                break;
////        }
//    }


    private void getDbUser() {
        mUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if(user.getUserName().equals(curUserName)) {
                        curUser = user;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug", "onCancelled: ");
            }
        });
    }

    private void setDefaultFragment() {
//        fragmentManager = getSupportFragmentManager();
//        transaction = fragmentManager.beginTransaction();
//        Fragment frag1 = new TodayFragment();
//        transaction.replace(R.id.content, frag1,null);
//        transaction.commit();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        mTodayFragment = new TodayFragment();
        if (mTodayFragment != null) {
            mTodayFragment.setHabit(mHabitList, curUserName);
        }
        transaction.replace(R.id.content, mTodayFragment, null);
        transaction.commit();
    }

    private void getFirebaseDB() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(curUserName);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userBean = dataSnapshot.getValue(User.class);
                if (userBean != null) {
                    mHabitList = userBean.getCreatedHabits();
                }

                if (mAllFragment != null) {
                    mAllFragment.setHabit(mHabitList);
                }

                if (mTodayFragment != null) {
                    mTodayFragment.setHabit(mHabitList, curUserName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



//    public void startCreateNewHabit(View view, String userGivenName){
//        Intent intent = new Intent(this, AddHabitActivity.class);
//        intent.putExtra("userName", userGivenName);
//        startActivity(intent);
//    }
}