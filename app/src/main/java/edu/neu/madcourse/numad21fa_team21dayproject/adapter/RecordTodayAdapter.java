package edu.neu.madcourse.numad21fa_team21dayproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.neu.madcourse.numad21fa_team21dayproject.R;
import edu.neu.madcourse.numad21fa_team21dayproject.cookbook.Habit;
import edu.neu.madcourse.numad21fa_team21dayproject.utils.TimeUtils;


public class RecordTodayAdapter extends RecyclerView.Adapter<RecordTodayAdapter.ViewHolder> {

    List<Habit> habits;
    Context context;

    public OnItemClickedListener mListener;

    public void setListener(OnItemClickedListener listener) {
        this.mListener = listener;
    }

    public RecordTodayAdapter(Context context, List<Habit> habits) {
        this.habits = habits;
        this.context = context;
    }

    public void setData(List<Habit> habitList) {
        this.habits = habitList;
        notifyDataSetChanged();
    }

    public interface OnItemClickedListener {
        void onItemClicked(Habit habit, List<Habit> habitList, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_habits_today, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Habit habit = habits.get(position);

        holder.tvName.setText(habit.hname);

        int finishedNum = habit.getFinished_num();
        long lastClickedTime = habit.getLastClickTime();
        int totalNum = habit.getTotal_num();

        if (lastClickedTime <= 0 || !TimeUtils.isToday(lastClickedTime)) {
            holder.tvDesc.setText(0 + "/" + habit.total_num);
        } else {
            if (finishedNum >= totalNum) {
                holder.tvDesc.setText(totalNum + "/" + totalNum);
            } else {
                holder.tvDesc.setText(finishedNum + "/" + totalNum);
            }
        }
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

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClicked(habit, habits, position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return habits.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDesc;
        private ImageView mPicImageView;
        private RelativeLayout mRootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            mPicImageView = itemView.findViewById(R.id.iv);
            mRootView = itemView.findViewById(R.id.rl_root_view);
        }
    }


}
