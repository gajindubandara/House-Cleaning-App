package com.example.house_cleaning_app.ui.newPost;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.data.JobDB;
import com.example.house_cleaning_app.model.Job;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddpostFragment extends Fragment {

    private AddpostViewModel mViewModel;
    Spinner roomF,bathroomF;
    Button btnCreate;
    ImageView imgR, imgBR;
    EditText editDate, editLoc, editNoOfR, editNoOfBr, editRSqFt, editBrSqFt;
    Calendar calendar = Calendar.getInstance();
    Bitmap pic;
    boolean BrPicCheck= false;
    boolean RPicCheck= false;

    public static AddpostFragment newInstance() {
        return new AddpostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_addpost, container, false);

        imgBR =view.findViewById(R.id.imgBR);
        imgR =view.findViewById(R.id.imgR);
        editDate =view.findViewById(R.id.Date);
        editLoc =view.findViewById(R.id.Loc);
        editNoOfBr =view.findViewById(R.id.NoBr);
        editNoOfR =view.findViewById(R.id.NoR);
        editRSqFt =view.findViewById(R.id.RSqFt);
        editBrSqFt =view.findViewById(R.id.BrSqFt);


        String[] roomFloor = {"Tile","Cement","Carpet","Wood"};
        String[] bathroomFloor = {"Tile","Polished Cement"};

        //set spinners
        roomF  = view.findViewById(R.id.spinner_roomFloor);
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, roomFloor); //selected item will look like a spinner set from XML
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomF.setAdapter(roomAdapter);

        bathroomF = view.findViewById(R.id.spinner_bathroomFloor);
        ArrayAdapter<String> bathroomAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bathroomFloor); //selected item will look like a spinner set from XML
        bathroomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bathroomF.setAdapter(bathroomAdapter);



        //Date picker
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String date = "dd/mm/yyyy";
                SimpleDateFormat format =new SimpleDateFormat(date, Locale.US);
                editDate.setText(format.format(calendar.getTime()));
            }
        };
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(view.getContext(),listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //room imager picker
        ActivityResultLauncher camLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                pic =(Bitmap) intent.getExtras().get("data");
                imgR.setImageBitmap(pic);
                RPicCheck= true;
            }
        });

        ActivityResultLauncher galleryLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                Uri selectedimage = intent.getData();
                imgR.setImageURI(selectedimage);
                pic = ((BitmapDrawable)imgR.getDrawable()).getBitmap();
                RPicCheck= true;
            }
        });

        imgR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(imgR.getContext());
                builder.setMessage("Please select an option").setTitle("Image Selection").setPositiveButton("Use the camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        camLauncher1.launch(intent);
                    }
                }).setNegativeButton("Select Form Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        galleryLauncher1.launch(intent);
                    }
                });
                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });

        //Bathroom imager picker
        ActivityResultLauncher camLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                pic =(Bitmap) intent.getExtras().get("data");
                imgBR.setImageBitmap(pic);
                BrPicCheck= true;
            }
        });

        ActivityResultLauncher galleryLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                Uri selectedimage = intent.getData();
                imgBR.setImageURI(selectedimage);
                pic = ((BitmapDrawable)imgBR.getDrawable()).getBitmap();
                BrPicCheck= true;
            }
        });

        imgBR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(imgBR.getContext());
                builder.setMessage("Please select an option").setTitle("Image Selection").setPositiveButton("Use the camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        camLauncher2.launch(intent);
                    }
                }).setNegativeButton("Select Form Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        galleryLauncher2.launch(intent);
                    }
                });
                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });

        //add location
        ActivityResultLauncher <Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK);
                {
                    Intent intent = result.getData();
                    LatLng latLng = intent.getParcelableExtra("location");
                    if(latLng != null){
                        String str = String.valueOf(latLng);
                        String[] splitStr = str.split("\\s+");
                        editLoc.setText(splitStr[1]);
                    }
                }
            }
        });
        editLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                launcher.launch(intent);
            }
        });

        //create post
        JobDB jdb=new JobDB();
        btnCreate=view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Validation
                if (checkValid()){

                        int id = 00;
                        String date = editDate.getText().toString();
                        String loc = "http://www.google.com/maps/place/"+editLoc.getText().toString();
                        String nor = editNoOfR.getText().toString();
                        String roomFloor = roomF.getSelectedItem().toString();
                        String bathroomFloor = bathroomF.getSelectedItem().toString();
                        String nobr = editNoOfBr.getText().toString();
                        String image ="";
                        String user = "ben";


                        int RSqFt = Integer.valueOf(editRSqFt.getText().toString());
                        int BrSqFt = Integer.valueOf(editBrSqFt.getText().toString());

                        int priceForRooms =0;
                        int priceForBathrooms =0;

                        //calculate the price for rooms
                        if (roomF.getSelectedItem().toString().equals("Tile")){
                            priceForRooms = RSqFt*250;
                        }
                        else if(roomF.getSelectedItem().toString().equals("Cement")){
                            priceForRooms = RSqFt*500;
                        }
                        else if(roomF.getSelectedItem().toString().equals("Carpet")){
                            priceForRooms = RSqFt*750;
                        }
                        else if(roomF.getSelectedItem().toString().equals("Wood")){
                            priceForRooms = RSqFt*1000;
                        }

                        //calculate the price for bathrooms
                        if (bathroomF.getSelectedItem().toString().equals("Tile")){
                            priceForBathrooms = BrSqFt*250;
                        }
                        else if(bathroomF.getSelectedItem().toString().equals("Polished Cement")){
                            priceForBathrooms = BrSqFt*500;
                        }

                        //Calculate total price
                        String price =String.valueOf(priceForBathrooms+priceForRooms);

                        //creating object
                        Job job=new Job(id,loc,date,nor,roomFloor,nobr,bathroomFloor,price,user,image);

                        //sending to databqse
                        int result=jdb.addJob(job);

                        if (result == 1) {
                            Toast.makeText(getActivity().getApplicationContext(),"New post added",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getActivity().getApplicationContext(),"New post not added",Toast.LENGTH_LONG).show();
                        }

                }
            }
        });
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddpostViewModel.class);
        // TODO: Use the ViewModel
    }
    private boolean checkValid() {
        if (editDate.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Date cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (editLoc.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Location cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (editNoOfR.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"No of Rooms cannot blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (editRSqFt.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Room square Feet cannot blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (editNoOfBr.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "No of Bathrooms cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        if (editBrSqFt.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),"Bathroom square Feet cannot blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (RPicCheck==false) {
            Toast.makeText(getActivity().getApplicationContext(),"Room image cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        if (BrPicCheck==false) {
            Toast.makeText(getActivity().getApplicationContext(),"Bathroom image cannot be blank",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
}