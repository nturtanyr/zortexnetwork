package com.example.mynetworkv1.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mynetworkv1.Trader;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Trader> mTrader = new MutableLiveData<Trader>();

    public void setTrader(Trader trader){
        mTrader.setValue(trader);
    }

    public LiveData<String> getTraderDescription() {
        MutableLiveData<String> mDescription = new MutableLiveData<String>();
        if(mTrader.getValue() != null) {
            mDescription.setValue(mTrader.getValue().description);
        }else
        {
            mDescription.setValue("Empty Description");}
        return mDescription;
    }

    public LiveData<String> getTraderTitle() {

        MutableLiveData<String> mTitle = new MutableLiveData<String>();
        if(mTrader.getValue() != null) {
            mTitle.setValue(mTrader.getValue().name);
        }
        else
        {mTitle.setValue("Empty Name");}
        return mTitle;
    }
}