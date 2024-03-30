package com.example.myapplication;
//
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.util.ToastUtil;

import java.io.FileWriter;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mBtnRegistration;
    private EditText mEtRegUsername1;
    private EditText mEtRegPassword1;
    private EditText mEtRegUsername2;
    private EditText mEtRegPassword2;
    private Button btn_query_count; //查询用户数量的按钮
    private TextView tv_user_count; //用户数量文本框

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg){
            if (msg.what==0){
                int count = (Integer) msg.obj;
                tv_user_count.setText("The no.of users in database is: "+count);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mBtnRegistration = findViewById(R.id.btn_my_register);
        mEtRegUsername1 = findViewById(R.id.et_ru1);
        mEtRegPassword1 = findViewById(R.id.et_rp1);
        mEtRegUsername2 = findViewById(R.id.et_ru2);
        mEtRegPassword2 = findViewById(R.id.et_rp2);
        btn_query_count = findViewById(R.id.btn_query_count);
        tv_user_count = findViewById(R.id.tv_user_count);

        btn_query_count.setOnClickListener(this);
        mBtnRegistration.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String username1 = mEtRegUsername1.getText().toString();
        String password1 = mEtRegPassword1.getText().toString();
        String username2 = mEtRegUsername2.getText().toString();
        String password2 = mEtRegPassword2.getText().toString();
        String registerSuccess = "Registration Success";
        String registerFail = "Registration Fail, Please choose a different username or " +
                "make sure to enter the same information";
        Intent intent = null;

        switch (v.getId()){
            case R.id.btn_my_register:
                if (isUsernameAvailable(username1) && username1.equals(username2) && password1.equals(password2)){
                    ToastUtil.showMsg(RegisterActivity.this, registerSuccess);
                    //从 RegisterActivity 回到 MainActivity
                    intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    ToastUtil.showMsg(RegisterActivity.this, registerFail);
                }
                break;
            case R.id.btn_query_count:
                doQueryCount();
                break;
        }
    }

    //查询用户数量的方法
    private void doQueryCount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = MySqlHelp.getUserSize();
                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = count;
                //向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }


    //改一下！！
    private boolean isUsernameAvailable(String username) {
        // Add logic to check if the username is available (e.g., not already taken)
        // Can check against a database, shared preferences, or any other storage mechanism
        // For simplicity, let's assume registration is always allowed in this example

        return true;
    }
}


//import androidx.appcompat.app.AppCompatActivity;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.myapplication.dao.UserDao;
//import com.example.myapplication.entity.User;
//
///**
// * function：连接注册页面
// */
//public class RegisterActivity extends AppCompatActivity {
//    private static final String TAG = "mysql-party-register";
//    EditText userAccount = null;
//    EditText userPassword = null;
//    EditText userName = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        userAccount = findViewById(R.id.userAccount);
//        userPassword = findViewById(R.id.userPassword);
//        userName = findViewById(R.id.userName);
//    }
//
//
//    public void register(View view){
//
//        String userAccount1 = userAccount.getText().toString();
//        String userPassword1 = userPassword.getText().toString();
//        String userName1 = userName.getText().toString();
//
//
//        User user = new User();
//
//        user.setUserAccount(userAccount1);
//        user.setUserPassword(userPassword1);
//        user.setUserName(userName1);
//        user.setUserType(1);
//        user.setUserState(0);
//        user.setUserDel(0);
//
//        new Thread(){
//            @Override
//            public void run() {
//
//                int msg = 0;
//
//                UserDao userDao = new UserDao();
//
//                User uu = userDao.findUser(user.getUserAccount());
//                if(uu != null){
//                    msg = 1;
//                }
//                else{
//                    boolean flag = userDao.register(user);
//                    if(flag){
//                        msg = 2;
//                    }
//                }
//                hand.sendEmptyMessage(msg);
//
//            }
//        }.start();
//
//
//    }
//    @SuppressLint("HandlerLeak")
//    final Handler hand = new Handler()
//    {
//        public void handleMessage(Message msg) {
//            if(msg.what == 0) {
//                Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();
//            } else if(msg.what == 1) {
//                Toast.makeText(getApplicationContext(),"该账号已经存在，请换一个账号",Toast.LENGTH_LONG).show();
//            } else if(msg.what == 2) {
//                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent();
//                //将想要传递的数据用putExtra封装在intent中
//                intent.putExtra("a","注册");
//                setResult(RESULT_CANCELED,intent);
//                finish();
//            }
//        }
//    };
//}
