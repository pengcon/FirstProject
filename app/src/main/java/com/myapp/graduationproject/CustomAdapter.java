package com.myapp.graduationproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<MarketItem> localDataSet=new ArrayList<>();
    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.onBind(localDataSet.get(position));
    }


    public void setFriendList(ArrayList<MarketItem> list){
        this.localDataSet = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.d("사이즈","e"+localDataSet.size());
        return localDataSet.size();

    }

    //===== 뷰홀더 클래스 =====================================================
    public static class ViewHolder extends RecyclerView.ViewHolder {
         ImageView image1;
//         ImageView image2;
         TextView title;
         TextView address;
//         TextView phone_number;
         TextView rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image1 = (ImageView) itemView.findViewById(R.id.image1);
//            image2 = (ImageView) itemView.findViewById(R.id.image2);
            title = (TextView) itemView.findViewById(R.id.title);
            address = (TextView) itemView.findViewById(R.id.address);
//            phone_number = (TextView) itemView.findViewById(R.id.phone_number);
//            rating = (TextView) itemView.findViewById(R.id.rating);
        }

        void onBind(MarketItem item) {
            image1.setImageBitmap(item.image1);
//            image2.setImageBitmap(item.image2);
            title.setText(item.title);
            address.setText(item.address);
//            phone_number.setText(item.phone_number);
//            rating.setText(item.rating);
        }
    }
}