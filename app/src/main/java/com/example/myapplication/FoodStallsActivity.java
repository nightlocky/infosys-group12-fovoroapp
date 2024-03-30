package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FoodStallsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button page_new_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_stalls);

        page_new_review = findViewById(R.id.page_new_review);
        page_new_review.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        page_new_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()){
                    case R.id.page_new_review:
                        intent = new Intent(FoodStallsActivity.this, ReviewActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }
}