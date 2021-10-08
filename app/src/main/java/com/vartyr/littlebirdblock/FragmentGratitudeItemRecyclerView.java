package com.vartyr.littlebirdblock;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentGratitudeItemRecyclerView extends Fragment {

    private static final String TAG = "FragmentGratitudeItemRecyclerView";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;

    // TODO: Rename and change types of parameters
    private String[] mParam1;       // List of gratitudes should be passed in to the fragment constructor

    protected RecyclerView mRecyclerView;
    protected GratitudeCustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    protected String[] mDataset;
    // protected String[] mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            // Pass in the array of gratitudes to this.
//            mParam1 = getArguments().getStringArray(ARG_PARAM1);
//        }
        // mDataset = new String[] {"one", "chiu", "tres"};
    }

    public void setData(ArrayList<String> data) {
        mDataset = data.toArray(new String[data.size()]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gratitude_item_recycler_view, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GratitudeCustomAdapter(mDataset); // dataset needs to come from remote, so we may want to defer view creation until then
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}