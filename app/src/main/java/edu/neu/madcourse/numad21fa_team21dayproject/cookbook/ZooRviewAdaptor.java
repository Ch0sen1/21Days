package edu.neu.madcourse.numad21fa_team21dayproject.cookbook;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.numad21fa_team21dayproject.R;

public class ZooRviewAdaptor extends RecyclerView.Adapter<ZooRviewHolder> {

    private ArrayList<Animal> animalList;
    private OnItemListener onItemListener;


    // Constructor
    public ZooRviewAdaptor(ArrayList<Animal> animalList, OnItemListener listener){
        this.animalList = animalList;
        this.onItemListener = listener;
    }


    public void setOnItemListener(OnItemListener listener){
        this.onItemListener = listener;
    }

    @Override
    public ZooRviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_card, parent, false);
        return new ZooRviewHolder(view, onItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ZooRviewHolder holder, int position) {
        Animal currentItem = animalList.get(position);


        holder.animalName.setText(currentItem.getaName());
        holder.imageID.setImageResource(currentItem.getPicPath());
        holder.price.setText('$'+ String.valueOf(currentItem.getPrice()));
//        holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 4]));
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }
}
