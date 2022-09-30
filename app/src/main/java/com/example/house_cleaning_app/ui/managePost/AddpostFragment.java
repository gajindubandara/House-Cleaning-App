package com.example.house_cleaning_app.ui.managePost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.house_cleaning_app.R;
import com.example.house_cleaning_app.Temp;
import com.example.house_cleaning_app.model.Job;
import com.example.house_cleaning_app.ui.post.MyPostsFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    DatabaseReference referance;
    FirebaseDatabase rootNode;
    private Uri imgRoomUri;
    private Uri imgBathroomUri;
    private StorageTask uploadTask;
    String key;
    StorageReference storageReference;
    String imageRefR =" ";
    String imageRefBr =" ";
    String price ="";
    String priceForRoomType ="";
    String priceForBathroomType="";
    String typeID;
    int roomPrice =0;
    int bathroomPrice=0;





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





        //set spinners
        ArrayList<String> Rft = new ArrayList<>();
        ArrayList<String> Bft = new ArrayList<>();

        Rft.add("Select Room Floor Type-");
        Bft.add("Select Bathroom Floor Type-");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("FloorType");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    String type =postSnapshot.child("type").getValue(String.class);
                    String typePrice =postSnapshot.child("price").getValue(String.class);
                    typeID =postSnapshot.child("key").getValue(String.class);
                    Temp.setTypeID(typeID);
                    String value =type+",  (Rs. "+typePrice+".00 per SqFt)";
                    Rft.add(value);
                    Bft.add(value);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        roomF  = view.findViewById(R.id.spinner_roomFloor);
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Rft);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomF.setAdapter(roomAdapter);

        bathroomF = view.findViewById(R.id.spinner_bathroomFloor);
        ArrayAdapter<String> bathroomAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Bft);
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
//                imgRoomUri = intent.getData();
                pic =(Bitmap) intent.getExtras().get("data");
                imgR.setImageBitmap(pic);
                RPicCheck= true;
                imgR.setImageBitmap(pic);

                Bitmap imgRoomBitmap =(Bitmap) intent.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imgRoomBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
                String  path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),imgRoomBitmap,"val",null);
                imgRoomUri =Uri.parse(path);

            }
        });


        ActivityResultLauncher galleryLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                imgRoomUri = intent.getData();
                imgR.setImageURI(imgRoomUri);
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

                Bitmap imgRoomBitmap =(Bitmap) intent.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imgRoomBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
                String  path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),imgRoomBitmap,"val",null);
                imgBathroomUri =Uri.parse(path);
            }
        });


        ActivityResultLauncher galleryLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                imgBathroomUri = intent.getData();
                imgBR.setImageURI(imgBathroomUri);
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
                        editLoc.setText("http://www.google.com/maps/place/"+splitStr[1]);
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
        btnCreate=view.findViewById(R.id.NewPost);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                referance=rootNode.getReference("Job");

                //Validation
                if (checkValid()){
                        String date = editDate.getText().toString();
                        String loc = editLoc.getText().toString();
                        String nor = editNoOfR.getText().toString();
                        String rF = String.valueOf(roomF.getSelectedItem());
                        String[] splitRF = rF.split("[,]", 0);
                        String roomFloor = splitRF[0];
                        String brF = String.valueOf(bathroomF.getSelectedItem());
                        String[] splitBRF = brF.split("[,]", 0);
                        String bathroomFloor =splitBRF[0];
                        String nobr = editNoOfBr.getText().toString();
                        String user = Temp.getNIC();
                        String status ="1";
                        int RSqFt = Integer.valueOf(editRSqFt.getText().toString());
                        int BrSqFt = Integer.valueOf(editBrSqFt.getText().toString());
                        String rsqft =editRSqFt.getText().toString();
                        String bsqft =editBrSqFt.getText().toString();


                    DatabaseReference rootRev = FirebaseDatabase.getInstance().getReference("FloorType");
                    Query getRoomF = rootRev.orderByChild("type").equalTo(roomFloor);

                    getRoomF.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.exists()){
                               priceForRoomType =snapshot.child(roomFloor).child("price").getValue(String.class);
                               roomPrice = Integer.valueOf(priceForRoomType)*RSqFt;
                               Query getBathroomF = rootRev.orderByChild("type").equalTo(bathroomFloor);

                                getBathroomF.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if(snapshot.exists()){
                                            priceForBathroomType =snapshot.child(bathroomFloor).child("price").getValue(String.class);

                                            bathroomPrice = Integer.valueOf(priceForBathroomType)*BrSqFt;
                                            price =String.valueOf(roomPrice+bathroomPrice);

                                            //Storing the job data
                                            try{
                                                String contractor = "";

                                                //Sending data to the database
                                                rootNode = FirebaseDatabase.getInstance();
                                                referance = rootNode.getReference("Job");
                                                String key= referance.push().getKey();

                                                //creating object
                                                Job job=new Job(key,loc,date,nor,roomFloor,nobr,bathroomFloor,price,user,status, imageRefR,imageRefBr,contractor,rsqft,bsqft);

                                                referance.child(key).setValue(job);
                                            }catch(Exception ex)
                                            {
                                                throw ex;
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    //getting the key
                    rootNode = FirebaseDatabase.getInstance();
                    referance = rootNode.getReference("Job");
                    referance.limitToLast(1).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s)
                        {
                            if (dataSnapshot.exists())
                            {
                               key = dataSnapshot.getKey();
                               // uploading image
                                try {
                                    storageReference = FirebaseStorage.getInstance().getReference();
                                    if (imgRoomUri != null && imgBathroomUri != null) {
                                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                        progressDialog.setTitle("Positing...");
                                        progressDialog.show();
                                        progressDialog.setCancelable(false);
                                        StorageReference rRef = storageReference.child("images/" + key+"/Room");
                                        StorageReference brRef = storageReference.child("images/" + key+"/Bathroom");
                                        uploadTask = rRef.putFile(imgRoomUri);
                                        uploadTask = brRef.putFile(imgBathroomUri);
                                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                                                Task<Uri> downloadUrlR = rRef.getDownloadUrl();
                                                Task<Uri> downloadUrlBR = brRef.getDownloadUrl();

                                                downloadUrlR.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageRefR = uri.toString();
                                                        referance = rootNode.getReference("Job");
                                                        referance.child(key).child("imageR").setValue(imageRefR);
                                                        progressDialog.dismiss();
                                                    }
                                                });
                                                downloadUrlBR.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageRefBr = uri.toString();
                                                        referance = rootNode.getReference("Job");
                                                        referance.child(key).child("imageBr").setValue(imageRefBr);
                                                        progressDialog.dismiss();

                                                        Toast.makeText(getActivity().getApplicationContext(),"Post Created!",Toast.LENGTH_LONG).show();
                                                        FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                                                        MyPostsFragment fragment = new MyPostsFragment();
                                                        trans.replace(R.id.nav_host_fragment_content_main, fragment);
                                                        trans.addToBackStack(null);
                                                        trans.detach(fragment);
                                                        trans.attach(fragment);
                                                        trans.commit();



                                                    }
                                                });
                                            }
                                        });
                                    }
                                }catch(Exception ex){
                                    throw ex;
                                }
                            }
                        }
                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) { }
                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChilddemo) { }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });

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
        String strR = roomF.getSelectedItem().toString();
        String[] splitStrR = strR.split("\\s+");
        if (splitStrR[0].equals("Select")) {
            Toast.makeText(getActivity().getApplicationContext(),"Select a Room Floor Type",Toast.LENGTH_LONG).show();
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
        String strB = bathroomF.getSelectedItem().toString();
        String[] splitStrB = strB.split("\\s+");
        if (splitStrB[0].equals("Select")){
            Toast.makeText(getActivity().getApplicationContext(),"Select a Bathroom Floor Type",Toast.LENGTH_LONG).show();
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