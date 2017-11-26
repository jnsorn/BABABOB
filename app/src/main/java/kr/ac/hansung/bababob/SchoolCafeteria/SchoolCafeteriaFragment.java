package kr.ac.hansung.bababob;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolCafeteriaFragment extends Fragment {

    ArrayList<StudentMenu> menus;
    RecyclerView rvFoodMenu;

    private static SchoolCafeteriaFragment instance;

    public SchoolCafeteriaFragment() {
        // Required empty public constructor
    }

    public static SchoolCafeteriaFragment getInstance(){
        if(instance == null)
            instance = new SchoolCafeteriaFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_cafeteria, container, false);

        rvFoodMenu = (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        menus = StudentMenu.createContactsList(5);
        SchoolMenuAdapter adapter = new SchoolMenuAdapter(getActivity(),menus);
        rvFoodMenu.setAdapter(adapter);
        rvFoodMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rvFoodMenu.setHasFixedSize(true);
        menus.add(new StudentMenu("PIZZA"));
        adapter.notifyDataSetChanged();
    }
}
