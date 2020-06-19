package com.example.mynetworkv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

// TODO The card list should probably be a fragment called by an adapter still in the IndividualTrader activity
// The IndividualTrader activity should act as a host for the cardlist as a fragment, and then shift focus to the tabbed layout when a card is selected.
// That way we can control the viewModel directly in that

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public CardListAdapter cardListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler UIHandler = new Handler(Looper.getMainLooper()){
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

        (new CallAPI(UIHandler,"traders",null)).execute();
    }


}
