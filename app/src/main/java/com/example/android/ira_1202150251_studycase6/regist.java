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

public class regist extends AppCompatActivity {
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
        setContentView(R.layout.activity_regist);

        email = (EditText)findViewById(R.id.mailreg);
        pswd = (EditText)findViewById(R.id.pswdreg);

        //firebase authentication instance
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //checking user presence
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //saat user mengisi dengan benar
                if ( user != null){
                    Intent si = new Intent(regist.this, home.class);
                    si.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(si);
                    finish();
                }
            }
        };
    }
//ketika dimulai
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
//ketika berakhir
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

//untuk pendaftaram
    public void register(View view) {
        pd.setMessage("Creating Account...");
        pd.show();
        String inemail = email.getText().toString().trim();
        String inpswd = pswd.getText().toString().trim();

        //mengecek jika username dan passwordnya kosong atau tidak
        if(!TextUtils.isEmpty(inemail)|| !TextUtils.isEmpty(inpswd)){
            mAuth.createUserWithEmailAndPassword(inemail, inpswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //ketika benar
                    if (task.isSuccessful()) {
                        Toast.makeText(regist.this, "Account created", Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(regist.this, signin.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();

                        //ketika salah dan firebase error
                    } else {
                        Log.w("Firebase ERROR", task.getException());
                        Toast.makeText(regist.this, "Account creation failed!", Toast.LENGTH_SHORT).show();
                        email.setText(null);
                        pswd.setText(null);
                    }
                    pd.dismiss();
                }
            });
        }else {
            //saat fielnya kosong
            Toast.makeText(this, " Field kosong!", Toast.LENGTH_SHORT).show();
            email.setText(null);
            pswd.setText(null);
        }
    }
}
