package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.MyViewHolder> {
    private final RecycleViewInterface recycleViewInterface;
    Context context;

    ArrayList<Review> list;


    public reviewAdapter(Context context, ArrayList<Review> list, RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.list = list;
        this.recycleViewInterface = recycleViewInterface;
    }
    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.weekly_review_row,parent,false);
        return new MyViewHolder(v,recycleViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Review user = list.get(position);
        holder.getTitle().setText(user.getTitle());
        holder.getDescription().setText(user.getDescription());
        holder.getLikecount().setText(String.valueOf(user.getLikecount()));
        Glide.with(context).load(user.getImg()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title, description,likecount;
        private ImageView img;

        public TextView getTitle() {
            return title;
        }

        public TextView getDescription() {
            return description;
        }

        public TextView getLikecount() {
            return likecount;
        }

        public ImageView getImg() {
            return img;
        }

        public MyViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);

            title = itemView.findViewById(R.id.titleView);
            description = itemView.findViewById(R.id.descriptionView);
            img = itemView.findViewById(R.id.recyclerimg);
            likecount = itemView.findViewById(R.id.likecount);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(recycleViewInterface != null){
                        int pos = getBindingAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recycleViewInterface.onItemClick(pos);
                        }
                    }

                }

            });



        }
    }

}
