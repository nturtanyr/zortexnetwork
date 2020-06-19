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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynetworkv1.CallAPI;
import com.example.mynetworkv1.DialogAlert;
import com.example.mynetworkv1.R;
import com.example.mynetworkv1.Trader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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

    private TextView textTraderTitle;
    private TextView textTraderDescription;
    ImageLoader imageLoader;
    private ImageView traderLogo;

    public DescriptionFragment() {
        // Required empty public constructor
    }

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

        pageViewModel = (new ViewModelProvider(requireActivity())).get(PageViewModel.class);
        Log.i("test", "ViewModel-> " + pageViewModel.toString());

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.requireContext()).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_description, container, false);

        textTraderTitle = root.findViewById(R.id.trader_title);
        textTraderDescription = root.findViewById(R.id.trader_description);
        traderLogo = root.findViewById(R.id.trader_logo);
        Log.d("VIEWMODEL", "lifecycleowner-> " + getViewLifecycleOwner().toString());

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
        pageViewModel.getTraderLogoURL().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                imageLoader.displayImage(s, traderLogo);
            }
        });


        return root;
    }
}