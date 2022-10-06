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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditPostFragment extends Fragment {

    private EditPostViewModel mViewModel;
    Spinner roomF,bathroomF;
    Button btnUpdate;
    ImageView imgR, imgBR;
    EditText editDate, editLoc, editNoOfR, editNoOfBr, editRSqFt, editBrSqFt;
    String dateDb,locDb,NoOfRDb,NoOfBrDb,RSqFtDb,BrSqFtDb,rImgUriDb,bImgUriDb,bfSpinner,rfSpinner;
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
    String brUrl,rUrl;
    int roomPrice =0;
    int bathroomPrice=0;
    String jobID =Temp.getJobID();



    public static EditPostFragment newInstance() {
        return new EditPostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);


        imgBR = view.findViewById(R.id.imgBR);
        imgR = view.findViewById(R.id.imgR);
        editDate = view.findViewById(R.id.editName);
        editLoc = view.findViewById(R.id.Loc);
        editNoOfBr = view.findViewById(R.id.NoBr);
        editNoOfR = view.findViewById(R.id.NoR);
        editRSqFt = view.findViewById(R.id.RSqFt);
        editBrSqFt = view.findViewById(R.id.BrSqFt);
        roomF  = view.findViewById(R.id.spinner_roomFloor);
        bathroomF = view.findViewById(R.id.spinner_bathroomFloor);
        btnUpdate =view.findViewById(R.id.UpdatePost);



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


        //set data
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Job");
        Query getJob = reference.orderByChild("jobID").equalTo(jobID);

        getJob.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot){
                if (snapshot.exists()) {
                    dateDb=snapshot.child(jobID).child("date").getValue(String.class);
                    locDb=snapshot.child(jobID).child("location").getValue(String.class);
                    NoOfBrDb=snapshot.child(jobID).child("noOfBathrooms").getValue(String.class);
                    NoOfRDb=snapshot.child(jobID).child("noOfRooms").getValue(String.class);
                    RSqFtDb=snapshot.child(jobID).child("rsqFt").getValue(String.class);
                    BrSqFtDb=snapshot.child(jobID).child("bsqFt").getValue(String.class);
                    rImgUriDb=snapshot.child(jobID).child("imageR").getValue(String.class);
                    bImgUriDb=snapshot.child(jobID).child("imageBr").getValue(String.class);



                    editDate.setText(snapshot.child(jobID).child("date").getValue(String.class));
                    editLoc.setText(snapshot.child(jobID).child("location").getValue(String.class));
                    editBrSqFt.setText(snapshot.child(jobID).child("bsqFt").getValue(String.class));
                    editRSqFt.setText(snapshot.child(jobID).child("rsqFt").getValue(String.class));
                    editNoOfBr.setText(snapshot.child(jobID).child("noOfBathrooms").getValue(String.class));
                    editNoOfR.setText(snapshot.child(jobID).child("noOfRooms").getValue(String.class));
                    rUrl = snapshot.child(jobID).child("imageR").getValue(String.class);
                    brUrl = snapshot.child(jobID).child("imageBr").getValue(String.class);
                    Picasso.get().load(rUrl).into(imgR);
                    Picasso.get().load(brUrl).into(imgBR);

                    rfSpinner = snapshot.child(jobID).child("rFloorType").getValue(String.class);
                    for (int i = 0; i < Rft.size(); i++) {
                        String temp =Rft.get(i);
                        String[] splitRF = temp.split("[,]", 0);
                        String roomFloor = splitRF[0];
                        if(roomFloor.equals(rfSpinner)){
//                            System.out.println("found! "+Rft.get(i));
                            roomF.setSelection(i);
                            break;
                        }
                    }

                    bfSpinner =snapshot.child(jobID).child("bFloorType").getValue(String.class);

                    for (int i = 0; i < Bft.size(); i++) {
                        String temp =Bft.get(i);
                        String[] splitRF = temp.split("[,]", 0);
                        String roomFloor = splitRF[0];
                        if(roomFloor.equals(bfSpinner)){
//                            System.out.println("found! "+Bft.get(i));
                            bathroomF.setSelection(i);
                            break;
                        }
                    }

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //spinners
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Rft);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomF.setAdapter(roomAdapter);


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
                pic =(Bitmap) intent.getExtras().get("data");
                imgR.setImageBitmap(pic);
                RPicCheck= true;


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


        //update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
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


                    if( editDate.getText().toString().equals(dateDb)&&editLoc.getText().toString().equals(locDb)&&
                            editNoOfR.getText().toString().equals(NoOfRDb)&&editNoOfBr.getText().toString().equals(NoOfBrDb)&&
                            editRSqFt.getText().toString().equals(RSqFtDb)&&editBrSqFt.getText().toString().equals(BrSqFtDb)&&
                            rfSpinner.equals(roomFloor)&&bfSpinner.equals(bathroomFloor)&&RPicCheck==false&&BrPicCheck==false){


                        Toast.makeText(getActivity().getApplicationContext(),"Nothing to update!",Toast.LENGTH_LONG).show();

                    }
                    else{

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


                                                    //creating object
                                                    Job job=new Job(jobID,loc,date,nor,roomFloor,nobr,bathroomFloor,price,user,status, rUrl,brUrl,contractor,rsqft,bsqft);

                                                    referance.child(jobID).setValue(job);
                                                    Toast.makeText(getActivity().getApplicationContext(),"Post Updated!",Toast.LENGTH_LONG).show();
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
                                    // uploading image
                                    try {
                                        storageReference = FirebaseStorage.getInstance().getReference();
                                        if (imgRoomUri != null) {
                                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                            progressDialog.setTitle("Uploading Images...");
                                            progressDialog.show();
                                            progressDialog.setCancelable(false);
                                            StorageReference rRef = storageReference.child("images/" + jobID+"/Room");
                                            uploadTask = rRef.putFile(imgRoomUri);
                                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                                                    Task<Uri> downloadUrlR = rRef.getDownloadUrl();


                                                    downloadUrlR.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            imageRefR = uri.toString();
                                                            referance = rootNode.getReference("Job");
                                                            referance.child(jobID).child("imageR").setValue(imageRefR);
                                                            progressDialog.dismiss();
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        if (imgBathroomUri != null) {
                                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                            progressDialog.setTitle("Uploading Images...");
                                            progressDialog.show();
                                            progressDialog.setCancelable(false);

                                            StorageReference brRef = storageReference.child("images/" + jobID+"/Bathroom");

                                            uploadTask = brRef.putFile(imgBathroomUri);
                                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                                                    Task<Uri> downloadUrlBR = brRef.getDownloadUrl();
                                                    downloadUrlBR.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            imageRefBr = uri.toString();
                                                            referance = rootNode.getReference("Job");
                                                            referance.child(jobID).child("imageBr").setValue(imageRefBr);
                                                            progressDialog.dismiss();
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

                FragmentTransaction trans =getActivity().getSupportFragmentManager().beginTransaction();
                MyPostsFragment fragment = new MyPostsFragment();
                trans.replace(R.id.nav_host_fragment_content_main, fragment);
                trans.addToBackStack(null);
                trans.detach(fragment);
                trans.attach(fragment);
                trans.commit();
            }
        });

        return view;
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditPostViewModel.class);
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

        return true;

    }
}