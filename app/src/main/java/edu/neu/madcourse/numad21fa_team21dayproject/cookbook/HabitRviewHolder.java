package edu.neu.madcourse.numad21fa_team21dayproject.cookbook;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.numad21fa_team21dayproject.R;


public class HabitRviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView imageID;
    public TextView habitName;
    public TextView habitCurDay;
    HabitRviewAdaptor.OnItemListener onItemListener;

    public HabitRviewHolder(View itemView, HabitRviewAdaptor.OnItemListener Listener){
        super(itemView);
        this.imageID = itemView.findViewById(R.id.habit_pic);
        this.habitName = itemView.findViewById(R.id.habit_name);
        this.habitCurDay = itemView.findViewById(R.id.habit_curday);
        this.onItemListener = Listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition());
    }
}

