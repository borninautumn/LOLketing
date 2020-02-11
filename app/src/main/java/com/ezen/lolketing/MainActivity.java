package com.ezen.lolketing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView icon_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        icon_info = findViewById(R.id.icon_info);
        icon_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MainActivity", "리그정보 클릭됨");
                Toast.makeText(getApplicationContext(),
                        "리그정보 선택됨", Toast.LENGTH_SHORT).show();
                Intent leagueIntent = new Intent(getApplicationContext(), LeagueInfoActivity.class);
                startActivity(leagueIntent);
            }
        });
    }
}
