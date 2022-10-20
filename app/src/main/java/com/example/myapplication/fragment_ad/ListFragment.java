package com.example.myapplication.fragment_ad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.viewpager_adapter.TabAdAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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

    private ViewPager2 layoutContent;
    private LinearLayout layoutActor, layoutDirector, layoutFilm;
    private ImageView icActor, icDirector, icFilm;
    private TextView txtActor, txtDirector, txtFilm;

    private static final int ACTOR_TAB = 0;
    private static final int DIRECTOR_TAB = 1;
    private static final int FILM_TAB = 2;
    private int currentIndex = ACTOR_TAB;

    private TabAdAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        init(view);

        adapter = new TabAdAdapter(getActivity());
        layoutContent.setAdapter(adapter);
        layoutContent.setUserInputEnabled(false);

        layoutActor.setOnClickListener(v -> replaceTabActor());
        layoutDirector.setOnClickListener(v -> replaceTabDirector());
        layoutFilm.setOnClickListener(v -> replaceTabFilm());

        return view;
    }

    private void init(View view) {
        layoutContent = view.findViewById(R.id.layoutContentTabBar);
        layoutActor = view.findViewById(R.id.layoutActor);
        layoutDirector = view.findViewById(R.id.layoutDirector);
        layoutFilm = view.findViewById(R.id.layoutFilm);
        icActor = view.findViewById(R.id.icActorBar);
        icDirector = view.findViewById(R.id.icDirectorBar);
        icFilm = view.findViewById(R.id.icFilmBar);
        txtActor = view.findViewById(R.id.txtActorBar);
        txtDirector = view.findViewById(R.id.txtDirectorBar);
        txtFilm = view.findViewById(R.id.txtFilmBar);
    }

    private void replaceTabActor() {
        if (currentIndex != ACTOR_TAB) {
            currentIndex = ACTOR_TAB;

            // unselected other tabs expect home tab
            txtDirector.setVisibility(View.GONE);
            txtFilm.setVisibility(View.GONE);

//            icFilm.setImageResource(R.drawable.ic_film);
//            imgHistory.setImageResource(R.drawable.ic_history);

            layoutFilm.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutDirector.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // Selected home tab
            txtActor.setVisibility(View.VISIBLE);
            layoutActor.setBackgroundResource(R.drawable.rounded_bg_selected);

            // Create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutActor.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(0);
        }
    }

    private void replaceTabDirector() {
        if (currentIndex != DIRECTOR_TAB) {
            currentIndex = DIRECTOR_TAB;

            // unselected other tabs expect home tab
            txtActor.setVisibility(View.GONE);
            txtFilm.setVisibility(View.GONE);

//            icFilm.setImageResource(R.drawable.ic_film);
//            imgHistory.setImageResource(R.drawable.ic_history);

            layoutFilm.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutActor.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // Selected home tab
            txtDirector.setVisibility(View.VISIBLE);
            layoutDirector.setBackgroundResource(R.drawable.rounded_bg_selected);

            // Create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutDirector.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(1);
        }
    }

    private void replaceTabFilm() {
        if (currentIndex != FILM_TAB) {
            currentIndex = FILM_TAB;

            // unselected other tabs expect home tab
            txtActor.setVisibility(View.GONE);
            txtDirector.setVisibility(View.GONE);

//            icFilm.setImageResource(R.drawable.ic_film);
//            imgHistory.setImageResource(R.drawable.ic_history);

            layoutDirector.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layoutActor.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // Selected home tab
            txtFilm.setVisibility(View.VISIBLE);
            layoutFilm.setBackgroundResource(R.drawable.rounded_bg_selected);

            // Create animation
            ScaleAnimation scaleAnimation =
                    new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            layoutFilm.startAnimation(scaleAnimation);

            layoutContent.setCurrentItem(2);
        }
    }
}