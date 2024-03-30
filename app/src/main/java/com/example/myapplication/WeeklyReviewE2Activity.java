package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class WeeklyReviewE2Activity extends AppCompatActivity {

    ArrayList<Review> reviewModels; //hold all models in review, and send to recyclerview
    private DatabaseReference databaseReference, likeReference;
    private boolean likeChecker = false;
    FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;
    ReviewRecyclerViewAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_review_e2);

        databaseReference = FirebaseDatabase.getInstance().getReference("Review");
        recyclerView = findViewById(R.id.myReviewRecyclerView);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewModels = new ArrayList<>();
        myAdapter = new ReviewRecyclerViewAdapter(this,reviewModels);
        recyclerView.setAdapter(myAdapter);

//        likeReference = firebaseDatabase.getReference("likes");


//        ReviewRecyclerViewAdapter adapter = new ReviewRecyclerViewAdapter(this,reviewModels);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 添加 ValueEventListener 来监听数据变化
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 清空 ArrayList，以便重新填充数据
                reviewModels.clear();

                // 遍历数据库中的每个 "Review" 节点
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // 使用 getValue 方法将数据转换为 Review 对象
                    Review review = snapshot.getValue(Review.class); //each user (snapshot.getValue(Review.class);)
                    review.setKey(snapshot.getKey());
                    reviewModels.add(review);
                }

                //sort based on rated score (like count)
                Collections.sort(reviewModels, new Comparator<Review>() {
                    @Override
                    public int compare(Review review1, Review review2) {
                        return Integer.compare(review2.getNoOfLike(),review1.getNoOfLike());
                    }
                });

                myAdapter.notifyDataSetChanged();
                // 在此处更新 RecyclerView 或执行其他操作以反映数据的更改
                // 比如，如果您有一个 RecyclerView 适配器，请在此处调用适配器的 notifyDataSetChanged 方法
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 处理数据检索时出现的错误
//                Log.e("DatabaseError", "Error fetching data", databaseError.toException());
            }
        });



    }
}