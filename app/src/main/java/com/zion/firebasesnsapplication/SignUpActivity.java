package com.zion.firebasesnsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button_sign_up).setOnClickListener(onClickListener);
        findViewById(R.id.button_login).setOnClickListener(onClickListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.button_sign_up:
                    signUp();
                    break;
                case R.id.button_login:
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class );
                    startActivity(intent);
                    break;

            }
        }
    };

    public void signUp() {
        final String email = ((EditText) findViewById(R.id.edit_text_email)).getText().toString();
        final String password = ((EditText) findViewById(R.id.edit_text_password)).getText().toString();
        String password_verification = ((EditText) findViewById(R.id.edit_text_password_verification)).getText().toString();
        if(email.length() > 0 && password.length() > 0) {
            if (password.equals(password_verification)) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:success");
                                    showToast("회원가입에 성공하셨습니다.");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    // login(email, password);
                                } else {
                                    if (task.getException() != null) {
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthUserCollisionException e) {
                                            e.printStackTrace();
                                            showToast("이미 가입된 이메일입니다.");
                                        } catch (FirebaseAuthWeakPasswordException e) {
                                            e.printStackTrace();
                                            showToast("비밀번호는 6자 이상이어야 합니다.");
                                        } catch (FirebaseAuthInvalidCredentialsException e) {
                                            e.printStackTrace();
                                            showToast("잘못된 이메일 형식입니다.");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        });

            } else {
                showToast("비밀번호가 일치하지 않습니다.");
            }
        } else {
            showToast("이메일 또는 비밀번호를 입력해주세요.");
        }
    }

    public void login(String email, String password) {
        if(email.length() > 0 && password.length() > 0) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class );
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                showToast("Authentication failed.");
                            }
                        }
                    });
        } else {
            showToast("이메일 또는 비밀번호를 입력해주세요.");
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
