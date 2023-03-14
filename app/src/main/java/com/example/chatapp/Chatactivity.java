package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Chatactivity extends AppCompatActivity {



    TabLayout tableLayout;
    TabItem mchat,mstatus,mcall;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

 Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);

tableLayout=findViewById(R.id.chattablayout);
mchat=findViewById(R.id.tabchat);
mstatus=findViewById(R.id.tabstatus);
mcall=findViewById(R.id.tabcall);

toolbar=findViewById(R.id.toolbar);
viewPager=findViewById(R.id.viewpager);


// three dot

        Drawable drawable= ContextCompat.getDrawable(getApplicationContext(),R.drawable.threedot);
        toolbar.setOverflowIcon(drawable);
toolbar.inflateMenu(R.menu.menu);





toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){

            case R.id.profile:

                startActivity(new Intent(Chatactivity.this,Showprofile.class));

                break;
            case R.id.setting:
                Toast.makeText(getApplicationContext(), "no settings", Toast.LENGTH_SHORT).show();

                break;

        }
        return  true;
    }
});




pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tableLayout.getTabCount());
viewPager.setAdapter(pagerAdapter);


tableLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        if(tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2){

           pagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
});

viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tableLayout));


    }



}