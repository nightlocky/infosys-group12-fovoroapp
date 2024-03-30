package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder { //自动constructor matching super
    ImageView imageView;
    TextView textView;
    View v; //这个view就是recycler view里每个食物图片的view。Click之后，就会open对应的activity
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_single_view);
        textView = itemView.findViewById(R.id.textView_single_view);
        v = itemView;
    }

}
