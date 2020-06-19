package com.example.mynetworkv1.ui.main;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mynetworkv1.Trader;

public class PageViewModel extends ViewModel {

    // Need to comment all this out
    // TODO Convert the Trader object to probably just become a ViewModel extension - replicating objects right now
    private MutableLiveData<Trader> mTrader = new MutableLiveData<Trader>();
    private LiveData<String> mTitle = Transformations.map(mTrader, new Function<Trader, String>() {
        @Override
        public String apply(Trader input) {
            return input.name;
        }
    });
    private LiveData<String> mDescription = Transformations.map(mTrader, new Function<Trader, String>() {
        @Override
        public String apply(Trader input) {
            return input.description;
        }
    });

    public void setTrader(Trader trader) {
        this.mTrader.setValue(trader);
        Log.d("VIEWMODEL","Setting the trader with traderID " + trader.id);
        Log.d("VIEWMODEL","Confirming we now have a trader: " + mTrader.getValue().id);
        Log.d("VIEWMODEL","Title: " + this.getTraderTitle());
        Log.d("VIEWMODEL","Desc: " + this.getTraderDescription());
    }

    public LiveData<String> getTraderDescription() {

        return mDescription;
    }

    public LiveData<String> getTraderTitle() {

        return mTitle;
    }
}