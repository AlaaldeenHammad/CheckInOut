package com.hbusiness.fbtest1122;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText editText1,editText2;
    Button button1;
    String EmName,ItemName,Quant;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date=new Date();
    Spinner spinner;
    Toolbar toolB;
    String[] array={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    ArrayAdapter<String> stringArrayAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolB = findViewById(R.id.toolbar);
        setSupportActionBar(toolB);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolB, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        editText1=findViewById(R.id.et1);
    editText2=findViewById(R.id.et2);
    button1=findViewById(R.id.b1);
    spinner=findViewById(R.id.Sp1);


    firebaseAuth=FirebaseAuth.getInstance();

        stringArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,array);
        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Quant=array[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void add(View view) {
        if (String.valueOf(editText1.getText()).isEmpty()){
            editText1.setError("Enter employee name !");
            return;
        }
        if (String.valueOf(editText2.getText()).isEmpty()){
            editText2.setError("Enter the item reference !");
            return;
        }
        String UserID=firebaseAuth.getUid();
        EmName= String.valueOf(editText1.getText());
        ItemName=editText2.getText().toString();
        Map<String, Object> user = new HashMap<>();
        user.put("EmployeeName", EmName);
        user.put("ItemName", ItemName);
        user.put("Quantity", Quant);
        user.put("Date",formatter.format(date));

// Add a new document with a generated ID
        db.collection(UserID)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "CAN'T ADD THE ITEM", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.report) {
            Intent intent = new Intent(MainActivity.this, Report.class);
            startActivity(intent);
        } else if (id==R.id.logout){
            firebaseAuth.signOut();
            Intent intent=new Intent(MainActivity.this,Login.class);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}