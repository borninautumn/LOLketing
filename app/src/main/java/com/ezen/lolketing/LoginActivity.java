package com.ezen.lolketing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ezen.lolketing.model.Users;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    TextView btn_findId;
    TextView btn_join;

    // 파이어베이스 인증
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    private int RC_SIGN_IN = 1000;
    private GoogleSignInClient googleSignInClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 파이어베이스 인증
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // 로그인
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        // 회원가입으로 넘어가기
        TextView btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("LoginActivity", "회원가입 클릭됨");
                Toast.makeText(getApplicationContext(), "회원가입 클릭됨", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // 구글 이메일로 로그인
        Button btn_google_login = findViewById(R.id.btn_google_login);
        btn_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    } //onCreate()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                Log.e("LoginActivity", "성공");
            } catch (ApiException e) {
                e.printStackTrace();
                Log.e("test", "실패1");
            }
        }

    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String googleId = account.getIdToken();
                        // 구글 로그인 성공
                        if (task.isSuccessful()) {
                            Users users = new Users();
                            users.setId(googleId);
                            firestore.collection("Users").document().set(users);
                            Log.e("LoginActivity", "구글 로그인 성공");
                            Toast.makeText(LoginActivity.this, "구글 로그인 성공",
                                    Toast.LENGTH_SHORT).show();
                            // 메인 액티비티로 이동
                            moveMain();
                        }
                        // 구글 로그인 실패
                        else{
                            Log.e("LoginActivity", "구글 로그인 실패");
                            Toast.makeText(LoginActivity.this, "구글 로그인 실패",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void doLogin() {
        EditText login_id = findViewById(R.id.login_id);
        EditText login_pw = findViewById(R.id.login_pw);

        // 예외처리
        if (login_id == null || login_id.getText().toString().length() < 1) {
            Toast.makeText(this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (login_pw == null || login_pw.getText().toString().length() < 1) {
            Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = login_id.getText().toString();
        String userPw = login_pw.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(userId, userPw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // 로그인 성공
                    Log.e("LoginActivity", "로그인 성공");
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    // 메인 액티비티로 이동
                    moveMain();
                } else {
                    // 로그인 실패
                    Log.e("LoginActivity", "로그인 실패");
                    Toast.makeText(LoginActivity.this, "로그인에 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } //doLogin()

    private void moveMain() {
        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentMain);
    } // moveIntent()
} //class
