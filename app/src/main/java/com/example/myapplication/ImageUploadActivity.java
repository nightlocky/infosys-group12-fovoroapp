package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ImageUploadActivity extends AppCompatActivity {
    private EditText reviewTitle;
    private EditText reviewComment;
    private EditText reviewStoreName;
    private EditText reviewStoreScore;


    //以下变量都是image的
    private static final int REQUEST_CODE_IMAGE = 101;
    //REQUEST_CODE_IMAGE 是用于标识您启动的活动的请求代码。(line68)
    //在调用 startActivityForResult() 方法时，您可以传递这个请求代码，以便在 onActivityResult() 方法中区分不同的活动返回结果。
    private ImageView imageViewAdd;
    private EditText inputImageName;
    private TextView textViewProgress;
    private ProgressBar progressBar;
    private Button btnUpload;
    Uri imageUri;
    boolean isImageAdded = false;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        reviewTitle = findViewById(R.id.review_title);
        reviewComment = findViewById(R.id.review_comment);
        reviewStoreName = findViewById(R.id.rate_store_name);
        reviewStoreScore = findViewById(R.id.rate_store_score);
        //以上是除image外的评论的部分 到这，要用review里的send来把这些评论啥的放到数据库里，并且根据每个user

        imageViewAdd = findViewById(R.id.imageViewAdd); //set image name
        inputImageName = findViewById(R.id.inputImageName);
        textViewProgress = findViewById(R.id.textViewProgress);
        progressBar = findViewById(R.id.progressBar);
        btnUpload = findViewById(R.id.btn_upload_image);
        textViewProgress.setVisibility(View.GONE); //progressBar进度条 和 textViewProgress进度文本0-100% 设置为可见
        progressBar.setVisibility(View.GONE);

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Food");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Review");
        storageReference = FirebaseStorage.getInstance().getReference().child("FoodImage");

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
                //startActivityForResult 这个方法接收一个 Intent 参数，用于指定要启动的活动，并可选地接收一个整数参数作为请求代码（requestCode）。
                //请求代码是用于在调用方活动中识别返回结果的标识符。
                //当被启动的活动完成并返回结果时，系统会调用调用方活动的 onActivityResult() 方法，并传递包含结果数据的 Intent 对象。
                //在 onActivityResult() 方法中(line131)，可以检查返回结果的请求代码，并获取并处理返回的数据。
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String imageName = inputImageName.getText().toString();
                if (isImageAdded != false && imageName!=null){
                    uploadAll(imageName);
                }
            }
        });
    }

    private void uploadImage(String imageName) {

        textViewProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE); //loading signal

        final String key = databaseReference.push().getKey(); //push就随便给一串代码储存 生成一个唯一的键，用于存储图像引用
        storageReference.child(key+".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            //putFile() 将图像文件上传到 Firebase Storage
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    //if upload successfully, 通过 getDownloadUrl() 方法获取上传后图像的下载链接
                    @Override
                    public void onSuccess(Uri uri) {
                        //after get the download link, use a hashmap stores imgName and imgUrl
                        //store into Firebase Realtime Database
                        HashMap hashMap = new HashMap();
                        hashMap.put("FoodName",imageName); //key1 (imageName is the manually input from user, the name of image)
                        hashMap.put("ImageUrl",uri.toString()); //key2
                        databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//                                Toast.makeText(ImageUploadActivity.this,"Data Successfully Uploaded!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            //use addOnProgressListener 方法监听上传进度，更新进度条和进度文本视图。
            //as the user click the submit button, this method will execute submit, and load to Firebase Realtime Database
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) { //allow user to see the speed of upload, calculate upload efficiency
                double progress = (snapshot.getBytesTransferred()*100) / snapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
                textViewProgress.setText(progress +" %");
            }
        });
    }

    //can also use: 1.ActivityResultContract or 2. (registerForActivityResult) ActivityResultLauncher in the latest version
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //这个方法用于处理从其他活动返回的结果，特别是处理用户选择的图像。
        //当用户选择图像后，会将图像的 URI 存储在 imageUri 变量中，并将 isImageAdded 标志设置为 true，以表示已经添加了图像。
        //然后，将选定的图像设置到 imageViewAdd 中显示给用户。
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_IMAGE && data!= null){
            imageUri = data.getData();
            isImageAdded = true;
            imageViewAdd.setImageURI(imageUri);
        }
    }

    //Upload ALl Things (从uploadImage仿写)
    private void uploadAll(String imageName) {

        textViewProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE); //loading signal

        final String key = databaseReference.push().getKey(); //push就随便给一串代码储存 生成一个唯一的键，用于存储图像引用
        storageReference.child(key+".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            //putFile() 将图像文件上传到 Firebase Storage
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    //if upload successfully, 通过 getDownloadUrl() 方法获取上传后图像的下载链接
                    @Override
                    public void onSuccess(Uri uri) {
                        //after get the download link, use a hashmap stores imgName and imgUrl
                        //store into Firebase Realtime Database
                        HashMap hashMap = new HashMap();
                        hashMap.put("title",reviewTitle.getText().toString());
                        hashMap.put("description",reviewComment.getText().toString());
                        hashMap.put("ratedStoreName",reviewStoreName.getText().toString());
                        hashMap.put("noOfLike",Integer.parseInt(reviewStoreScore.getText().toString()));
                        hashMap.put("foodImageName",imageName); //key1 (imageName is the manually input from user, the name of image)
                        hashMap.put("img",uri.toString()); //key2

                        databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            }
                        });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            //use addOnProgressListener 方法监听上传进度，更新进度条和进度文本视图。
            //as the user click the submit button, this method will execute submit, and load to Firebase Realtime Database
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) { //allow user to see the speed of upload, calculate upload efficiency
                double progress = (snapshot.getBytesTransferred()*100) / snapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
                textViewProgress.setText(progress +" %");
            }
        });
    }
}