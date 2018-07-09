package com.example.lab.android.nuc.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.lab.android.nuc.chat.CustomTabView.CustomTabView;
import com.example.lab.android.nuc.chat.Fragment.ChatFragment;
import com.example.lab.android.nuc.chat.Fragment.CommunityFragment;
import com.example.lab.android.nuc.chat.Fragment.PracticeFragment;
import com.example.lab.android.nuc.chat.Translation.Glossary.MyWordRecycleViewActivity;
import com.example.lab.android.nuc.chat.Translation.activity.HelpActivity;
import com.example.lab.android.nuc.chat.Translation.activity.IDActivity;
import com.example.lab.android.nuc.chat.Translation.activity.MainActivity_11;
import com.example.lab.android.nuc.chat.chat_ui.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.lab.android.nuc.chat.R.menu.toolbar;

public class MainActivity extends AppCompatActivity implements CustomTabView.OnTabCheckListener, NavigationView.OnNavigationItemSelectedListener{




    private CustomTabView mCustomTabView;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragments.add(new PracticeFragment());
        mFragments.add(new ChatFragment());
        mFragments.add(new CommunityFragment());
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initView() {
        mCustomTabView = findViewById(R.id.custom_tab_container);
        CustomTabView.Tab tabHome = new CustomTabView.Tab().setText("Practice")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.drawable.ic_practice)
                .setPressedIcon(R.drawable.ic_practice);
        mCustomTabView.addTab(tabHome);
        CustomTabView.Tab tabDis = new CustomTabView.Tab().setText("Chat")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.drawable.ic_chat)
                .setPressedIcon(R.drawable.ic_chat);
        mCustomTabView.addTab(tabDis);
        CustomTabView.Tab tabAttention = new CustomTabView.Tab().setText("Community")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.drawable.ic_community)
                .setPressedIcon(R.drawable.ic_community);
        mCustomTabView.addTab(tabAttention);

        mCustomTabView.setOnTabCheckListener(this);
        mCustomTabView.setCurrentItem(0);

    }

    @Override
    public void onTabSelected(View v, int position) {
        onTabItemSelected(position);
    }

    private void onTabItemSelected(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mFragments.get(0);
                break;
            case 1:
                fragment = mFragments.get(1);
                break;
            case 2:
                fragment = mFragments.get(2);
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment).show(fragment).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.toolbar ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:

                break;
            case R.id.more:

                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Info) {

        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this, IDActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_word) {
            Intent intent = new Intent(MainActivity.this, MyWordRecycleViewActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer( GravityCompat.START);
        return true;
    }
}
