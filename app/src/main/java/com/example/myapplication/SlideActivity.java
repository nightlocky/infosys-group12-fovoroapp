package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//侧滑页面
public class SlideActivity extends AppCompatActivity {

    //声明控件
    private FirebaseAuth firebaseAuth;
    private ImageView mIvHead; //头像 (改对应的)
    private TextView mTvUser;
    private SlideMenu slideMenu;
    private Button mBtnWeeklyReview, mBtnStoreReview, mBtnFoodReview,
            mBtnTopdownList, to_weekly_review, to_food_stalls,
            to_submit_reviews, to_my_profile;

    FloatingActionButton floatingBtn_logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        firebaseAuth = FirebaseAuth.getInstance();

        //找到控件
        mTvUser = findViewById(R.id.user_name);
        mIvHead = findViewById(R.id.iv_head);
        slideMenu = findViewById(R.id.slideMenu);
        mBtnWeeklyReview = findViewById(R.id.btn_main_1);
        mBtnStoreReview = findViewById(R.id.btn_main_2);
        mBtnFoodReview = findViewById(R.id.btn_main_3);
        mBtnTopdownList = findViewById(R.id.btn_main_4);
        to_weekly_review = findViewById(R.id.to_weekly_review);
        to_food_stalls = findViewById(R.id.to_food_stalls);
        to_submit_reviews = findViewById(R.id.to_submit_review);
        floatingBtn_logOut = findViewById(R.id.logOut);
        to_my_profile = findViewById(R.id.to_my_profile);


        getUserInfo();
        //实现侧滑的部分，点击加侧滑
        mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu(); //难的是写SlideMenu java文件，但已经被提供了
            }
        });

        //在主函数中调用
        setListener();
    }
    private void setListener(){
        onClick onClick = new onClick();
        //对每一个按钮进行setOnClickListener
        mBtnWeeklyReview.setOnClickListener(onClick);
        mBtnStoreReview.setOnClickListener(onClick);
        mBtnFoodReview.setOnClickListener(onClick);
        mBtnTopdownList.setOnClickListener(onClick);
        floatingBtn_logOut.setOnClickListener(onClick);


        to_weekly_review.setOnClickListener(onClick);
        to_food_stalls.setOnClickListener(onClick);
        to_submit_reviews.setOnClickListener(onClick);
        to_my_profile.setOnClickListener(onClick);

    }
    private void getUserInfo() {
        // 获取当前登录用户
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // 获取用户的显示名称
            String emailNo = currentUser.getEmail();
            String userFormat = emailNo.substring(0,emailNo.indexOf("@")).replace("_","");
            String userName = "Hello, "+ userFormat +"."+"\nWhat are you looking for...";
            if (userName != null && !userName.isEmpty()) {
                mTvUser.setText(userName);
            }

            //获取用户的头像 URL
            // 假设您按照用户ID来存储头像 userId = userFormat
            DatabaseReference profileImageRef = FirebaseDatabase.getInstance().getReference().child("ProfileImage").child(userFormat);
            profileImageRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String imageUrl = dataSnapshot.child("ImageUrl").getValue(String.class);
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(SlideActivity.this).load(imageUrl).into(mIvHead);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(SlideActivity.this, "Failed to load profile image.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class onClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
//                case R.id.btn_main_1:
//                    intent = new Intent(SlideActivity.this, WeeklyReviewActivity.class);
//                    break;
//                case R.id.btn_main_2:
//                    intent = new Intent(SlideActivity.this, StoreReviewActivity.class);
//                    break;
//                case R.id.btn_main_3:
//                    intent = new Intent(SlideActivity.this, FoodReviewActivity.class);
//                    break;
//                case R.id.btn_main_4:
//                    intent = new Intent(SlideActivity.this, TopdownListActivity.class);
//                    break;
                case R.id.to_weekly_review:
                    intent = new Intent(SlideActivity.this, WeeklyReviewE2Activity.class);
                    break;
                case R.id.to_food_stalls:
                    intent = new Intent(SlideActivity.this, FoodStallsActivity.class);
                    break;
                case R.id.to_submit_review:
                    intent = new Intent(SlideActivity.this, ImageUploadActivity.class); //Review activity
                    break;
                case R.id.to_my_profile:
                    intent = new Intent(SlideActivity.this, MyProfileActivity.class);
                    break;
                case R.id.logOut:
                    intent = new Intent(SlideActivity.this, Login.class);
                    break;
            }
            startActivity(intent);
        }
    }
}