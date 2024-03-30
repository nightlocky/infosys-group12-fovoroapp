package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReviewActivity extends AppCompatActivity {

    private EditText input,input2;
    private Button send_btn,btnUpdate;
    private DatabaseReference rootDataBaseRef;
    private Button btnRead,btnDelete,submit_review;
    private TextView textView, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        input = findViewById(R.id.input);
        input2 = findViewById(R.id.input2);
        send_btn = findViewById(R.id.firebase_sent);
        btnUpdate = findViewById(R.id.btn_update);
        btnRead = findViewById(R.id.read_from_firebase);
        btnDelete = findViewById(R.id.btn_delete);
        textView = findViewById(R.id.text_view);
        textView2 = findViewById(R.id.text_view2);

        submit_review = findViewById(R.id.submit_review);

        submit_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()){
                    case R.id.submit_review:
                        intent = new Intent(ReviewActivity.this, ImageUploadActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });


        //        rootDataBaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("User1"); //m1
        rootDataBaseRef = FirebaseDatabase.getInstance().getReference().child("Users"); //m2
//        rootDataBaseRef = FirebaseDatabase.getInstance().getReference();


        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootDataBaseRef.child("user1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
//                            String data = snapshot.getValue().toString();
//                            textView.setText(data);
                            Object id = map.get("ID");
                            String name = (String) map.get("Name");
                            textView.setText(""+id);
                            textView2.setText(name);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(input.getText().toString());
                String name = input2.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("ID",id);
                hashMap.put("Name",name);
                rootDataBaseRef.child("user1").setValue(hashMap);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() { //update id and name in firebase
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(input.getText().toString());
                String name = input2.getText().toString();
                HashMap hashMap = new HashMap();
                hashMap.put("Id",id);
                hashMap.put("Name",name);

                rootDataBaseRef.child("user1").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        Toast.makeText(ReviewActivity.this, "Your Data is Successfully Updated",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootDataBaseRef.child("user1").child("ID").setValue(null); //m1

//                rootDataBaseRef.child("user1").child("ID").removeValue(); //m2 删掉ID
            }
        });
    }
}

//https://www.youtube.com/watch?v=xb0axUs_UjU&list=PLYx38U7gxBf3pmsHVTUwRT_lGON6ZIBHi&index=6


//rating bar 搜索整个8个店的星星
//小红心要么一直加，要么变不回白心
//


