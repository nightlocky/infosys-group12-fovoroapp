package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    EditText inputSearch;
    RecyclerView recyclerView;
    FloatingActionButton floatingBtn;
    FirebaseRecyclerOptions<Food> options;
    FirebaseRecyclerAdapter<Food,MyViewHolder> adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        inputSearch = findViewById(R.id.inputSearch);
        recyclerView = findViewById(R.id.recyclerView);
        floatingBtn = findViewById(R.id.floatingBtn);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Review");

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ImageUploadActivity.class));
            }
        });

        LoadData("");

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString() != null){
                    LoadData(s.toString());
                } else{
                    LoadData("");
                }
            }
        });
    }

    private void LoadData(String data){
        Query query = databaseReference.orderByChild("foodImageName").startAt(data).endAt(data+"\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(query,Food.class).build();
        adapter = new FirebaseRecyclerAdapter<Food, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Food model) {

                holder.textView.setText(model.getFoodImageName()); //Food.java method
                Picasso.get().load(model.getImg()).into(holder.imageView);
                //Picasso.get().load(model.getImageUrl()).into(holder.imageView): 这一行代码使用 Picasso 图片加载库从给定的 URL 加载图像，并将其设置到 ViewHolder 中的 ImageView 控件中显示出来。
                //model.getImageUrl() 返回当前食物对象的图像 URL。Picasso 是个流行的 Android 图片加载库，可以方便地从网络或本地加载图片，并将其设置到 ImageView 中。

                holder.v.setOnClickListener(new View.OnClickListener() { //这个view就是recycler view里每个食物图片的view。Click之后，就会open对应的activity
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, ViewActivity.class);
                        intent.putExtra("FoodKey",getRef(position).getKey());
                        //使用了 putExtra() 方法来添加一个额外的数据项到 Intent 中。"FoodKey" 是一个键（Key），它用于标识传递的数据项，
                        //而 getRef(position).getKey() 返回了与当前位置（position）对应的数据项的键值。
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
                //LayoutInflater 是 Android 提供的一个类，用于从 XML 布局文件中创建视图对象。from() 方法，可以获取到一个 LayoutInflater 实例，该方法需要传入一个 Context 参数，这里使用了 parent.getContext() 来获取父视图的上下文。
                //inflate (R.layout.single_view, parent, false): 是 LayoutInflater 的 inflate() 方法的调用。将一个 XML 布局文件转换为其对应的视图对象，并且可以指定是否将该视图添加到另一个视图组（parent）中。
                //R.layout.single_view: 这是指定要加载的布局文件的资源 ID。
                //parent: 这是父视图，表示新创建的视图对象将被添加到其下。在这个例子中，parent 是一个 ViewGroup，通常是用来放置新创建的视图的容器。
                //false: 指示是否将新创建的视图对象添加到父视图中的标志。传递 false 表示不将新创建的视图添加到父视图中，因为后面的代码可能会将其手动添加到布局中。
                //View v = ...: 最后，将加载的视图对象赋值给了一个名为 v 的 View 类型变量。这个变量可以用来在后续的代码中引用加载的视图，比如设置其属性、添加到布局中等操作。
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}