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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ContactFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TRADERID = "null";

    private PageViewModel pageViewModel;

    private TextView traderEmail;
    private TextView traderPhone;
    private TextView traderLocation;

    public ContactFragment() {
        // Required empty public constructor
    }

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
        pageViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class);
        Log.i("test", "ViewModel-> " + pageViewModel.toString());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        traderEmail = root.findViewById(R.id.contact_email);
        traderPhone = root.findViewById(R.id.contact_phone);
        traderLocation = root.findViewById(R.id.contact_location);

        pageViewModel.getTraderEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                traderEmail.setText("Email: " + s);
            }
        });

        pageViewModel.getTraderPhone().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                traderPhone.setText("Phone: " + s);
            }
        });

        pageViewModel.getTraderLocation().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                traderLocation.setText("Location: " + s);
            }
        });

        FloatingActionButton emailButton = root.findViewById(R.id.button_email);

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