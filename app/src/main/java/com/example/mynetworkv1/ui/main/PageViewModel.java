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

    private LiveData<String> mEmail = Transformations.map(mTrader, new Function<Trader, String>() {
        @Override
        public String apply(Trader input) {
            return input.email;
        }
    });

    private LiveData<String> mPhone = Transformations.map(mTrader, new Function<Trader, String>() {
        @Override
        public String apply(Trader input) {
            return input.phone;
        }
    });

    private LiveData<String> mLocation = Transformations.map(mTrader, new Function<Trader, String>() {
        @Override
        public String apply(Trader input) {
            return input.formattedAddress;
        }
    });

    private LiveData<String> mDescription = Transformations.map(mTrader, new Function<Trader, String>() {
        @Override
        public String apply(Trader input) {
            return input.description;
        }
    });

    private LiveData<String> mTraderLogo = Transformations.map(mTrader, new Function<Trader, String>() {
        @Override
        public String apply(Trader input) {
            return input.logo_URL;
        }
    });

    public void setTrader(Trader trader) {
        this.mTrader.setValue(trader);
    }

    public LiveData<String> getTraderDescription() {

        return mDescription;
    }

    public LiveData<String> getTraderEmail() {

        return mEmail;
    }

    public LiveData<String> getTraderPhone() {

        return mPhone;
    }

    public LiveData<String> getTraderLocation() {

        return mLocation;
    }

    public LiveData<String> getTraderTitle() {

        return mTitle;
    }

    public LiveData<String> getTraderLogoURL() {

        return mTraderLogo;
    }
}