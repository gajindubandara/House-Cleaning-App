//package com.kevin.retrievedata;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Handler;
//import android.widget.Toast;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class MainActivity extends AppCompatActivity {
//    SharedPreferences sp;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//
//        //firebase reference
//        DatabaseReference  mRef = FirebaseDatabase.getInstance().getReference();
//
//        //set the database contents for demo1 ("From value to push key")
//        mRef.child("demo").child("-LaFdaiduKysaF").child("points").setValue("10");
//        mRef.child("demo").child("-LaFdaiduKysaF").child("uid").setValue("1532");
//        mRef.child("demo").child("-LaFdaiduKysaF").child("name").setValue("Nobel");
//
//        mRef.child("demo").child("-eOPoisDFaZde").child("points").setValue("asda");
//        mRef.child("demo").child("-eOPoisDFaZde").child("uid").setValue("2568");
//        mRef.child("demo").child("-eOPoisDFaZde").child("name").setValue("Kevin");
//
//        //set the database contents for demo2 ("From value to immediate key")
//        mRef.child("demo2").child("-abc").child("points").setValue("10");
//        mRef.child("demo2").child("-abc").child("uid").setValue("1532");
//        mRef.child("demo2").child("-abc").child("name").setValue("Nobel");
//
//        mRef.child("demo2").child("-cde").child("points").setValue("asda");
//        mRef.child("demo2").child("-cde").child("uid").setValue("2568");
//        mRef.child("demo2").child("-cde").child("name").setValue("Kevin");
//
//
//        //activate solution1
//        solution1();
//
//        //activate solution2 after 5 seconds
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//                solution2();
//            }
//        }, 5000);
//    }
//
//
//    private void solution1() {
//        //How to extract the push key if we want to search using value?
//        //Solution for "From value to push key"
//
//        //firebase reference
//        DatabaseReference  mRef = FirebaseDatabase.getInstance().getReference();
//        mRef.child("demo").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s)
//            {
//                if (dataSnapshot.exists())
//                {
//                    String pushkey = dataSnapshot.getKey();
//                    mRef.child("demo").child(pushkey).addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s)
//                        {
//                            if (dataSnapshot.exists())
//                            {
//                                if (dataSnapshot.getKey().toString().equals("name")) {
//                                    //edit Nobel to any desired value
//                                    if (dataSnapshot.getValue().toString().equals("Nobel")) {
//                                        Toast.makeText(MainActivity.this, "Push Key: " +pushkey, Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                        }
//                        @Override
//                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) { }
//                        @Override
//                        public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
//                        @Override
//                        public void onChildMoved(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChilddemo) { }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) { }
//                    });
//                }
//            }
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) { }
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChilddemo) { }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) { }
//        });
//    }
//
//    private void solution2() {
//        //How to extract the immediate key if we want to search using value?
//        //Solution for "From value to immediate key"
//
//        //firebase reference
//        DatabaseReference  mRef = FirebaseDatabase.getInstance().getReference();
//        mRef.child("demo2").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s)
//            {
//                if (dataSnapshot.exists()) {
//                    String pushkey = dataSnapshot.getKey();
//                    mRef.child("demo2").child(pushkey).addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
//                            if (dataSnapshot.exists()) {
//                                //edit Nobel to any desired value
//                                if (dataSnapshot.getValue().toString().equals("Nobel")) {
//                                    String immediatekey = dataSnapshot.getKey().toString();
//                                    Toast.makeText(MainActivity.this, "Immediate Key: " + immediatekey, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                        @Override
//                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) { }
//                        @Override
//                        public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
//                        @Override
//                        public void onChildMoved(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChilddemo) { }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) { }
//                    });
//                }
//            }
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) { }
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChilddemo) { }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) { }
//        });
//    }
//}