package com.example.orderez;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myViewHolder> {

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myadapter.myViewHolder holder, int position, @NonNull model model) {
        holder.nameText.setText(model.getName());
        holder.orderText.setText(model.getOrder());
        holder.extraText.setText(model.getExtra());
//        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img)
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView nameText, orderText, extraText;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.img1);
            nameText = (TextView)itemView.findViewById(R.id.nameText);
            orderText = (TextView)itemView.findViewById(R.id.orderText);
            extraText = (TextView)itemView.findViewById(R.id.extraText);
        }
    }
}
