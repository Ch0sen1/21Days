package edu.neu.madcourse.numad21fa_team21dayproject.cookbook;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.numad21fa_team21dayproject.R;

public class ZooRviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView imageID;
    public TextView animalName;
    public TextView price;
    ZooRviewAdaptor.OnItemListener onItemListener;


    public ZooRviewHolder(View itemView, ZooRviewAdaptor.OnItemListener Listener){
        super(itemView);
        this.imageID = itemView.findViewById(R.id.animal_pic);
        this.animalName = itemView.findViewById(R.id.animal_name);
        this.price = itemView.findViewById(R.id.habit_curday);
        this.onItemListener = Listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition());
    }
}
