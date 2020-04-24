package com.example.parsaniahardik.json_recycler_retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter
    extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder> {

        private LayoutInflater inflater;
        private ArrayList<Model> dataModelArrayList;

       public Adapter(RetrofitActivity inflater, ArrayList<Model> dataModelArrayList) {
        this.inflater = LayoutInflater.from(inflater);
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public RetrofitAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.retro_item, parent, false);
        RetrofitAdapter.MyViewHolder holder = new RetrofitAdapter.MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RetrofitAdapter.MyViewHolder holder, int position) {
        Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.name.setText(dataModelArrayList.get(position).getName());
        holder.country.setText(dataModelArrayList.get(position).getCountry());
        holder.city.setText(dataModelArrayList.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);

            TextView country = (TextView) itemView.findViewById(R.id.country);
            TextView  name = (TextView) itemView.findViewById(R.id.name);
            TextView city = (TextView) itemView.findViewById(R.id.city);
            ImageView iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }


}
