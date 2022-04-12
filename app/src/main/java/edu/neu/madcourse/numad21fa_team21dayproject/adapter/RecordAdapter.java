package edu.neu.madcourse.numad21fa_team21dayproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.neu.madcourse.numad21fa_team21dayproject.R;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.Habit;


public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    List<Habit> habits;
    Context context;

    public RecordAdapter(Context context, List<Habit> habits) {
        this.habits = habits;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_habits, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Habit habit = habits.get(position);

        holder.tvName.setText(habit.hname);
        holder.tvDesc.setText(habit.htext);
        holder.tv_number.setText(habit.curdays + "");
        switch (habit.getPic()) {
            case 1:
                holder.mPicImageView.setImageResource(R.drawable.habit1);
                break;
            case 2:
                holder.mPicImageView.setImageResource(R.drawable.habit2);

                break;
            case 3:
                holder.mPicImageView.setImageResource(R.drawable.habit3);

                break;

            case 4:
                holder.mPicImageView.setImageResource(R.drawable.habit_4);

                break;

            case 5:
                holder.mPicImageView.setImageResource(R.drawable.habit5);
                break;
            default:
                break;

        }
    }


    @Override
    public int getItemCount() {
        return habits.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDesc;
        TextView tv_number;
        private ImageView mPicImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tv_number = itemView.findViewById(R.id.tv_number);
            mPicImageView = itemView.findViewById(R.id.iv);
        }
    }
}
