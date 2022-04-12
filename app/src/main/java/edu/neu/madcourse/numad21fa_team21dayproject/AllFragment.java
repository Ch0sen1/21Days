package edu.neu.madcourse.numad21fa_team21dayproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.Animal;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.Habit;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.HabitRviewAdaptor;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.User;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.ZooRviewAdaptor;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.tools;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AllFragment extends Fragment {
    private String curUserName;
    private final String TAG = "ALL_FRAG";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManger;
    private HabitRviewAdaptor rviewAdaptor;
    private ArrayList<Habit> resList = new ArrayList<>();
    private Integer imageRes;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";


    public AllFragment() {
        // Required empty public constructor
    }



//    public static AllFragment newInstance(String param1, String param2) {
//        AllFragment fragment = new AllFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        curUserName = this.getArguments().getString("curUserName");
//        Log.e(TAG,curUserName);
        init(savedInstanceState);
        getUserHistory();


        rLayoutManger = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.all_recycle);
        recyclerView.setHasFixedSize(true);

        rviewAdaptor = new HabitRviewAdaptor(resList, this::onItemClick);
        rviewAdaptor.setOnItemListener(this::onItemClick);
        recyclerView.setAdapter(rviewAdaptor);
        recyclerView.setLayoutManager(rLayoutManger);

        return view;
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
//        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {

        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (resList == null || resList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {

                    int id = savedInstanceState.getInt(KEY_OF_INSTANCE + i + "0");
                    String name = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    int curDay = savedInstanceState.getInt(KEY_OF_INSTANCE + i + "2");
                    String hText = savedInstanceState.getString(KEY_OF_INSTANCE + i + "3");
                    Habit item = new Habit(name, id, curDay,hText);

                    resList.add(item);
                }
            }
        }

    }

    //    private void createRecyclerView() {
//        rLayoutManger = new LinearLayoutManager(getActivity());
//
//        recyclerView = (RecyclerView) view.findViewById(R.id.ZooViewRecycler);
//        recyclerView.setHasFixedSize(true);
//
//        rviewAdaptor = new ZooRviewAdaptor(resList, this::onItemClick);
//        rviewAdaptor.setOnItemListener(this::onItemClick);
//        recyclerView.setAdapter(rviewAdaptor);
//        recyclerView.setLayoutManager(rLayoutManger);
//
//
//    }
    // bug free
    private void getUserHistory() {
        StringBuilder sb = new StringBuilder();
        tools.mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
//                    sb.append(user.getUserName()).append("\n");

                    if (user.getUserName().equals(curUserName)) {
                        ArrayList<Habit> tmp = user.getCreatedHabits();
                        for (int i=0; i <tmp.size();i++) {
                            Habit habitCur = tmp.get(i);
                            Integer picIDCur = habitCur.getPic();
                            Integer curDayCur = habitCur.getCurdays();
                            String hTextCur = habitCur.getHtext();
                            String habitNameCur = habitCur.getHname();


                            Log.e(TAG, picIDCur.toString()+curDayCur.toString()+ hTextCur+habitNameCur);
                            setRes(i,picIDCur,curDayCur, habitNameCur, hTextCur);
                        }
                        sb.append(user.toString());
                        //resList = user.getReceivedSticker();
                    }
                    //sb.append(user.getUserName());
                    Log.i("Get Data", user.getUserName());
                }
                //v.setText(sb.toString());
                //v.setText(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //return sb.toString();
    }

    private void setRes(int i, Integer ID, Integer curDay, String name, String hText) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (ID == 1){
                    imageRes = R.drawable.panda;
                } else if (ID == 2){
                    imageRes = R.drawable.fox;
                } else if (ID == 3){
                    imageRes = R.drawable.bear;
                }
                resList.add(i, new Habit(name, imageRes, curDay, hText));
                rviewAdaptor.notifyItemInserted(i);
            }
        });
    }

    // Handling Orientation Changes on Android
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        int size = resList == null ? 0 : resList.size();
        outState.putInt(NUMBER_OF_ITEMS, size );
        for (int i = 0; i < size; i++) {
            // put image information id into instance
//            outState.putString(KEY_OF_INSTANCE + i + "0", resList.get(i).getUrl());
            // put itemName information into instance
            outState.putInt(KEY_OF_INSTANCE + i + "0", resList.get(i).getPic());
            outState.putString(KEY_OF_INSTANCE + i + "1", resList.get(i).getHname());
            outState.putInt(KEY_OF_INSTANCE + i + "2", resList.get(i).getCurdays());
            outState.putString(KEY_OF_INSTANCE + i + "3", resList.get(i).getHtext());
        }
        super.onSaveInstanceState(outState);

    }



    public void onItemClick(int position) {
//        resList.get(position).onItemClick(position);
        rviewAdaptor.notifyItemChanged(position);
    }
}
