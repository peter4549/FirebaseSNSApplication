package com.zion.firebasesnsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class );
            startActivity(intent);
        } else {
            for (UserInfo userInfo : user.getProviderData()) {
                String name = userInfo.getDisplayName();
                Log.e("Name", "" + name);
                if(name == null) {
                    Intent intent = new Intent(MainActivity.this, RegisterMemberInfoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        }

        findViewById(R.id.button_logout).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_logout:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(MainActivity.this, SignUpActivity.class );
                    startActivity(intent);
                    break;
            }
        }
    };
}
