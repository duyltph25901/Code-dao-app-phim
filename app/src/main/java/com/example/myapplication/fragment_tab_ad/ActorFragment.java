package com.example.myapplication.fragment_tab_ad;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter_rcv.ActorAdapter;
import com.example.myapplication.class_supported.DataConvert;
import com.example.myapplication.db_actor.ActorDatabase;
import com.example.myapplication.model.Actor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActorFragment newInstance(String param1, String param2) {
        ActorFragment fragment = new ActorFragment();
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
    private TextView txtListActor;
    private RecyclerView rcv;
    private TextView txtDeleteAll;

    // layout dialog
    private EditText inputFullName;
    private TextView txtDOB;
    private Spinner spnCountries, spnGender;
    private CircleImageView img;
    private Button btnChooseImg, btnDone;
    private ImageView imgClear;

    private List<Actor> actors;
    private ActorAdapter adapter;
    private Bitmap bitmap;

    private static final int THUMBNAIL_SIZE = 1280 * 720;
    public static final int MY_REQUEST_CODE_SELECT_IMG = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_actor_tab, container, false);

        init(view);

        setTextColor(txtListActor,
                getResources().getColor(R.color.color_second_primary),
                getResources().getColor(R.color.color_primary));

        txtDeleteAll.setOnClickListener(v -> deleteAll());

        return view;
    }

    private void init(View view) {
        txtListActor = view.findViewById(R.id.txtTitleActor);
        rcv = view.findViewById(R.id.rcvActor);
        txtDeleteAll = view.findViewById(R.id.txtDeleteAllActor);
        
        actors = new ArrayList<>();
        adapter = new ActorAdapter(getActivity(), new ActorAdapter.ClickItem() {
            @Override
            public void update(Actor actor) {
                showDialogUpdate(actor);
            }

            @Override
            public void delete(Actor actor) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Confirm delete")
                        .setMessage("Do you want to deleteb "+actor.getFullName()+"?")
                        .setNegativeButton("Yes", (dialog, which) -> {
                            ActorDatabase.getInstance(getActivity()).actorDAO().delete(actor);
                            loadData();
                            Toast.makeText(getActivity(), "Delete successfully!", Toast.LENGTH_SHORT).show();
                        })
                        .setPositiveButton("No", null)
                        .show();
            }
        });
        adapter.setActors(actors);
        loadData();
        rcv.setAdapter(adapter);
    }

    private void deleteAll() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Confirm delete")
                .setMessage("Do you want to delete all data?")
                .setNegativeButton("Yes", (dialog, which) -> {
                    ActorDatabase.getInstance(getActivity()).actorDAO().deleterAll();
                    loadData();
                    Toast.makeText(getActivity(), "Delete successfully!", Toast.LENGTH_SHORT).show();
                })
                .setPositiveButton("No", null)
                .show();
    }
    
    private void loadData() {
        actors = ActorDatabase.getInstance(getActivity()).actorDAO().getAllActors();
        adapter.setActors(actors);
    }

    private void setTextColor(TextView txtView, int... color) {
        TextPaint textPaint = txtView.getPaint();
        float width = textPaint.measureText(txtView.getText().toString());

        Shader shader = new LinearGradient(0, 0, width, txtView.getTextSize(), color, null, Shader.TileMode.CLAMP);
        txtView.getPaint().setShader(shader);
        txtView.setTextColor(color[0]);
    }

    private void showDialogUpdate(Actor actor) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_actor);
        Window window = dialog.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initDialog(dialog);
        setDataForSpn();
        setOldData(actor);
        bitmap = null;

        imgClear.setOnClickListener(v -> dialog.cancel());
        btnChooseImg.setOnClickListener(v -> takePicture());
        btnDone.setOnClickListener(v -> update(actor, dialog));
        txtDOB.setOnClickListener(v -> showDatePickerDialog());

        dialog.show();
    }

    private void initDialog(Dialog dialog) {
        inputFullName = dialog.findViewById(R.id.inputFullNameActorUpdate);
        txtDOB = dialog.findViewById(R.id.txtDOBActorUpdate);
        spnCountries = dialog.findViewById(R.id.spnCountriesActorUpdate);
        spnGender = dialog.findViewById(R.id.spnSexActorUpdate);
        img = dialog.findViewById(R.id.imgActorUpdate);
        btnChooseImg = dialog.findViewById(R.id.btnChooseImageActorUpdate);
        btnDone = dialog.findViewById(R.id.btnDoneUpdateActor);
        imgClear = dialog.findViewById(R.id.imgClearDialogUpateActor);
    }

    private void setDataForSpn() {
        List<String> genders = new ArrayList<>();
        List<String> countries = new ArrayList<>();

        genders = getGenders();
        countries = getAllNameCountries();

        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, countries);
        countriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCountries.setAdapter(countriesAdapter);
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, genders);
        gendersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(gendersAdapter);
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

    private void takePicture() {
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
            img.setImageURI(uri);
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

    private void setOldData(Actor actor) {
        img.setImageBitmap(DataConvert.convertByArrToImage(actor.getImg()));
        inputFullName.setText(actor.getFullName());
        txtDOB.setText(actor.getDOB());
        int indexCountries = 0;
        int indexSex = 0;
        for (int i=0; i<getAllNameCountries().size(); ++i) {
            if (getAllNameCountries().get(i).equalsIgnoreCase(actor.getCountries())) {
                indexCountries = i;
                break;
            }
        } for (int i=0; i<getGenders().size(); i++) {
            if (getGenders().get(i).equalsIgnoreCase(actor.getGender())) {
                indexSex = i;
                break;
            }
        }
        spnGender.setSelection(indexSex);
        spnCountries.setSelection(indexCountries);
        bitmap = DataConvert.convertByArrToImage(actor.getImg());
    }

    private void update(Actor actor, Dialog dialog) {
        String fullNameUpdate = inputFullName.getText().toString().trim();
        String DOBUpdate = txtDOB.getText().toString().trim();
        String countriesUpdate = spnCountries.getSelectedItem().toString().trim();
        String genderUpdate = spnGender.getSelectedItem().toString().trim();

        boolean isNullInput = fullNameUpdate.isEmpty() || DOBUpdate.isEmpty();
        if (isNullInput) {
            Toast.makeText(getActivity(), "Please complete all information!", Toast.LENGTH_SHORT).show();

            return;
        }

        actor.setFullName(fullNameUpdate);
        actor.setDOB(DOBUpdate);
        actor.setCountries(countriesUpdate);
        actor.setGender(genderUpdate);
        actor.setImg(DataConvert.convertImageToByArr(bitmap));

        ActorDatabase.getInstance(getActivity()).actorDAO().update(actor);
        loadData();
        Toast.makeText(getActivity(), "Update successfully!", Toast.LENGTH_SHORT).show();
        dialog.cancel();
    }
}