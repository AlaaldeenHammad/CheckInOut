package com.hbusiness.fbtest1122;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Report extends AppCompatActivity {
    ArrayList<String>list=new ArrayList<String>();
    TableLayout tableLayout;
    TableRow tableRow;
    FirebaseAuth mAuth;
    LinearLayout linearLayout;
    SimpleDateFormat SpFormatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        //tableLayout = findViewById(R.id.TableL);
        //tableRow = findViewById(R.id.row1);
        mAuth = FirebaseAuth.getInstance();
        linearLayout=findViewById(R.id.linear);
        db.collection(mAuth.getUid())
                .whereEqualTo("Date",SpFormatter.format(date))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(Report.this, "yeeeeeeeeeeeeeeeeeeeeeees", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Toast.makeText(Report.this, "10203000000", Toast.LENGTH_SHORT).show();
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                TextView TextViewN=new TextView(Report.this);
                                linearLayout.addView(TextViewN);
                                TextViewN.setTextSize(20);
                                TextViewN.setText(document.getData().toString());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        /*db.collection(mAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Report.this, "yeeeeeeeeeeeeeeeeeeeeeees", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                TextView TextViewN=new TextView(Report.this);
                                linearLayout.addView(TextViewN);
                                TextViewN.setText(document.getData());
                            }
                        } else {
                            Toast.makeText(Report.this, "fieeeeeeeeeeeeeeeeeld", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }
}