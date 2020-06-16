package com.example.mynetworkv1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.MyViewHolder> {
    // The adapter class for our cards, converting our card details to ImageViews

    // define list of strings that will have our card URLs

    // And the image loader that'll hadnle downloading to the cache and displaying the cards
    ImageLoader imageLoader;

    Context currentContext;
    public ArrayList<Trader> traderList = new ArrayList<Trader>();

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
    public CardListAdapter(Context context) {
        // Intialise our string array list
        currentContext = context;
        // Create global configuration and initialize ImageLoader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(currentContext).build();
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // Displaying the image we want at that position in the ImageView
        imageLoader.displayImage(traderList.get(position).card_URL, holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent loadTrader = new Intent(currentContext, IndividualTrader.class);
                loadTrader.putExtra("Trader", traderList.get(position));
                currentContext.startActivity(loadTrader);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return traderList.size();
    }
}

