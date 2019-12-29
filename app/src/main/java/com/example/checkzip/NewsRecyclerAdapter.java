package com.example.checkzip;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.MyViewHolder> {
    private static final String TAG = "newsAdapter"; //For

    private ArrayList<String> imgUrls = new ArrayList<>();
    private ArrayList<String> headLines = new ArrayList<>();
    private Context mContext;


    public NewsRecyclerAdapter(ArrayList<String> imgUrl, ArrayList<String> headLine, Context context) {

        imgUrls = imgUrl;
        headLines = headLine;
        mContext = context;
    }


    @NonNull
    @Override
    public NewsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.news_items, parent, false);
        NewsRecyclerAdapter.MyViewHolder viewHolder = new NewsRecyclerAdapter.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerAdapter.MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext).asBitmap().load(imgUrls.get(position)).into(holder.image);

        holder.headLine.setText(headLines.get(position));
    }

    @Override
    public int getItemCount() {
        return headLines.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView headLine;
        RelativeLayout parentLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            headLine = itemView.findViewById(R.id.news_header);
            parentLayout = itemView.findViewById(R.id.news_parent_layout);
        }
    }
}
