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

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mynetworkv1.ui.main.SectionsPagerAdapter;

public class IndividualTrader extends AppCompatActivity {

    public Trader currentTrader = new Trader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO Rather than build from a Trader object, build using the ID and then use the API to call the information again
        // This allows us to keep our memory cache fresh
        // Before running my async code, create the alert dialog object
        String mTraderID = (String) getIntent().getSerializableExtra("Trader");
        Log.d("ZORTEXAPI","Passed ID as " + mTraderID);
        // Exceutes async task that'll begin collecting trader data and update our card view
        setContentView(R.layout.activity_individual_trader);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), mTraderID);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void showAPIAlert(){
        AlertDialog issueWithAPI = new AlertDialog.Builder(this).create();
        issueWithAPI.setTitle("ERROR");
        issueWithAPI.setMessage(this.getResources().getString(R.string.network_error_message));
        issueWithAPI.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        issueWithAPI.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                finish();
            }
        });
        issueWithAPI.show();
    }
}