package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;



import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WeeklyReviewActivity extends AppCompatActivity implements RecycleViewInterface {

    private RecyclerView recyclerView;
    private reviewAdapter adapter; // Create Object of the Adapter class
    private DatabaseReference database; // Create object of the Firebase Realtime Database
    private ArrayList<Review> list;

    DrawerLayout layDL;
    NavigationView vNV;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_review);

        layDL = findViewById(R.id.layDL);
        vNV = findViewById(R.id.vNV);
        toolbar = findViewById(R.id.toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layDL, toolbar, R.string.open_drawer, R.string.close_drawer);

        layDL.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            vNV.setCheckedItem(R.id.row_weekly_reviews);
        }
        NavClick();
        // Create an instance of the database and get its reference
        database = FirebaseDatabase.getInstance().getReference("Reviews");

        recyclerView = findViewById(R.id.review_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // To display the RecyclerView linearly
        list = new ArrayList<>();
        adapter = new reviewAdapter(this, list,this);
        recyclerView.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Review review = dataSnapshot.getValue(Review.class);
                    list.add(review);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void NavClick() {
        vNV.setNavigationItemSelectedListener(item -> {
            Fragment frag = null;
            switch (item.getItemId()) {

                case R.id.row_stores:
                    Toast.makeText(this, "Stores", Toast.LENGTH_SHORT).show();
                    Intent storeintent = new Intent(this,AccountPage.class);
                    startActivity(storeintent);
                    break;

                case R.id.row_weekly_reviews:
                    Toast.makeText(this, "weekly_reviews", Toast.LENGTH_SHORT).show();
                    Intent weeklyreviewsintent = new Intent(this,WeeklyReviewActivity.class);
                    startActivity(weeklyreviewsintent);
                    break;

                case R.id.row_logout:
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                    Intent logoutintent = new Intent(this,Login.class);
                    startActivity(logoutintent);
                    finish();
                    break;
            }
            layDL.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(WeeklyReviewActivity.this,TopdownListActivity.class);
        startActivity(intent);
    }
}
