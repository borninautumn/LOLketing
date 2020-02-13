package com.ezen.lolketing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ImageView icon_info;
    BottomNavigationView bottomNavigationView;

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
                leagueIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(leagueIntent);
            }
        });

        // 바텀네비게이션뷰 이벤트 처리
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab_home:
                        Log.e("bottomNav: ", "홈탭 눌림");
                        Toast.makeText(MainActivity.this, "홈탭 눌림", Toast.LENGTH_SHORT).show();
                        return true; // 탭 변경 허용
                    case R.id.tab_menu:
                        Log.e("bottomNav: ", "메뉴탭 눌림");
                        Toast.makeText(MainActivity.this, "메뉴탭 눌림", Toast.LENGTH_SHORT).show();
                        return true; // 탭 변경 허용
                    case R.id.tab_bookmark:
                        Log.e("bottomNav: ", "즐겨찾기탭 눌림");
                        Toast.makeText(MainActivity.this, "즐겨찾기탭 눌림", Toast.LENGTH_SHORT).show();
                        return true; // 탭 변경 허용
                    case R.id.tab_mypage:
                        Log.e("bottomNav: ", "마이페이지탭 눌림");
                        Toast.makeText(MainActivity.this, "마이페이지탭 눌림", Toast.LENGTH_SHORT).show();
                        return true; // 탭 변경 허용
                }
                return false; // 탭 변경 취소
            }
        });
    } // onCreate()

    @Override
    protected void onResume() {
        super.onResume();
        int index = getSavedTabIndex();
        changeTab(index);
    } // onResume()

    public void changeTab(int index) {
        if (index == 0) {
            bottomNavigationView.setSelectedItemId(R.id.tab_home);
        } else if (index == 1) {
            bottomNavigationView.setSelectedItemId(R.id.tab_menu);
        } else if (index == 2) {
            bottomNavigationView.setSelectedItemId(R.id.tab_bookmark);
        } else if (index == 3) {
            bottomNavigationView.setSelectedItemId(R.id.tab_mypage);
        }
    } // changeTab()

    public int getSavedTabIndex() {
        SharedPreferences pref = getSharedPreferences("myPref", Activity.MODE_PRIVATE);
        int index = pref.getInt("tabIndex", 0);
        return index;
    } // getSavedTabIndex()

} // end class
