package com.google.loginassessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static final int RC_SIGN_IN = 1;
    GoogleSignInAccount account;
    Button Googlesignin,Guestsignin;
    FirebaseUser user;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=findViewById(R.id.text);
       progressBar=findViewById(R.id.progressbar);
        getSupportActionBar().setTitle("");
        Googlesignin=findViewById(R.id.googlesignin);
        Guestsignin=findViewById(R.id.guestsignin);
       progressBar.setVisibility(View.GONE);
        Googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                progressBar.setVisibility(View.VISIBLE);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

        Guestsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,NameActivity.class);
                startActivity(i);
            }
        });


         //// For clickable terms and conditions and privacy policy////
        SpannableString ss=new SpannableString(textView.getText().toString());
        ClickableSpan terms=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.google.com/"));
                startActivity(i);
            }
        };
        ClickableSpan policy=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.google.com/"));
                startActivity(i);
            }
        };
        ss.setSpan(terms,40,56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(policy,61,75,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            progressBar.setVisibility(View.VISIBLE);
            Intent i=new Intent(MainActivity.this,HomeActivity.class);
            i.putExtra("Username1",mAuth.getCurrentUser().getDisplayName());
            progressBar.setVisibility(View.GONE);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                 account = task.getResult(ApiException.class);
                 String idToken=account.getIdToken();

                AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
                mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"signInWithCredential:failure",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                intent.putExtra("Username1",account.getDisplayName());
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
                finish();
            } catch (ApiException e) { }
        }
    }
}