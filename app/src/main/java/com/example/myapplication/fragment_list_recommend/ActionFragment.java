package com.example.myapplication.fragment_list_recommend;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.activities_viewer.FilmActivity;
import com.example.myapplication.adapter_rcv.RecommendFilmAdapter;
import com.example.myapplication.db_film.FilmDatabase;
import com.example.myapplication.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionFragment newInstance(String param1, String param2) {
        ActionFragment fragment = new ActionFragment();
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

    private RecyclerView rcv;
    private List<Film> films;
    private RecommendFilmAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_action, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        rcv = view.findViewById(R.id.rcvActionFilm);
        films = new ArrayList<>();
        adapter = new RecommendFilmAdapter(new RecommendFilmAdapter.ClickItem() {
            @Override
            public void clickItem(Film film) {
                Intent intent = new Intent(getActivity().getBaseContext(), FilmActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("filmObj", film.getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        adapter.setFilms(films);
        loadData();
        rcv.setAdapter(adapter);
    }

    private void loadData() {
        films = getFilms();
        adapter.setFilms(films);
    }

    private List<Film> getFilms() {
        List<Film> list = new ArrayList<>();

        list = FilmDatabase.getInstance(getActivity()).filmDAO().searchFilmByCategory("Action Movies");

        return list;
    }
}