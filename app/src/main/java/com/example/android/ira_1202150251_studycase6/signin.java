package com.example.android.ira_1202150251_studycase6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signin extends AppCompatActivity {
    //mendeklarasikan variable yang akan di gunakan
    EditText email, pswd;
    //firebases authentification fields
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    //progress dialog
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email = (EditText)findViewById(R.id.email);
        pswd = (EditText)findViewById(R.id.pswd);

        //firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        //progress dialog context
        pd = new ProgressDialog(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //checking user presence
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if ( user != null){
                    Intent home = new Intent(signin.this, home.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home);
                    finish();
                }

            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    //Method ketika akctivity berakhir
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    //method ketika activity dimulai
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //method onclick untuk login
    public void masuk(View view) {
        pd.setMessage("Loging in");

        //membaca inputan user
        String inemail = email.getText().toString();
        String inpswd = pswd.getText().toString();
        //jika sudah benar
        if(!TextUtils.isEmpty(inemail)|| !TextUtils.isEmpty(inpswd)){
            //untuk menampilkan dialog
            pd.show();

            //Login dengan email dan password yang diinputkan user
            mAuth.signInWithEmailAndPassword(inemail, inpswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //ketika login berhasil
                    if (task.isSuccessful()){
                        Intent move = new Intent( signin.this, home.class);
                        move.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(move);
                        finish();
                        //saat login gagal
                    }else{
                        Toast.makeText(signin.this, "Login Failed!",Toast.LENGTH_SHORT).show();
                        Log.w("ErrorDatabase", task.getException());
                    }
                    //menutup dialog saat login berhasil atau gagal
                    pd.dismiss();
                }
            });
            //jika user tidak menginputkan apa apa
        }else{
            //untuk memunculkan required sign
            email.setError("Fill in the blank!");
            pswd.setError("Fill in the blank");
            //memunculkan toast field tidak boleh kosong
            Toast.makeText(signin.this, " Field Tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }

    }

    //onclick ketika memencet tombol signup
    public void signup(View view) {
        //Untuk langsung berpindah ke halaman sign up
        startActivity(new Intent(signin.this, regist.class));
    }
}
