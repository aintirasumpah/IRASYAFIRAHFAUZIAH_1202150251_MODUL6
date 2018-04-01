package com.example.android.ira_1202150251_studycase6;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    ViewPager vpg; TabLayout tbl; AppBarLayout abl; Toolbar tb;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Inisialisasi semua objek
        vpg = findViewById(R.id.vipeg); tbl = findViewById(R.id.tabs);
        abl = findViewById(R.id.abala); tb = findViewById(R.id.toleb);
        auth = FirebaseAuth.getInstance();

        //menentukan toolbar
        setSupportActionBar(tb);

        setupPager(vpg);

        //Mengikat tab layout dengan view pager
        tbl.setupWithViewPager(vpg);


    }

    public void setupPager(ViewPager vpg) {
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        adapter.addFragment(new  homeall(), "NEWEST");
        adapter.addFragment(new homeuser(), "ME");

        vpg.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menulogout) {
            auth.signOut();
            startActivity(new Intent(home.this, signin.class ));
            finish();
        }
        return true;
    }


    //method untuk tombol yang menuju ke upload gambar
    public void addpost(View view) {
        startActivity(new Intent(home.this, upload.class));
    }

    //method untuk ViewPager
    class VPAdapter extends FragmentPagerAdapter{
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> listfragmenttitle = new ArrayList<>();

        public VPAdapter(FragmentManager fm){
            super(fm);
        }

        //untuk penentuan posisi fragment
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        //
        public void addFragment(Fragment f, String title){
            fragmentList.add(f);
            listfragmenttitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listfragmenttitle.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
