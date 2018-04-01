package com.example.android.ira_1202150251_studycase6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeall extends Fragment {
    RecyclerView rc; DatabaseReference dataref;
    ArrayList<DBPost> list;  AdapterPost adapterPost;

    public homeall() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inisialisasi semua objek
        View v = inflater.inflate(R.layout.f_homeall, container, false);
        rc = v.findViewById(R.id.rchomeall);
        list = new ArrayList<>();
        dataref = FirebaseDatabase.getInstance().getReference().child("post");
        adapterPost = new AdapterPost(list, this.getContext());

        //Menampilkan recyclerview
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rc.setAdapter(adapterPost);

        //Event listener ketika value pada Firebase berubah
        dataref.addValueEventListener(new ValueEventListener() {
            //Digunakan untuk membaca seluruh postingan dari firebase
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    DBPost post = data.getValue(DBPost.class);
                    post.setKey(data.getKey());
                    list.add(post);
                    adapterPost.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

}
