package com.example.mediastackapp.Activities;

import android.os.Bundle;

import com.example.mediastackapp.R;
import com.example.mediastackapp.Adapters.ViewPagerFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
public class MainActivity extends AppCompatActivity {

    private ViewPagerFragmentAdapter fragmentAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private String[] titles=new String[] {"Articles","Favorites"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tab_layout);
        fragmentAdapter=new ViewPagerFragmentAdapter(this);

        viewPager2.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();
    }
}