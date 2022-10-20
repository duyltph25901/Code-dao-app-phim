package com.example.myapplication.fragment_tab_ad;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter_rcv.FilmAdapter;
import com.example.myapplication.class_supported.DataConvert;
import com.example.myapplication.db_actor.ActorDatabase;
import com.example.myapplication.db_director.DirectorDatabase;
import com.example.myapplication.db_film.FilmDatabase;
import com.example.myapplication.model.Actor;
import com.example.myapplication.model.Director;
import com.example.myapplication.model.Film;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilmFragment newInstance(String param1, String param2) {
        FilmFragment fragment = new FilmFragment();
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

    // layout fragment
    private EditText inputFullName;
    private ImageButton btnSearch;
    private TextView txtDeleteAll;
    private RecyclerView rcv;

    // layout dialog;
    private EditText inputNameUpdate, inputLinkTrailerUpdate, inputLinkFilmUpdate, inputTimeUpdate, inputAgeUpdate, inputPointUpdate;
    private Button btnUpdate;
    private ImageView imgClearDialog;

    private List<Film> films;
    private FilmAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_film_tap, container, false);

        init(view);

        txtDeleteAll.setOnClickListener(v -> deleteAll());
        btnSearch.setOnClickListener(v -> search());

        return view;
    }

    private void init(View view) {
        inputFullName = view.findViewById(R.id.inputSearchFilmName);
        btnSearch = view.findViewById(R.id.btnSearchFilm);
        txtDeleteAll = view.findViewById(R.id.txtDeleteAllFilm);
        rcv = view.findViewById(R.id.rcvFilm);

        films = new ArrayList<>();
        adapter = new FilmAdapter(new FilmAdapter.ClickItem() {
            @Override
            public void delete(Film film) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Confirm delete")
                        .setMessage("Do you want to delete "+film.getName()+"?")
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FilmDatabase.getInstance(getActivity()).filmDAO().delete(film);
                                loadData();
                                Toast.makeText(getActivity(), "Delete successfully!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("No", null)
                        .show();
            }

            @Override
            public void update(Film film) {
                showDialogUpdate(film);
            }
        });
        adapter.setFilms(films);
        loadData();
        rcv.setAdapter(adapter);
    }

    private void loadData() {
        films = FilmDatabase.getInstance(getActivity()).filmDAO().getFilms();
        adapter.setFilms(films);
    }

    private void deleteAll() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Confirm delete")
                .setMessage("Do you want to delete all?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FilmDatabase.getInstance(getActivity()).filmDAO().deleteAllFilm();
                        loadData();
                        Toast.makeText(getActivity(), "Delete successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("No", null)
                .show();
    }

    private void search() {
        String key = inputFullName.getText().toString().trim();
        films = new ArrayList<>();
        films = FilmDatabase.getInstance(getActivity()).filmDAO().searchFilm(key);
        adapter.setFilms(films);
        displayKeyBoard();
    }

    private void displayKeyBoard(){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void showDialogUpdate(Film film) {
        final Dialog dialogUpdate = new Dialog(getContext());
        dialogUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogUpdate.setContentView(R.layout.dialog_update_film);
        Window window = dialogUpdate.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialogUpdate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        init(dialogUpdate);

        btnUpdate.setOnClickListener(v -> update(film, dialogUpdate));
        imgClearDialog.setOnClickListener(v -> dialogUpdate.cancel());

        dialogUpdate.show();
    }

    private void init(Dialog dialog) {
        inputNameUpdate = dialog.findViewById(R.id.inputFilmNameUpdate);
        inputLinkTrailerUpdate = dialog.findViewById(R.id.inputLinkTrailerUpdate);
        inputLinkFilmUpdate = dialog.findViewById(R.id.inputLinkFilmUpdate);
        inputTimeUpdate = dialog.findViewById(R.id.inputTimeUpdate);
        inputAgeUpdate = dialog.findViewById(R.id.inputLimitAgeUpdate);
        inputPointUpdate = dialog.findViewById(R.id.inputPointFilmUpdate);
        btnUpdate = dialog.findViewById(R.id.btnUpdateFilm);
        imgClearDialog = dialog.findViewById(R.id.imgClearDialogUpdateFilm);
    }

    private void update(Film film, Dialog dialog) {
        String linkFilmNew = inputLinkFilmUpdate.getText().toString().trim();
        String linkTrailerNew = inputLinkTrailerUpdate.getText().toString().trim();
        String filmNameNew = inputNameUpdate.getText().toString().trim();
        String timeNew = inputTimeUpdate.getText().toString().trim();
        String ageNew = inputAgeUpdate.getText().toString().trim();
        String pointNew = inputPointUpdate.getText().toString().trim();

        // Check  null input
        boolean isNullInput = isNullInput(linkFilmNew, linkTrailerNew, filmNameNew, timeNew, ageNew, pointNew);
        if (isNullInput) {
            Toast.makeText(getActivity(), "Please complete all information!", Toast.LENGTH_SHORT).show();

            return;
        }

        // Convert var
        int age = Integer.valueOf(ageNew);
        int time = Integer.valueOf(timeNew);
        double point = Double.valueOf(pointNew);

        // Check age
        if (age < 13) {
            Toast.makeText(getActivity(), "Minimum age must be greater than or equal to 13 !", Toast.LENGTH_SHORT).show();

            return;
        }

        // Check time
        if (time <= 0) {
            Toast.makeText(getActivity(), "Invalid time!", Toast.LENGTH_SHORT).show();

            return;
        }

        // Check point
        if (point < 0 || point > 10) {
            Toast.makeText(getActivity(), "Invalid score!", Toast.LENGTH_SHORT).show();

            return;
        }

        film.setLimitAge(age);
        film.setTime(time);
        film.setPoint(point);
        film.setName(filmNameNew);
        film.setLinkFilm(linkFilmNew);
        film.setLinkTrailer(linkTrailerNew);
        FilmDatabase.getInstance(getActivity()).filmDAO().update(film);
        Toast.makeText(getActivity(), "Update successfully!", Toast.LENGTH_SHORT).show();
        dialog.cancel();
        loadData();
    }

    private boolean isNullInput(String... input) {
        for (int i=0; i<input.length; i++) {
            if (input[i].isEmpty()) return true;
        }

        return false;
    }
}