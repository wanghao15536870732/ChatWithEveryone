package com.example.lab.android.nuc.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.lab.android.nuc.chat.CustomTabView.CustomTabView;
import com.example.lab.android.nuc.chat.Fragment.ChatFragment;
import com.example.lab.android.nuc.chat.Fragment.CommunityFragment;
import com.example.lab.android.nuc.chat.Fragment.PracticeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomTabView.OnTabCheckListener {
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


}
