package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Button btn_delete;
    DatabaseReference ref,DataRef;
    StorageReference StorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        imageView = findViewById(R.id.image_sinle_view_activity);
        textView = findViewById(R.id.textView_single_view_activity);
        btn_delete = findViewById(R.id.btn_single_view_delete);
        ref = FirebaseDatabase.getInstance().getReference().child("Food");

        String FoodKey = getIntent().getStringExtra("FoodKey"); //FoodKey就是那串数据库每个图片的乱码
        DataRef = FirebaseDatabase.getInstance().getReference().child("Food").child(FoodKey); //现在DataRef能访问到realtime database的：FoodName和ImageUrl
        StorageRef = FirebaseStorage.getInstance().getReference().child("FoodImage").child(FoodKey+".jpg"); //StorageRef 能访问到 storage的

        ref.child(FoodKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String foodName = snapshot.child("FoodName").getValue().toString();
                    String imageUrl = snapshot.child("ImageUrl").getValue().toString();

                    Picasso.get().load(imageUrl).into(imageView);
                    textView.setText(foodName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        StorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                        });
                    }
                });
            }
        });


    }
}