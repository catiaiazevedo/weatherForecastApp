package com.example.weatherforecastapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelperAdapter extends RecyclerView.Adapter
{
    Context context;
    ArrayList arrayList;

    public HelperAdapter(Context context, ArrayList arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_items, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        viewHolderClass.textView.setText(Data.names[position]);
        viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view.findViewById(R.id.textView) ;

                Intent intent =  new Intent(context, MainActivity2.class);
                intent.putExtra("city", textView.getText().toString());
                context.startActivity(intent);

                Toast.makeText(context, textView.getText().toString()+" city selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder
    {
        TextView textView;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
