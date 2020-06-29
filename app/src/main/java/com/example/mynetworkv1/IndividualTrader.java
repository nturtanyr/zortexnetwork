package com.example.mynetworkv1;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.mynetworkv1.ui.main.PageViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
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

    PageViewModel pageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This allows us to keep our memory cache fresh

        String mTraderID = (String) getIntent().getSerializableExtra("Trader");
        Log.d("ZORTEXAPI","Passed ID as " + mTraderID);

        Handler UIHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message inputMessage) {
                Trader trader = ((ArrayList<Trader>) inputMessage.obj).get(0);
                Log.d("HANDLER","Found trader object: " + trader.id);
                switch(inputMessage.what){
                    case 500:
                        Log.d("HANDLER","The FRAGMENT handler received 500");
                        DialogAlert.showAPIAlert(getApplicationContext());
                        break;
                    case 200:
                        Log.d("HANDLER","The FRAGMENT handler received 200");
                        pageViewModel.setTrader(trader);
                        Log.d("HANDLER","Updated trader object");
                        break;
                    default:
                        Log.d("HANDLER","The FRAGMENT handler received nowt!");
                }
            }
        };


        setContentView(R.layout.activity_individual_trader);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), mTraderID);
        pageViewModel = (new ViewModelProvider(this)).get(PageViewModel.class);
        Log.i("test", "ViewModel-> " + pageViewModel.toString());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        (new CallTradersEndpoint(UIHandler, "traders_by_id", mTraderID)).execute();

    }

}