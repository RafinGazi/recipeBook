package com.example.recipebook.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipebook.Model.Recipe;
import com.example.recipebook.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    Context context;
    ArrayList<Recipe> arrayList;

    public DataAdapter(Context context, ArrayList<Recipe> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View v= layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Recipe model = arrayList.get(position);
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView2);
        holder.tvName2.setText(model.getName());
        holder.tvChef2.setText(model.getChef());
        holder.tvDesc2.setText(model.getDesc());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView2;
        TextView tvName2;
        TextView tvChef2;
        TextView tvDesc2;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView2= itemView.findViewById(R.id.imageView2);
            tvName2=itemView.findViewById(R.id.tvName2);
            tvChef2=itemView.findViewById(R.id.tvChef2);
            tvDesc2=itemView.findViewById(R.id.tvDesc2);
        }


    }

}
