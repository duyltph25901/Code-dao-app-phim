package com.example.myapplication.fragment_viewer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.viewpager_adapter.LayoutRecommendAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView tab1, tab2, tab3, tab4, tabSelected;
    private ViewPager2 layoutContent;
    private LayoutRecommendAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);

        return view;
    }

    private void init(View view) {
        tab1 = view.findViewById(R.id.tab1);
        tab2 = view.findViewById(R.id.tab2);
        tab3 = view.findViewById(R.id.tab3);
        tab4 = view.findViewById(R.id.tab4);
        tabSelected = view.findViewById(R.id.txtSelected);
        layoutContent = view.findViewById(R.id.layoutListRecommendFilm);

        adapter = new LayoutRecommendAdapter(getActivity());
        layoutContent.setAdapter(adapter);
        layoutContent.setUserInputEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1: {
                // change tab bar
                tab1.setBackgroundResource(R.drawable.tab_selected_bg);
                tab2.setBackgroundResource(R.drawable.tab_un_selected_tab);
                tab3.setBackgroundResource(R.drawable.tab_un_selected_tab);
                tab4.setBackgroundResource(R.drawable.tab_un_selected_tab);
                tabSelected.animate().x(0).setDuration(500);

                // change layout
                layoutContent.setCurrentItem(0);
                break;
            } case R.id.tab2: {
                tab1.setBackgroundResource(R.drawable.tab_un_selected_tab);
                tab2.setBackgroundResource(R.drawable.tab_selected_bg);
                tab3.setBackgroundResource(R.drawable.tab_un_selected_tab);
                tab4.setBackgroundResource(R.drawable.tab_un_selected_tab);
                int size = tab2.getWidth();
                tabSelected.animate().x(size).setDuration(500);

                layoutContent.setCurrentItem(1);
                break;
            } case R.id.tab3: {
                tab1.setBackgroundResource(R.drawable.tab_un_selected_tab);
                tab2.setBackgroundResource(R.drawable.tab_un_selected_tab);
                tab3.setBackgroundResource(R.drawable.tab_selected_bg);
                tab4.setBackgroundResource(R.drawable.tab_un_selected_tab);
                int size = tab2.getWidth()*2;
                tabSelected.animate().x(size).setDuration(500);

                layoutContent.setCurrentItem(2);
                break;
            } case R.id.tab4: {
                tab1.setBackgroundResource(R.drawable.tab_un_selected_tab);
                tab2.setBackgroundResource(R.drawable.tab_un_selected_tab);
                tab3.setBackgroundResource(R.drawable.tab_un_selected_tab);
                tab4.setBackgroundResource(R.drawable.tab_selected_bg);
                int size = tab2.getWidth()*3;
                tabSelected.animate().x(size).setDuration(500);

                layoutContent.setCurrentItem(3);
                break;
            }
        }
    }
}