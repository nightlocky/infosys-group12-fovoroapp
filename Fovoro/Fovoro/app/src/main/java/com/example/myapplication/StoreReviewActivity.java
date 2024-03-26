package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.StoreList.WesternStoreActivity;

public class StoreReviewActivity extends AppCompatActivity {
//    private RecyclerView mRVFinish; //意思是学过的内容（菜谱）

    private ImageView westernStore;
    private ImageView japaneseStore;
    private ImageView taiwanStore;
    private ImageView indianStore;
    private ImageView singaporeStore;
    private ImageView malaysianStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_review);

//        mRVFinish = findViewById(R.id.rv_finish);
        //利用adapter显示item
//        mRVFinish.setLayoutManager(new LinearLayoutManager(StoreReviewActivity.this));
//        //设置adapter
//        mRVFinish.setAdapter(new FoodTypeFinishAdapter(StoreReviewActivity.this, new FoodTypeFinishAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int pos) {
//                Toast.makeText(StoreReviewActivity.this,"click..."+pos,Toast.LENGTH_SHORT).show();
//            }
//        }));

        westernStore = findViewById(R.id.iv_western);
        setListener();

    }
    private void setListener(){
       onClick onClick = new onClick();
       westernStore.setOnClickListener(onClick);

    }
    private class onClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                //1. westernStore
                case R.id.iv_western:
                    intent = new Intent(StoreReviewActivity.this, WesternStoreActivity.class);
                    break;

            }
            startActivity(intent);
        }
    }
}