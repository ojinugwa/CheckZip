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

import java.util.ArrayList;

public class RepRecyclerAdapter extends RecyclerView.Adapter<RepRecyclerAdapter.MyViewHolder> {

    private static final String TAG = "repElectAdapter"; //For

    private ArrayList<String> role = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private Context mContext;

    public RepRecyclerAdapter(ArrayList<String> roles, ArrayList<String> names, Context context) {

        role = roles;
        name = names;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rep_items, parent, false);
        RepRecyclerAdapter.MyViewHolder holder = new RepRecyclerAdapter.MyViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        //Glide.with(mContext).asBitmap().load()

        holder.roleView.setText("Role:  " + role.get(position));
        holder.nameView.setText("Name:  " + name.get(position));
    }

    @Override
    public int getItemCount() {
        return role.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //CircleImagerView image;
        TextView nameView;
        TextView roleView;

        RelativeLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            //image = itemView.findViewById(R.id.image);

            roleView = itemView.findViewById(R.id.role);
            nameView = itemView.findViewById(R.id.rep_elect_name);
            parentLayout = itemView.findViewById(R.id.rep_elect_layout);
        }
    }
}
