package com.example.mynetworkv1.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynetworkv1.GetTraderInfo;
import com.example.mynetworkv1.R;
import com.example.mynetworkv1.Trader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptionFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "0";
    private static final String ARG_TRADERID = "null";

    private PageViewModel pageViewModel;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DescriptionFragment newInstance(int index, String traderID) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, index);
        args.putString(ARG_TRADERID, traderID);
        Log.d("TRADERINFO","Reading trader ID as " + traderID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
        (new GetTraderInfo(this.getContext(), pageViewModel, getArguments().getString(ARG_TRADERID))).execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_description, container, false);

        final TextView textTraderTitle = root.findViewById(R.id.trader_title);
        final TextView textTraderDescription = root.findViewById(R.id.trader_description);
        pageViewModel.getTraderDescription().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textTraderDescription.setText(s);
            }
        });
        pageViewModel.getTraderTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textTraderTitle.setText(s);
            }
        });
        return root;
    }
}