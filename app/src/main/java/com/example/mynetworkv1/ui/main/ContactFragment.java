package com.example.mynetworkv1.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynetworkv1.CallAPI;
import com.example.mynetworkv1.DialogAlert;
import com.example.mynetworkv1.R;
import com.example.mynetworkv1.Trader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TRADERID = "null";

    private PageViewModel pageViewModel;

    // TODO: Rename and change types of parameters

    public ContactFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String traderID) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TRADERID, traderID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);// Exceutes async task that'll begin collecting trader data and update our card view
        Handler UIHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message inputMessage) {
                Trader trader = ((ArrayList<Trader>) inputMessage.obj).get(0);
                Log.d("HANDLER","Found trader object: " + trader.id);
                switch(inputMessage.what){
                    case 500:
                        Log.d("HANDLER","The CONTACT handler received 500");
                        DialogAlert.showAPIAlert(getContext());
                        break;
                    case 200:
                        Log.d("HANDLER","The CONTACT handler  received 200");
                        pageViewModel.setTrader(trader);
                        Log.d("HANDLER","Updated trader object");
                        break;
                    default:
                        Log.d("HANDLER","The CONTACT handler  received nowt!");
                }
            }
        };
        (new CallAPI(UIHandler, "trader", getArguments().getString(ARG_TRADERID))).execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contact, container, false);
        FloatingActionButton emailButton = root.findViewById(R.id.contact_email);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return root;
    }
}