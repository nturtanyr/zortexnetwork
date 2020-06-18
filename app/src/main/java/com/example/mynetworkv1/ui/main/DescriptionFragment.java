package com.example.mynetworkv1.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynetworkv1.CallAPI;
import com.example.mynetworkv1.DialogAlert;
import com.example.mynetworkv1.R;
import com.example.mynetworkv1.Trader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptionFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TRADERID = "null";

    private PageViewModel pageViewModel;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DescriptionFragment newInstance(String traderID) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TRADERID, traderID);
        Log.d("TRADERINFO","Reading trader ID as " + traderID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Exceutes async task that'll begin collecting trader data and update our card view
        Handler UIHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message inputMessage) {
                Trader trader = ((ArrayList<Trader>) inputMessage.obj).get(0);
                Log.d("HANDLER","Found trader object: " + trader.id);
                switch(inputMessage.what){
                    case 500:
                        Log.d("HANDLER","The DESCRIPTION handler received 500");
                        DialogAlert.showAPIAlert(getContext());
                        break;
                    case 200:
                        Log.d("HANDLER","The DESCRIPTION handler received 200");
                        pageViewModel.setTrader(trader);
                        Log.d("HANDLER","Updated trader object");
                        break;
                    default:
                        Log.d("HANDLER","The DESCRIPTION handler received nowt!");
                }
            }
        };
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        (new CallAPI(UIHandler, "trader", getArguments().getString(ARG_TRADERID))).execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_description, container, false);

        final TextView textTraderTitle = root.findViewById(R.id.trader_title);
        final TextView textTraderDescription = root.findViewById(R.id.trader_description);

        pageViewModel.getTraderDescription().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textTraderDescription.setText(s);
            }
        });
        pageViewModel.getTraderTitle().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textTraderTitle.setText(s);
            }
        });
        return root;
    }
}