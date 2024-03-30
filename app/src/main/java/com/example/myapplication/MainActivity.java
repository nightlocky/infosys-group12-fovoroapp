package com.example.myapplication;
//
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.util.ToastUtil;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //声明控件
    private Button mBtnLogin;
    private Button mBtnRegister;
    private EditText mEtUser;
    private EditText mEtPassword;

    //firebase
    private TextView textViewData;

//    public StringBuffer getStringFromFirebase() throws IOException {
//        String myFirebaseUrl = "https://stud-term4-androidstudio-default-rtdb.asia-southeast1.firebasedatabase.app/"; // put your URL here
//// 1. CREATE A HttpURLConnection
//        URL url = new URL(myFirebaseUrl);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection(); con.setRequestMethod("GET"); //
//        System.out.println("HTTP Response Status Code:"+con.getResponseCode());
//// 2. Pass the InputStream to BufferedReader object to download the data
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//// 3. Read the data from the BufferedReader object and create the String
//        String inputLine;
//        StringBuffer content = new StringBuffer(); while ((inputLine = in.readLine()) != null) {
//            content.append(inputLine);
//        }
//// 4. Close and Disconnect and display the JSON result
//        in.close();
//        con.disconnect();
////        System.out.println("Downloaded Content:" + content);
//        return content;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到控件
        mBtnLogin = findViewById(R.id.btn_login); //按照id的
        mBtnRegister = findViewById(R.id.btn_register);
        mEtUser = findViewById(R.id.et_1);
        mEtPassword = findViewById(R.id.et_2);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

    }

    public void onClick(View v){
        //匹配对应用户名和密码，才能登陆操作
        String username = mEtUser.getText().toString();
        String password = mEtPassword.getText().toString();
        //弹出内容设置
        String loginSuccess = "Login Success";
        String loginFail = "Login Fail, Please re-enter username or password";
        Intent intent = null;

        switch (v.getId()){
            case R.id.btn_login:
                //正确的username：mike，password：123
                if (username.equals("mike")&&password.equals("123")){
                    //toast普通版
                    //Toast.makeText(getApplicationContext(),ok,Toast.LENGTH_SHORT).show();
                    ToastUtil.showMsg(MainActivity.this,loginSuccess);
                    intent = new Intent(MainActivity.this, SlideActivity.class);
                    startActivity(intent);
                    break;
                }else{
                    //失败
                    //toast提升版，居中显示
                    //Toast toastCenter = Toast.makeText(getApplicationContext(),fail,Toast.LENGTH_SHORT);
                    //toastCenter.setGravity(Gravity.CENTER,0,0);
                    //toastCenter.show();
                    ToastUtil.showMsg(MainActivity.this,loginFail);
                }
                break;
            case R.id.btn_register:
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }
}

