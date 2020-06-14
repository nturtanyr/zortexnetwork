package com.example.mynetworkv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public RecyclerView.Adapter cardListAdapter;
    public ArrayList<String> cardList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Exceutes async task that'll begin collecting trader data and update our card view
        (new GetTraders(this)).execute();

        // Define new view that'll contain trader cards
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // We use this setting to improve performance as changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Using a linear layotu manager for this view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Specifying an adapter using the CardListAdapter and our card list
        cardListAdapter = new CardListAdapter(cardList, this);
        recyclerView.setAdapter(cardListAdapter);
    }

}
