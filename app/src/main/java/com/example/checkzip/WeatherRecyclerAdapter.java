package com.example.checkzip;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;

public class WeatherRecyclerAdapter extends Adapter<WeatherRecyclerAdapter.MyViewHolder> {

    private static final String TAG = "weatherAdapter"; //For

    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> temp = new ArrayList<>();
    private ArrayList<String> temp_min = new ArrayList<>();
    private ArrayList<String> temp_max = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public WeatherRecyclerAdapter(ArrayList<String> descrip, ArrayList<String> date_in, ArrayList<String> min, ArrayList<String> max, ArrayList<String> current_temp, Context context) {

        description = descrip;
        date = date_in;
        temp = current_temp;
        temp_min = min;
        temp_max = max;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.weather_items, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called.");


        holder.descrip.setText("Description   " + description.get(position));
        holder.day.setText("Date   " + date.get(position));
        holder.current.setText("Temperature   " + temp.get(position) + " F");
        holder.min.setText("Min Temperature   " + temp_min.get(position) + " F");
        holder.max.setText("Max Temperature   " + temp_max.get(position) + " F");
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //CircleImagerView image;
        TextView descrip;
        TextView current;
        TextView min;
        TextView max;
        TextView day;

        RelativeLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = itemView.findViewById(R.id.image);
            day = itemView.findViewById(R.id.date);
            descrip = itemView.findViewById(R.id.description);
            current = itemView.findViewById(R.id.temperature);
            min = itemView.findViewById(R.id.min_temp);
            max = itemView.findViewById(R.id.max_temp);
            //parentLayout = itemView.findViewById(R.id.parent_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }

                    }
                }
            });
        }
    }
}
