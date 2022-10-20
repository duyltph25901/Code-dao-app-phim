package com.example.myapplication.fragment_ad;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.example.myapplication.model.Actor;
import com.example.myapplication.model.Director;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DirectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DirectorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DirectorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DirectorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DirectorFragment newInstance(String param1, String param2) {
        DirectorFragment fragment = new DirectorFragment();
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

    private EditText inputFullName, inputStory;
    private TextView txtDOB;
    private ImageView avt;
    private Spinner spnCountries, spnSex;
    private Button btnChooseImage, btnClear, btnAdd;

    private static final int THUMBNAIL_SIZE = 1280 * 720;
    public static final int MY_REQUEST_CODE_SELECT_IMG = 1;

    private List<String> countries = new ArrayList<>();
    private List<String> genders = new ArrayList<>();
    private Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_director, container, false);

        init(view);

        btnChooseImage.setOnClickListener(v -> chooseImage());
        btnClear.setOnClickListener(v -> clear());
        btnAdd.setOnClickListener(v -> add());
        txtDOB.setOnClickListener(v -> showDatePickerDialog());

        return view;
    }

    private void init(View view) {
        inputFullName = view.findViewById(R.id.inputFullNameDirector);
        inputStory = view.findViewById(R.id.inputStoryDirector);
        txtDOB = view.findViewById(R.id.txtDOBDirector);
        avt = view.findViewById(R.id.imgDirector);
        spnCountries = view.findViewById(R.id.spnCountriesDirector);
        spnSex = view.findViewById(R.id.spnSexDirector);
        btnClear = view.findViewById(R.id.btnClearDirector);
        btnChooseImage = view.findViewById(R.id.btnChooseImageDirector);
        btnAdd = view.findViewById(R.id.btnAddDirector);

        // Get data for list
        countries = getAllNameCountries();
        genders = getGenders();

        // set data
        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, countries);
        countriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCountries.setAdapter(countriesAdapter);
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, genders);
        gendersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSex.setAdapter(gendersAdapter);

        bitmap = null;
    }

    private List<String> getAllNameCountries(){
        List<String> list = new ArrayList<>();

        String[] countryCodes = Locale.getISOCountries();

        for (String countryCode : countryCodes) {

            Locale obj = new Locale("", countryCode);

//            System.out.println("Country Code = " + obj.getCountry()
//                    + ", Country Name = " + obj.getDisplayCountry());

            list.add(obj.getDisplayCountry());
        }

        return list;
    }

    private List<String> getGenders() {
        List<String> list = new ArrayList<>();

        list.add("Male");
        list.add("Female");
        list.add("Other");

        return list;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Title"), MY_REQUEST_CODE_SELECT_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MY_REQUEST_CODE_SELECT_IMG){
            assert data != null;
            Uri uri = data.getData();
            avt.setImageURI(uri);
            try {
                bitmap = getThumbnail(uri);
            } catch (IOException e) {
                e.printStackTrace();
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

    private void clear() {
        avt.setImageResource(R.mipmap.ic_launcher);
        inputFullName.setText("");
        txtDOB.setText("");
        spnSex.setSelection(0);
        spnCountries.setSelection(0);
        inputStory.setText("");
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            txtDOB.setText(format.format(calendar.getTime()));
        }, nam, thang ,ngay);
        datePickerDialog.show();
    }

    private void displayKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void add() {
        String fullName = inputFullName.getText().toString().trim();
        String dob = txtDOB.getText().toString().trim();
        String countries = spnCountries.getSelectedItem().toString().trim();
        String gender = spnSex.getSelectedItem().toString().trim();
        String story = inputStory.getText().toString().trim();

        // Check null
        boolean isNull = fullName.isEmpty() || dob.isEmpty() || story.isEmpty() || bitmap == null;
        if (isNull) {
            Toast.makeText(getActivity(), "Please complete all information!", Toast.LENGTH_SHORT).show();

            return;
        }

        Director director = new Director(DataConvert.imagemTratada(DataConvert.convertImageToByArr(bitmap)), fullName, dob, countries, gender, story);
        DirectorDatabase.getInstance(getActivity()).directorDAO().insert(director);
        clear();
        displayKeyboard();
        Toast.makeText(getActivity(), "Insertion successful!", Toast.LENGTH_SHORT).show();
    }
}