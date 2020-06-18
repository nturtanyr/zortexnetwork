package com.example.mynetworkv1;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mynetworkv1.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class IndividualTrader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO Rather than build from a Trader object, build using the ID and then use the API to call the information again
        // This allows us to keep our memory cache fresh
        // Before running my async code, create the alert dialog object
        String mTraderID = (String) getIntent().getSerializableExtra("Trader");
        Log.d("ZORTEXAPI","Passed ID as " + mTraderID);

        setContentView(R.layout.activity_individual_trader);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), mTraderID);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

}