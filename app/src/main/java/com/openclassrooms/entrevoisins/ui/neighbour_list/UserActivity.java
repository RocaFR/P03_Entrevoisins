package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {

    private Neighbour mNeighbour;
    private List<Neighbour> mNeighbourList;
    private NeighbourApiService mApiService;

    // UI
    @BindView(R.id.imageview_neighbour_photo)
    ImageView mNeighbourPhoto;
    @BindView(R.id.textview_neighbour_name)
    TextView mNeighbourName;
    @BindView(R.id.textview_neighbour_name_description)
    TextView mNeighbourNameDescription;
    @BindView(R.id.textview_neighbour_adrress)
    TextView mNeighbourAddress;
    @BindView(R.id.textview_neighbour_phone)
    TextView mNeighbourPhone;
    @BindView(R.id.textview_neighbour_facebook)
    TextView mNeighbourFacebook;
    @BindView(R.id.text_view_neighbour_about_description)
    TextView mNeighbourDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        mApiService = DI.getNeighbourApiService();

        this.initList();
        this.getNeighbourByID();
        this.updateUI();
    }

    /**
     * Navigate to {@link UserActivity}
     * @param pActivity the activity who initiate the navigation
     */
    public static void navigate(Activity pActivity) {
        Intent intent = new Intent(pActivity, UserActivity.class);
        ActivityCompat.startActivity(pActivity, intent, null);
    }

    private void initList() {
        mNeighbourList = mApiService.getNeighbours();
    }

    private void getNeighbourByID() {
        long id = getIntent().getLongExtra(NeighbourFragment.KEY_USER_ID, 0);
        for (Neighbour neighbour : mNeighbourList) {
            if (neighbour.getId() == id) {
                mNeighbour = neighbour;
                Log.d("UserActivity", "getNeighbourByID: " + neighbour.getName());
            }
        }
    }

    private void updateUI() {
        Glide.with(this).load(mNeighbour.getAvatarUrl()).into(mNeighbourPhoto);
        mNeighbourName.setText(mNeighbour.getName());
        mNeighbourNameDescription.setText(mNeighbour.getName());
        mNeighbourAddress.setText(mNeighbour.getAddress());
        mNeighbourPhone.setText(mNeighbour.getPhoneNumber());
        mNeighbourFacebook.setText(mNeighbour.getAvatarUrl());
        mNeighbourDescription.setText(mNeighbour.getAboutMe());
    }
}
