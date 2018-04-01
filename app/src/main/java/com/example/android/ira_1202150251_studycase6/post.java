package com.example.android.ira_1202150251_studycase6;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class post extends AppCompatActivity {
    TextView user, title, caption;
    ImageView image;
    EditText sourcekomentar;
    RecyclerView rc;
    AdapterComment adapter;
    ArrayList<DBComm> list;
    DatabaseReference dref;
    ProgressDialog pd;
    String usernya, idfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        user = findViewById(R.id.yangaplod);
        image = findViewById(R.id.gambardaripost);
        sourcekomentar = findViewById(R.id.sourcekomentar);
        pd = new ProgressDialog(this);
        title = findViewById(R.id.judulgambarpost);
        caption = findViewById(R.id.deskripsigambarpost);
        dref = FirebaseDatabase.getInstance().getReference().child("comment");
        rc = findViewById(R.id.rckomentar);
        list = new ArrayList<>();
        adapter = new AdapterComment(this,list);

        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setAdapter(adapter);

        String [] usersaatini  = FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@");
        usernya = usersaatini[0];
        idfoto = getIntent().getStringExtra("key");
        user.setText(getIntent().getStringExtra("user"));
        title.setText(getIntent().getStringExtra("title"));
        caption.setText(getIntent().getStringExtra("caption"));
        Glide.with(this).load(getIntent().getStringExtra("image")).override(250,250).into(image);

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data :dataSnapshot.getChildren()){
                    DBComm cur = data.getValue(DBComm.class);
                    if(cur.getCmtft().equals(idfoto)){
                        list.add(cur);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postcomment(View view) {
        pd.setMessage("Adding comment");
        pd.show();

        //Inisialisasi objek
        DBComm com = new DBComm(usernya, sourcekomentar.getText().toString(), idfoto);

        //Input data ke Firebase
        dref.push().setValue(com).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(post.this, "Successfully added comment!", Toast.LENGTH_SHORT).show();
                    sourcekomentar.setText(null);
                }else{
                    Toast.makeText(post.this, "Failed to add comment!", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }
        });
    }
}