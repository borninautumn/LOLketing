package com.ezen.lolketing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezen.lolketing.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class JoinActivity extends AppCompatActivity {

    // 파이어 베이스 인증
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // 회원가입 버튼 이벤트 처리
        Button btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doJoin();
            }
        });
    } //onCreate()

    // 회원가입
    private void doJoin() {
        EditText join_id = findViewById(R.id.join_id);
        EditText join_pw = findViewById(R.id.join_pw);
        EditText join_pw_ch = findViewById(R.id.join_pw_ch);

        // 예외처리
        if (join_id == null || join_id.getText().toString().length() < 1) {
            Toast.makeText(this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if (join_pw == null || join_pw.getText().toString().length() < 1) {
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if (join_pw_ch == null || join_pw_ch.getText().toString().length() < 1) {
            Toast.makeText(this, "비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
        }
        if (! join_pw.getText().toString().equals(join_pw_ch.getText().toString())) {
            Toast.makeText(this, "입력한 비밀번호와 다릅니다", Toast.LENGTH_SHORT).show();
        }

        final String userId = join_id.getText().toString();
        String userPw = join_pw.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(userId, userPw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // 회원가입 성공
                    users.setId(userId);
                    firestore.collection("Users").document(userId).set(users);
                    Log.e("JoinActivity", "회원가입 성공");
                    Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    // 로그인 화면으로 이동
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else {
                    // 회원가입 실패
                    Log.e("JoinActivity", "회원가입 실패");
                    Toast.makeText(JoinActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } //doJoin()

} //class
