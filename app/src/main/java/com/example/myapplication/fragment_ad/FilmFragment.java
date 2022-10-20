package com.example.myapplication.fragment_ad;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
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

    private ImageView imgAvt, imgPoster;
    private Button btnChooseAvt, btnChoosePoster, btnClear, btnAdd;
    private EditText inputLinkTrailer, inputLinkFilm, inputListActors, inputListDirectors, inputSummary, inputTime, inputMinAge, inputPoint, inputName;
    private TextView txtPremiere;
    private Spinner spnActor, spnDirector, spnCategory;

    private static final int THUMBNAIL_SIZE = 1280 * 720;
    public static final int MY_REQUEST_CODE_SELECT_AVT = 1;
    public static final int MY_REQUEST_CODE_SELECT_POSTER= 2;

    private Bitmap bitAvt, bitPoster;
    private List<Actor> actors;
    private List<Director> directors;
    private List<String> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_film, container, false);

        init(view);

        txtPremiere.setOnClickListener(v -> showDatePickerDialog());
        btnChooseAvt.setOnClickListener(v -> chooseAvt());
        btnChoosePoster.setOnClickListener(v -> choosePoster());
        btnClear.setOnClickListener(v -> clearForm());
        btnAdd.setOnClickListener(v -> add());

        final String[] inputActor = {""};
        final String[] inputDirector = {""};

        spnActor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemName = spnActor.getSelectedItem().toString().trim();
                inputActor[0] += itemName + ", ";
                inputListActors.setText(inputActor[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spnDirector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemName = spnDirector.getSelectedItem().toString().trim();
                inputDirector[0] += itemName + ", ";
                inputListDirectors.setText(inputDirector[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return view;
    }

    private void init(View view) {
        imgAvt = view.findViewById(R.id.imgAvtFilm);
        imgPoster = view.findViewById(R.id.imgPosterFilm);
        btnChooseAvt = view.findViewById(R.id.btnChooseAvtFilm);
        btnChoosePoster = view.findViewById(R.id.btnChoosePosterFilm);
        inputLinkTrailer = view.findViewById(R.id.inputLinkTrailer);
        inputLinkFilm = view.findViewById(R.id.inputLinkFilm);
        spnActor = view.findViewById(R.id.spnActor);
        inputListActors = view.findViewById(R.id.inputActorName);
        spnDirector = view.findViewById(R.id.spnDirector);
        inputListDirectors = view.findViewById(R.id.inputDirectorName);
        txtPremiere = view.findViewById(R.id.txtPremierScheduleFilm);
        spnCategory = view.findViewById(R.id.spnCategory);
        inputSummary = view.findViewById(R.id.inputSummary);
        inputTime = view.findViewById(R.id.inputTime);
        inputMinAge = view.findViewById(R.id.inputMinAge);
        inputPoint = view.findViewById(R.id.inputPoint);
        btnClear = view.findViewById(R.id.btnClearFilm);
        btnAdd = view.findViewById(R.id.btnAddFilm);
        inputName = view.findViewById(R.id.inputNameFilm);

        bitAvt = null;
        bitPoster = null;

        directors = new ArrayList<>();
        actors = new ArrayList<>();
        categories = new ArrayList<>();

        setDataForSpn();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            txtPremiere.setText(format.format(calendar.getTime()));
        }, nam, thang ,ngay);
        datePickerDialog.show();
    }

    private void chooseAvt() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Title"), MY_REQUEST_CODE_SELECT_AVT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case MY_REQUEST_CODE_SELECT_AVT: {
                assert data != null;
                Uri uri = data.getData();
                imgAvt.setImageURI(uri);
                try {
                    bitAvt = getThumbnail(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } case MY_REQUEST_CODE_SELECT_POSTER: {
                assert data != null;
                Uri uri = data.getData();
                imgPoster.setImageURI(uri);
                try {
                    bitPoster = getThumbnail(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    // Convert uri to bitmap
    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = getActivity().getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = getActivity().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    private void choosePoster() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Title"), MY_REQUEST_CODE_SELECT_POSTER);
    }

    private void clearForm() {
        inputPoint.setText("");
        inputTime.setText("");
        inputMinAge.setText("");
        inputSummary.setText("");
        inputLinkFilm.setText("");
        inputLinkTrailer.setText("");
        inputName.setText("");
        imgPoster.setImageResource(R.mipmap.ic_launcher);
        imgAvt.setImageResource(R.mipmap.ic_launcher);
    }

    private void getData() {
        actors = ActorDatabase.getInstance(getActivity()).actorDAO().getAllActors();
        directors = DirectorDatabase.getInstance(getActivity()).directorDAO().getAllDirectors();

        categories.add("Comedy Movies");
        categories.add("Action Movies");
        categories.add("Sci-fi Movies");
        categories.add("Horror Movies");
        categories.add("War Movies");
        categories.add("Romance Movies");
        categories.add("Musical Film");
        categories.add("Film Noir");
        categories.add("Animated Movies");
        categories.add("Crime Movies");
        categories.add("Drama Movies");
    }

    private void setDataForSpn() {
        getData();
        List<String> listActor = new ArrayList<>();
        List<String> listDirector = new ArrayList<>();
        for (int i=0; i<actors.size(); i++) {
            listActor.add(actors.get(i).getFullName());
        } for (int i=0; i<directors.size(); i++) {
            listDirector.add(directors.get(i).getFullName());
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, listActor);
        spnActor.setAdapter(adapter);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, listDirector);
        spnDirector.setAdapter(adapter);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
        spnCategory.setAdapter(adapter);
    }

    private void add() {
        String linkTrailer = inputLinkTrailer.getText().toString().trim();
        String linkFilm = inputLinkFilm.getText().toString().trim();
        String listActor = inputListActors.getText().toString().trim();
        String listDirector = inputListDirectors.getText().toString().trim();
        String premiereSchedule = txtPremiere.getText().toString().trim();
        String summary = inputSummary.getText().toString().trim();
        String category = spnCategory.getSelectedItem().toString().trim();
        String nameFilm = inputName.getText().toString().trim();

        // Check actors and directors
        boolean isListNull = actors.isEmpty() || directors.isEmpty();
        if (isListNull) {
            Toast.makeText(getActivity(), "List is null!", Toast.LENGTH_SHORT).show();
            return;
        }

        // check null
        boolean checkNullInput = checkNullInput(linkTrailer, linkFilm, premiereSchedule,
                summary, inputTime.getText().toString().trim(),
                inputMinAge.getText().toString().trim(),
                inputPoint.getText().toString().trim(),
                nameFilm) || bitPoster == null || bitAvt == null;
        if (checkNullInput) {
            Toast.makeText(getActivity(), "Please complete all information!", Toast.LENGTH_SHORT).show();

            return;
        }

        int time = Integer.valueOf(inputTime.getText().toString().trim());
        int minAge = Integer.valueOf(inputMinAge.getText().toString().trim());
        double point = Double.valueOf(inputPoint.getText().toString().trim());

        // check url
//        boolean isURLFilm = isURL(linkFilm);
//        boolean isURLTrailer = isURL(linkTrailer);
//        Log.e("check", isURLTrailer+"");
//        Log.e("check", isURLFilm+"");
//        if (!isURLFilm || !isURLTrailer) {
//            Toast.makeText(getActivity(), "Invalid link!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        // Check actor
        boolean isExitActor = isExitActor(listActor);
        if (!isExitActor) {
            Toast.makeText(getActivity(), "Actor name does not exist in the list!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check director
        boolean isExitDirector = isExitDirector(listDirector);
        if (!isExitDirector) {
            Toast.makeText(getActivity(), "Director name does not exits in the list!", Toast.LENGTH_SHORT).show();

            return;
        }

        // Check time and min age
        if (time <= 0) {
            Toast.makeText(getActivity(), "Invalid time!", Toast.LENGTH_SHORT).show();

            return;
        }

        // Check min age
        if (minAge < 13) {
            Toast.makeText(getActivity(), "Minimum age must be greater than or equal to 13!", Toast.LENGTH_SHORT).show();

            return;
        }

        // Check point
        if (point < 0 || point > 10) {
            Toast.makeText(getActivity(), "Invalid score!", Toast.LENGTH_SHORT).show();

            return;
        }

        Film film = new Film(DataConvert.imagemTratada(DataConvert.convertImageToByArr(bitAvt)),
                DataConvert.imagemTratada(DataConvert.convertImageToByArr(bitPoster)),
                linkTrailer, linkFilm, nameFilm, listActor, listDirector, premiereSchedule, category, summary, time, minAge, point);
        FilmDatabase.getInstance(getActivity()).filmDAO().insert(film);
        clearForm();
        Toast.makeText(getActivity(), "Insertion successfully!", Toast.LENGTH_SHORT).show();
    }

    public boolean isURL(String url) {
        try {
            (new java.net.URL(url)).openStream().close();
            return true;
        } catch (Exception ex) { }
        return false;
    }

    private boolean isExitActor(String listActor) {
        String[] arr = listActor.split(",");
        // Clear space in head and last of string
        for (int i=0; i<arr.length; i++) {
            arr[i] = arr[i].trim();
        }

        // Check actor is exit in database
        for (int i=0; i<arr.length; i++) {
            Actor actor = ActorDatabase.getInstance(getActivity()).actorDAO().searchActor(arr[i]);
            if (actor == null) {
                return false;
            }
        }

        return true;
    }

    private boolean isExitDirector(String listDirector) {
        String[] arr = listDirector.split(",");

        // Clear space in head and last of string
        for (int i=0; i<arr.length; i++) {
            arr[i] = arr[i].trim();
        }

        // check director exits in the list
        for (int i=0; i<arr.length; i++) {
            Director director = DirectorDatabase.getInstance(getActivity()).directorDAO().searchDirector(arr[i]);
            if (director == null) {
                return false;
            }
        }

        return true;
    }

    private boolean checkNullInput(String... input) {
        boolean check = false;
        for (int i=0; i<input.length; i++) {
            if (input[i].isEmpty()) {
                check = true;
                break;
            }
        }

        return check;
    }
}