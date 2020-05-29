package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.entrevoisins.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    /**
     * Navigate to {@link UserActivity}
     * @param pActivity the activity who initiate the navigation
     */
    public static void navigate(Activity pActivity) {
        Intent intent = new Intent(pActivity, UserActivity.class);
        ActivityCompat.startActivity(pActivity, intent, null);
    }
}
