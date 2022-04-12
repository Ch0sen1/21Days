package edu.neu.madcourse.numad21fa_team21dayproject.fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import edu.neu.madcourse.numad21fa_team21dayproject.R;
import edu.neu.madcourse.numad21fa_team21dayproject.adapter.RecordTodayAdapter;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.Animal;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.Habit;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.User;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.tools;
import edu.neu.madcourse.numad21fa_team21dayproject.utils.TimeUtils;

public class TodayFragment extends Fragment {

    public static TodayFragment newInstance() {
        return new TodayFragment();
    }

    private View rootView;
    private RecyclerView mRecyclerView;
    private RecordTodayAdapter adapter;
    //    private List<Habit> list = new ArrayList<>();
    private List<Habit> list = new ArrayList<>();
    private String mUserName;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mUser;
    int animalSentCnt = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_today, container, false);
        mUser = tools.mDatabase.child("users");
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecordTodayAdapter(getActivity(), list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        adapter.setListener(new RecordTodayAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(Habit habit2, List<Habit> habitList, int position) {
                if (habit2 == null) {
                    return;
                }
//                int animalSentCnt = 0;
                for (int i = 0; i < habitList.size(); i++) {
                    Habit habitPosition = habitList.get(position);
                    if (i == position) {
                        int finishedNum = habitPosition.getFinished_num();
                        int totalNum = habitPosition.getTotal_num();

                        long lastClickedTime = habitPosition.getLastClickTime();
                        if (lastClickedTime <= 0 || !TimeUtils.isToday(lastClickedTime)) {
                            habitPosition.setFinished_num(1);
                            habitPosition.setLastClickTime(System.currentTimeMillis());
                        } else {
                            if (finishedNum >= totalNum) {
                                Toast.makeText(getActivity(), "You have complete this habit today!", Toast.LENGTH_SHORT).show();
                            } else {
                                finishedNum = (finishedNum + 1);
                                if (finishedNum >= totalNum) {
                                    habitPosition.setCurdays(habitPosition.getCurdays() + 1);
                                    if (animalSentCnt < 5) {
                                        Animal animal = getRandomAnimal();
                                        doUpdate(habitPosition.getPic(), animal);
                                        animalSentCnt++;
                                    }
                                    if(habitPosition.getCurdays() == 21){
                                        Animal panda = new Animal("Panda",1, 100000);
                                        doUpdate(habitPosition.getPic(), panda);
                                        Snackbar S1 = Snackbar.make(view,"Congratulation!, you just finished one of your habit! " +
                                                "  And you received a animal at your zoo!!",Snackbar.LENGTH_SHORT);

                                        S1.show();
                                    }
                                }
                                habitPosition.setFinished_num(finishedNum);
                                habitPosition.setLastClickTime(System.currentTimeMillis());
                            }
                        }
                    }
                }
                mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                mDatabaseReference.child("users").child(mUserName).child("createdHabits").setValue(habitList);
                if (adapter != null) {
                    adapter.setData(habitList);
                }
            }
        });

    }

    public void setHabit(List<Habit> habitList, String userName) {
        this.mUserName = userName;
        if (list != null) {
            list.clear();
        }
        if (habitList != null && habitList.size() > 0) {
            list.addAll(habitList);
        }
        if (adapter != null) {
            adapter.setData(list);
        }
    }



    private Animal getRandomAnimal(){
        Animal newAnimal = null;
        int random = (int)(Math.random() * 3 + 2);
        if(random == 2){
            newAnimal = newAnimal = new Animal("Fox",2, 500);
        } else if(random == 3){
            newAnimal = newAnimal = new Animal("Bear",3, 1000);
        } else if (random == 4) {
            newAnimal = newAnimal = new Animal("Dog",4, 50);
        }
        Log.e("TODAY", newAnimal.getaName());
        return newAnimal;
    };


    private void doUpdate(int picID, Animal newAnimal) {
        mUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if(user.getUserName().equals(mUserName)) {
                        // give animal
                        ArrayList<Animal> tmp = user.getReceivedAnimals();
                        //Animal newAnimal = getRandomAnimal();
                        tmp.add(newAnimal);
                        user.setReceivedAnimals(tmp);
                        DatabaseReference ref = postSnapshot.getRef().child("receivedAnimals");
                        ref.setValue(tmp);

                        // update level
                        Integer newLevel = user.getUserLevel() + 1;
                        user.setUserLevel(newLevel);
                        DatabaseReference ref2 = postSnapshot.getRef().child("userLevel");
                        ref2.setValue(newLevel);

                        // update user's ability
                        if (picID == 1 || picID == 3){
                            Integer newIntel = user.getUserIntelligent() + 1;
                            user.setUserIntelligent(newIntel);
                            DatabaseReference ref3 = postSnapshot.getRef().child("userIntelligent");
                            ref3.setValue(newIntel);
                        } else if (picID == 4 || picID == 2) {
                            Integer newStr = user.getUserStr() + 1;
                            user.setUserStr(newStr);
                            DatabaseReference ref4 = postSnapshot.getRef().child("userStr");
                            ref4.setValue(newStr);
                        } else if(picID == 5){
                            Integer newAgi = user.getUserAgility() + 1;
                            user.setUserAgility(newAgi);
                            DatabaseReference ref5 = postSnapshot.getRef().child("userAgility");
                            ref5.setValue(newAgi);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug", "onCancelled: ");
            }
        });
    }
}