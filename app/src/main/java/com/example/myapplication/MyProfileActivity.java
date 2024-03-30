package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class MyProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE = 101;
    private TextView profile_name;
    private ImageView profile_image;
    private Button btnUploadProfileImage;
    private FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;
    StorageReference storageReference;
    Uri imageUri;
    boolean isImageAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        profile_name = findViewById(R.id.profile_name);
        profile_image = findViewById(R.id.iv_profile_image);
        btnUploadProfileImage = findViewById(R.id.btn_upload_profile_image);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ProfileImage");
        storageReference = FirebaseStorage.getInstance().getReference().child("ProfileImageStore");

        getUserInfo();

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);

            }
        });

        btnUploadProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String imageName = profile_name.getText().toString();
                if (isImageAdded != false && imageName!=null){
                    uploadImage(imageName);
                }
            }
        });
    }

    private void getUserInfo() {
        // 获取当前登录用户
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // 获取用户的显示名称
            String emailNo = currentUser.getEmail();
            String userName = emailNo.substring(0,emailNo.indexOf("@")).replace("_","");
            if (userName != null && !userName.isEmpty()) {
                profile_name.setText(userName);
            }
        }
    }


    private void uploadImage(String imageName) {
        final String key = databaseReference.push().getKey(); //push就随便给一串代码储存 生成一个唯一的键，用于存储图像引用
        storageReference.child(key+".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            //putFile() 将图像文件上传到 Firebase Storage
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    //if upload successfully, 通过 getDownloadUrl() 方法获取上传后图像的下载链接
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("ImageUrl",uri.toString());
                        databaseReference.child(imageName).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MyProfileActivity.this,"Your profile image uploaded successfully!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),SlideActivity.class));
                            }
                        });
                    }
                });
            }
        });
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) { //allow user to see the speed of upload, calculate upload efficiency
//
//            }
//        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_IMAGE && data!= null){
            imageUri = data.getData();
            isImageAdded = true;
            profile_image.setImageURI(imageUri);
        }
    }
}