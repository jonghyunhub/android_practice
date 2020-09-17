package com.chuwhdgus.subway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<info> mList;
    private LayoutInflater mInflate;
    private Context mContext;

    public MyAdapter(Context context, ArrayList<info> items){
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  mInflate.inflate(R.layout.item, parent, false);
        MyViewHolder viewHolder =  new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        //binding
        holder.subWayRouteName.setText(mList.get(position).subwayRouteName);
        holder.stationName.setText(mList.get(position).subwayStationNm);
        holder.end_subwayStation.setText(mList.get(position).endSubWayStationNm);
        holder.arrTime.setText(mList.get(position).arrTime);
        holder.depTime.setText(mList.get(position).depTime);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    //ViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView subWayRouteName;
        public TextView stationName;
        public TextView end_subwayStation;
        public TextView arrTime;
        public TextView depTime;

        public MyViewHolder(View itemView){
            super(itemView);

            subWayRouteName = itemView.findViewById(R.id.subwayRouteName);
            stationName = itemView.findViewById(R.id.stationName);
            end_subwayStation = itemView.findViewById(R.id.end_subwaystation);
            arrTime = itemView.findViewById(R.id.arrTime);
            depTime = itemView.findViewById(R.id.depTime);
        }
    }
}
