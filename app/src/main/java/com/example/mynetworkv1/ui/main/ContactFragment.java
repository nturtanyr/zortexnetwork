package com.example.mynetworkv1.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynetworkv1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.URI;

public class ContactFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TRADERID = "null";

    private PageViewModel pageViewModel;

    private TextView traderEmail;
    private TextView traderPhone;
    private TextView traderLocation;

    private String traderEmailString;
    private String traderPhoneString;

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
                traderEmailString = s;
            }
        });

        pageViewModel.getTraderPhone().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                traderPhone.setText("Phone: " + s);
                traderPhoneString = "tel:"+s;
            }
        });

        pageViewModel.getTraderLocation().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                traderLocation.setText("Location: " + s);
            }
        });

        FloatingActionButton emailButton = root.findViewById(R.id.button_email);

        FloatingActionButton phoneButton = root.findViewById(R.id.button_phone);

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phoneIntent = new Intent (Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse(traderPhoneString));
                getContext().startActivity(phoneIntent);
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent (Intent.ACTION_SEND);
                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{traderEmailString});
                getContext().startActivity(Intent.createChooser(emailIntent, "Send Mail"));
            }
        });

        return root;
    }
}