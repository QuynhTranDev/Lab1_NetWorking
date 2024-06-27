package com.example.testing;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnEdit, btnDel;
    TextView tvResults;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        database = FirebaseFirestore.getInstance();
        tvResults = findViewById(R.id.tvResults);
        btnDel = findViewById(R.id.btnDel);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);

        btnAdd.setOnClickListener(v -> insertFirebase(tvResults));
        btnEdit.setOnClickListener(v -> updateFireBase(tvResults));
        btnDel.setOnClickListener(v -> deleteFirebase(tvResults));

        selectDataFormFirebase(tvResults);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    String id = "";
    ToDo toDo = null;
    public void insertFirebase(TextView tvResults) {
        id = UUID.randomUUID().toString();
        toDo = new ToDo(id, "title1", "des1");
        HashMap<String, Object> map = toDo.convertMap();

        database.collection("TODO").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                tvResults.setText("Add Successful");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tvResults.setText("Add Failed"+e.getMessage());
            }
        });

    }

    public void updateFireBase(TextView tvResults) {
        id = "a14ab8af-21a9-40d2-816d-d288644fbed2";
        toDo = new ToDo(id, "newTitle1","newDes1");
        database.collection("TODO").document(toDo.getId()).update(toDo.convertMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                tvResults.setText("Edit Successful");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tvResults.setText("Edit Failed"+e.getMessage());

            }
        });
    }

    private void deleteFirebase(TextView tvResults) {
        id = "a14ab8af-21a9-40d2-816d-d288644fbed2";
        database.collection("TODO").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                tvResults.setText("Del Successful");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tvResults.setText("Del Failed"+e.getMessage());

            }
        });
    }

    String strResults = "";

    public ArrayList<ToDo> selectDataFormFirebase (TextView tvResults) {
        ArrayList<ToDo> list = new ArrayList<>();
        database.collection("TODO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            strResults = "";
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                ToDo toDo1 = documentSnapshot.toObject(ToDo.class);
                                strResults += "Id: "+toDo1.getId()+"\n"+"Title: "+toDo1.getTitle()+"\n";
                                list.add(toDo1);
                            }
                            tvResults.setText(strResults);
                        }
                        else {
                            tvResults.setText("Failed...");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResults.setText("Failed...?");
                    }
                });
        return list;
    }
}