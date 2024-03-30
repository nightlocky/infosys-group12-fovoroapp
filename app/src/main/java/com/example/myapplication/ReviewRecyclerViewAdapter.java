package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.MyViewHolder> {

    Context context; //inflating the layout
    ArrayList<Review> reviewModels; //hold the models

    private boolean likeChecker = false;
    private DatabaseReference likeReference;


    public ReviewRecyclerViewAdapter(Context context, ArrayList<Review> reviewModels){

        this.context = context;
        this.reviewModels = reviewModels;

//        likeReference = FirebaseDatabase.getInstance().getReference().child("likes");
    }

    @NonNull
    @Override
    public ReviewRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout (giving a layout for each of the row)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.weekly_review_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assigning values to views created in recycler_view_row layout file (update data on each of the row)
        //based on position of recycler view

        //reviewModels.get(position)就是每个对应的user (乱码)
        holder.tvTitle.setText(reviewModels.get(position).getTitle());
        holder.tvDescription.setText(reviewModels.get(position).getDescription());
        holder.tvStoreName.setText(reviewModels.get(position).getRatedStoreName());
        Glide.with(context).load(reviewModels.get(position).getImg()).into(holder.tvImageView);
        holder.tvLikeCount.setText(String.valueOf(reviewModels.get(position).getNoOfLike()));



//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String currentUserId = user.getUid();
//        final String postKey = reviewModels.get(position).getKey(); //get key of post (和上面key重复了）

        holder.tvLikeButton.setOnClickListener(v -> { // == new View.OnClickListener()
            Review reviewModel = reviewModels.get(position); //对应的每个user
            String key = reviewModel.getKey();
            boolean hasLiked = reviewModel.isHasLiked();
            int currentLikes = reviewModel.getNoOfLike();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Review").child(key);



//            if (!hasLiked) {
//                // 用户未点赞，增加点赞
//                currentLikes++;
//                reviewModel.setHasLiked(true);
//                Log.d("ReviewAdapter3", "Review at position3 " + position + " hasLiked: " + reviewModel.isHasLiked());
//                holder.tvLikeButton.setImageResource(R.drawable.like);
//                //更新模型和UI
//                reviewModel.setNoOfLike(currentLikes);
//                holder.tvLikeCount.setText(String.valueOf(currentLikes));
////                databaseReference.child("noOfLike").setValue(currentLikes);
////                return;
//            } else if(hasLiked){
//                Log.d("ReviewAdapter2", "Review at position2 " + position + " hasLiked: " + reviewModel.isHasLiked());
//                // 用户已点赞，取消点赞
//                currentLikes = Math.max(0, currentLikes - 1); // 防止点赞数成为负数
//                reviewModel.setHasLiked(false);
//                holder.tvLikeButton.setImageResource(R.drawable.like_unclicked);
//                reviewModel.setNoOfLike(currentLikes);
//                holder.tvLikeCount.setText(String.valueOf(currentLikes));
////                databaseReference.child("noOfLike").setValue(currentLikes);
////                return;
//            }

            //更新数据库中的点赞数，不涉及用户点赞状态的持久化
//            databaseReference = FirebaseDatabase.getInstance().getReference("Review").child(key);
//            databaseReference.child("noOfLike").setValue(currentLikes);

            if (!hasLiked){
                // 获取当前key
                int newScore = reviewModel.getNoOfLike() + 1;
                //更新页面的点赞数
                reviewModel.setNoOfLike(newScore); //user.method(score+1)
                reviewModel.setHasLiked(true);
                holder.tvLikeCount.setText(String.valueOf(newScore));
                holder.tvLikeButton.setImageResource(R.drawable.ic_like);
                // 更新数据库中的点赞数
                databaseReference.child("noOfLike").setValue( newScore);
            }
            else {
                // 用户已点赞，执行取消点赞逻辑
                int newScore = reviewModel.getNoOfLike() - 1;
                reviewModel.setNoOfLike(newScore);
                reviewModel.setHasLiked(false); // 设置已点赞标志为 false
                // 更新 UI
                holder.tvLikeCount.setText(String.valueOf(newScore));
                holder.tvLikeButton.setImageResource(R.drawable.ic_dislike); // 白心图标
                // 更新数据库中的点赞数和点赞状态
                databaseReference.child("noOfLike").setValue(newScore);
            }
        });

        //从这到上面的    final String postKey = reviewModels.get(position).getKey(); //get key of post (和上面key重复了） 都是有用的


        //可删
//        holder.setLikesButtonStatus(postKey);
//
//        holder.tvLikeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                likeChecker = true;
//                likeReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (likeChecker == true){
//                            if (snapshot.child(postKey).hasChild(currentUserId)){
//                                likeReference.child(postKey).child(currentUserId).removeValue();
//                                likeChecker = false;
//                            }else{
//                                likeReference.child(postKey).child(currentUserId).setValue(true);
//                                likeChecker = false;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });

    }

    @Override
    public int getItemCount() {
        //no.of items want to display in total
        return reviewModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grabbing all views from recycler_view_row layout file
        //kinda like in the onCreate method

        //1个img view，3个textview
        private ImageView tvImageView;
        private TextView tvTitle,tvDescription,tvLikeCount,tvStoreName;
        private ImageButton tvLikeButton;

//        private DatabaseReference likesRef;
        private int likesCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            tvImageView = itemView.findViewById(R.id.recyclerImage);
            tvTitle = itemView.findViewById(R.id.ImageTitle);
            tvDescription = itemView.findViewById(R.id.ImageComment);
            tvLikeCount = itemView.findViewById(R.id.likeCount);
            tvStoreName = itemView.findViewById(R.id.StoreName);
            tvLikeButton = itemView.findViewById(R.id.like);
//            tvFoodName = itemView.findViewById(R.id.FoodName);
        }

        //可删
//        public void setLikesButtonStatus(final String postKey){
//            tvLikeButton = itemView.findViewById(R.id.like);
//            tvLikeCount = itemView.findViewById(R.id.likeCount);
//
//            likesRef = FirebaseDatabase.getInstance().getReference("likes");
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            String userId = user.getUid();
//            String likes = "likes";
//
//            likesRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.child(postKey).hasChild(userId)){
//                        likesCount = (int) snapshot.child(postKey).getChildrenCount();
//                        tvLikeButton.setImageResource(R.drawable.ic_like);
//                        tvLikeCount.setText(Integer.toString(likesCount)+likes);
//                    }
//                    else {
//                        likesCount = (int) snapshot.child(postKey).getChildrenCount();
//                        tvLikeButton.setImageResource(R.drawable.ic_dislike);
//                        tvLikeCount.setText(Integer.toString(likesCount)+likes);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//
//                }
//            });
//        }

    }
}
