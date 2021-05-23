package com.example.orderez;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myViewHolder> {

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myadapter.myViewHolder holder, final int position, @NonNull model model) {
        holder.nameText.setText(model.getName());
        holder.orderText.setText(model.getOrder());
        holder.extraText.setText(model.getExtra());
//        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img)
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext()).setContentHolder(new ViewHolder(R.layout.dialogcontent)).setExpanded(true, 1450).create();

//                View myView = dialogPlus.getHolderView();
//                EditText name = myView.findViewById(R.id.nameText);
//                EditText order = myView.findViewById(R.id.orderText);
//                EditText extra = myView.findViewById(R.id.extraText);
//                Button update = myView.findViewById(R.id.update);
//
//                name.setText(model.getName());
//                order.setText(model.getOrder());
//                extra.setText(model.getExtra());

                dialogPlus.show();

//                update.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete");
                builder.setTitle("Are you sure you want to delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("users").child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });
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
        ImageView edit, delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.img1);
            nameText = (TextView)itemView.findViewById(R.id.nameText);
            orderText = (TextView)itemView.findViewById(R.id.orderText);
            extraText = (TextView)itemView.findViewById(R.id.extraText);
            edit = (ImageView)itemView.findViewById(R.id.editIcon);
            delete = (ImageView)itemView.findViewById(R.id.deleteIcon);
        }
    }
}
