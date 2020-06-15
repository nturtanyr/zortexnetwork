package com.example.mynetworkv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.MyViewHolder> {
    // The adapter class for our cards, converting our card details to ImageViews

    // define list of strings that will have our card URLs
    private ArrayList<String> mDataset;

    // And the image loader that'll hadnle downloading to the cache and displaying the cards
    ImageLoader imageLoader;

    // This defines the view that'll hold our data
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // So an image view is enough for our cards
        public ImageView imageView;
        public MyViewHolder(ImageView v) {
            super(v);
            imageView = v;
        }
    }

    // Then we construct using a list of URIs and our current context
    public CardListAdapter(ArrayList<String> myDataset, Context context) {
        // Intialise our string array list
        mDataset = myDataset;

        // Create global configuration and initialize ImageLoader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view using the card layout
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Displaying the image we want at that position in the ImageView
        imageLoader.displayImage(mDataset.get(position), holder.imageView);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

