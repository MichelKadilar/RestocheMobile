package com.restoche;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;


public class ListeRestoFragment extends ListFragment {
    ItemSelected activity;
    public interface ItemSelected{
        void onItemSelected(int id);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(ItemSelected) context;

    }

    public ListeRestoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> titles= Arrays.asList("Chez Michel ","Chez Kaleb","Chez Adjo","Chez Clément");
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,titles));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        activity.onItemSelected(position);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liste_resto, container, false);
    }
}