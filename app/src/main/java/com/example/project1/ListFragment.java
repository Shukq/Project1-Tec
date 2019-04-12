package com.example.project1;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private static final String TAG = "Lista";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_list_fragment,container,false);
        recyclerView = view.findViewById(R.id.list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);
        ArrayList<Restaurant> myDataset = new ArrayList<>();
        Restaurant res1 = new Restaurant("brbrbr1",12345678);
        Restaurant res2 = new Restaurant("brbrbr2",12345678);
        Restaurant res3 = new Restaurant("brbrbr3",12345678);
        myDataset.add(res1);
        myDataset.add(res2);
        myDataset.add(res3);
        // specify an adapter (see also next example)
        mAdapter = new ListAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);




        return view;
    }
}
