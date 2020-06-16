package com.example.mynetworkv1.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynetworkv1.R;
import com.example.mynetworkv1.Trader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptionFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "defaultTitle";
    private static final String ARG_DESC = "defaultDescription";
    private static final String ARG_SECTION_NUMBER = "defaultDescription";

    private PageViewModel pageViewModel;

    private String mTitle;
    private String mDesc;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DescriptionFragment newInstance(int index, Trader trader) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, index);
        args.putString(ARG_TITLE, trader.name);
        args.putString(ARG_DESC, trader.description);
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
            mTitle = getArguments().getString(ARG_TITLE);
            mDesc = getArguments().getString(ARG_DESC);
        }
        pageViewModel.setIndex(index);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_description, container, false);

        final TextView textTraderTitle = root.findViewById(R.id.trader_title);
        pageViewModel.getTraderTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textTraderTitle.setText(s);
            }
        });
        final TextView textTraderDescription = root.findViewById(R.id.trader_description);
        pageViewModel.getTraderDescription().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textTraderDescription.setText(s);
            }
        });
        return root;
    }
}