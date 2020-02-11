package com.ezen.lolketing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton icon_info = findViewById(R.id.icon_info);
        icon_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("icon_info", "리그 정보 클릭");
                Toast.makeText(MainActivity.this, "리그 정보 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LeagueInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
