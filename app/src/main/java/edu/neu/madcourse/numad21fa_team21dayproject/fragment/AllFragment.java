package edu.neu.madcourse.numad21fa_team21dayproject.fragment;

import com.google.firebase.database.DatabaseReference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.neu.madcourse.numad21fa_team21dayproject.R;
import edu.neu.madcourse.numad21fa_team21dayproject.adapter.RecordAdapter;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.Habit;


public class AllFragment extends Fragment {
    private View rootView;
    private RecyclerView mRecyclerView;
    private RecordAdapter adapter;
    //    private List<Habit> list = new ArrayList<>();
    private List<Habit> list = new ArrayList<>();
    private String mUserName;
    private DatabaseReference mDatabaseReference;

    public static AllFragment newInstance(String name) {
        AllFragment allFragment = new AllFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        allFragment.setArguments(bundle);
        return allFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString("name");
        }
        mRecyclerView = rootView.findViewById(R.id.all_recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecordAdapter(getActivity(), list);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void setHabit(List<Habit> habitList) {
        if (list != null) {
            list.clear();
        }
        if (habitList != null && habitList.size() > 0) {
            list.addAll(habitList);
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
