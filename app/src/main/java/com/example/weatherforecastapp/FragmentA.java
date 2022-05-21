package com.example.weatherforecastapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentA extends Fragment {

    ArrayList names;
    RecyclerView recyclerView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_a, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        names = new ArrayList();

        for (int i=0; i<Data.names.length; i++)
        {
            names.add(Data.names);
        }

        HelperAdapter helperAdapter = new HelperAdapter(getContext(), names);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(helperAdapter);

        return view;
    }
}