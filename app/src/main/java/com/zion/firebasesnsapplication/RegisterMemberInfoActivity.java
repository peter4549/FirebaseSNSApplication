package com.zion.firebasesnsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterMemberInfoActivity extends AppCompatActivity {
    private final static String TAG = "RegisterMemberInfo";

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member_info);

        user = FirebaseAuth.getInstance().getCurrentUser();
        findViewById(R.id.button_register_member_info).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.button_register_member_info:
                    profileUpdate();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void profileUpdate() {
        String name = ((EditText) findViewById(R.id.edit_text_name)).getText().toString();
        String phone = ((EditText) findViewById(R.id.edit_text_phone)).getText().toString();
        String birthday = ((EditText) findViewById(R.id.edit_text_birthday)).getText().toString();
        String address = ((EditText) findViewById(R.id.edit_text_address)).getText().toString();
        if(name.length() > 0 && phone.length() > 9 && birthday.length() > 4 && address.length() > 0) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            MemberInfo memberInfo = new MemberInfo(name, phone, birthday, address);
            if(user != null) {
                db.collection("users").document(user.getUid())
                        .set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                showToast("회원정보가 등록되었습니다.");
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                                showToast("등록에 실패하였습니다.");
                            }
                        });
            }

            /*
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            if(user != null) {
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                    showToast("회원정보가 등록되었습니다.");
                                    finish();
                                }
                            }
                        });
            }
            */
        } else {
            showToast("회원정보를 입력해주세요.");
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
