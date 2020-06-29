package com.example.mynetworkv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

// TODO The card list should probably be a fragment called by an adapter still in the IndividualTrader activity
// The IndividualTrader activity should act as a host for the cardlist as a fragment, and then shift focus to the tabbed layout when a card is selected.
// That way we can control the viewModel directly in that

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public CardListAdapter cardListAdapter;
    private Handler UIHandler;
    public static HashMap<String, String> catDict = new HashMap<String, String>(){{
        put("All", "");
        put ("Mental Health", "d864e64b-2517-4d03-9da9-7c544aad238a");
        put ("Flooring", "e2aff2dc-4c86-4671-9c3d-3bcc3f8251b5");
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UIHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message inputMessage) {
                ArrayList<Trader> tradersList = (ArrayList<Trader>) inputMessage.obj;
                switch(inputMessage.what){
                    case 500:
                        Log.d("HANDLER","The MAIN handler received 500");
                        DialogAlert.showAPIAlert(getApplicationContext());
                        break;
                    case 200:
                        Log.d("HANDLER","The MAIN handler received 200");
                        cardListAdapter.SetCardList(tradersList);
                        cardListAdapter.notifyDataSetChanged();
                        break;
                    default:
                        Log.d("HANDLER","The MAIN handler received nowt!");
                }
            }
        };
        // Define new view that'll contain trader cards
        recyclerView = findViewById(R.id.my_recycler_view);

        // We use this setting to improve performance as changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Using a linear layotu manager for this view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specifying an adapter using the CardListAdapter and our card list
        cardListAdapter = new CardListAdapter(this);
        recyclerView.setAdapter(cardListAdapter);


        Spinner spinner1 = (Spinner) findViewById(R.id.category_spinner);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                String categoryId = catDict.get(parent.getItemAtPosition(pos).toString());
                Log.d("spinner", categoryId);
                if(categoryId == ""){
                    (new CallTradersEndpoint(UIHandler,"traders",null)).execute();
                }
                else {


                    (new CallTradersEndpoint(UIHandler,"traders_by_category", categoryId)).execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                (new CallTradersEndpoint(UIHandler,"traders",null)).execute();
            }
        });


        (new CallTradersEndpoint(UIHandler,"traders",null)).execute();
    }


}
