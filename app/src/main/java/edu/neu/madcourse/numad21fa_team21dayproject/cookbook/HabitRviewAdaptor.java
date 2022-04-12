package edu.neu.madcourse.numad21fa_team21dayproject.cookbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.numad21fa_team21dayproject.R;

public class HabitRviewAdaptor extends RecyclerView.Adapter<HabitRviewHolder>{

    private ArrayList<Habit> habitList;
    private HabitRviewAdaptor.OnItemListener onItemListener;

    // Constructor
    public HabitRviewAdaptor(ArrayList<Habit> habitList, HabitRviewAdaptor.OnItemListener listener){
        this.habitList = habitList;
        this.onItemListener = listener;
    }

    public void setOnItemListener(HabitRviewAdaptor.OnItemListener listener){
        this.onItemListener = listener;
    }

    @Override
    public HabitRviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_card, parent, false);
        return new HabitRviewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitRviewHolder holder, int position) {
        Habit currentItem = habitList.get(position);

        holder.habitName.setText(currentItem.getHname());
        holder.imageID.setImageResource(currentItem.getPic());
        holder.habitCurDay.setText(String.valueOf(currentItem.getCurdays()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }
}
