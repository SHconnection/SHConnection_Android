package com.example.kolibreath.shconnection.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
<<<<<<< HEAD
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

=======
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
>>>>>>> kolibreath
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.kolibreath.shconnection.R;
<<<<<<< HEAD
import com.example.kolibreath.shconnection.adapter.FragmentAdapter;
import com.example.kolibreath.shconnection.ui.main.fragment.AddressFragment;
import com.example.kolibreath.shconnection.ui.main.fragment.CommentFragment;
import com.example.kolibreath.shconnection.ui.main.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
    BottomNavigationBar.OnTabSelectedListener {

  private static int index;
=======
import com.example.kolibreath.shconnection.ui.main.news.ViewPictureActivity;
import com.example.kolibreath.shconnection.ui.main.profile.UserProfileActivity;
import java.util.ArrayList;
import java.util.List;

  /**
   * 没有使用系统提供的Toolbar 构建 自定义了layout引入
   */
public class MainActivity extends AppCompatActivity implements
    BottomNavigationBar.OnTabSelectedListener {

  private FloatingActionButton mFab;
>>>>>>> kolibreath
  private BottomNavigationBar mBtmBar;
  private ViewPager mViewPager;
  private FragmentAdapter mAdapter;
  private List<BottomNavigationItem> mItemList = new ArrayList<>();
  private List<Fragment> mFragments = new ArrayList<>();

    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView();
    initFragment();
  }

  public static void start(Context context){
    context.startActivity(new Intent(context,MainActivity.class));
  }

  private void initView(){

     mFab   = findViewById(R.id.fab);
    mBtmBar = findViewById(R.id.bottom_navigation_bar);
    mViewPager = findViewById(R.id.viewpager);

    mBtmBar.setTabSelectedListener(this);

    initBottomNavigationItem();

    mBtmBar.setMode(BottomNavigationBar.MODE_FIXED);
    mBtmBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
    //todo to change the main color of it
    mBtmBar.setBackgroundColor(Color.BLACK);
    mBtmBar.addItem(mItemList.get(0)).addItem(mItemList.get(1)).addItem(mItemList.get(2))
        .setFirstSelectedPosition(1)
        .initialise();
<<<<<<< HEAD
//    mBtmBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
//      @Override
//      public void onTabSelected(int position) {
//        switch (position){
//          case 0:
//            index = 0;
//            replaceFragment(MainActivity.this,mFragments.get(index),R.id.viewpager);
//            break;
//          case 1:
//            index = 1;
//            replaceFragment(MainActivity.this,mFragments.get(index),R.id.viewpager);
//            break;
//          case 2:
//            index = 2;
//            replaceFragment(MainActivity.this,mFragments.get(index),R.id.viewpager);
//
//        }
//      }
//
//      @Override
//      public void onTabUnselected(int position) {
//
//      }
//
//      @Override
//      public void onTabReselected(int position) {
//
//      }
//    });
=======

    ImageView mBtnProfile = findViewById(R.id.btn_profile);
    mBtnProfile.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        UserProfileActivity.Companion.start(MainActivity.this);
      }
    });

    mFab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        ViewPictureActivity.Companion.start(MainActivity.this);
      }
    });
>>>>>>> kolibreath
  }

  //todo set it for the new navigation item
  private void initBottomNavigationItem(){

    BottomNavigationItem item1 = new BottomNavigationItem(R.drawable.ic_address,"通讯录");
    BottomNavigationItem item2 = new BottomNavigationItem(R.drawable.ic_home,"家校圈");
    BottomNavigationItem item3 = new BottomNavigationItem(R.drawable.ic_comment,"孩子评价");

    mItemList.add(item1);
    mItemList.add(item2);
    mItemList.add(item3);
  }

  private void setBottomBadge(int index, String text){
    TextBadgeItem item = new TextBadgeItem().setBackgroundColor(Color.RED)
        .setText(text);
    mItemList.get(index).setBadgeItem(item);
  }

  @Override public void onTabSelected(int position) {

  }

  @Override public void onTabUnselected(int position) {

  }

  @Override public void onTabReselected(int position) {

  }

  private void initFragment(){
      AddressFragment addressFragment = new AddressFragment();
      HomeFragment homeFragment = new HomeFragment();
      CommentFragment commentFragment = new CommentFragment();
      FragmentManager manager = getSupportFragmentManager();
      mAdapter = new FragmentAdapter(manager);
      mFragments.add(addressFragment);
      mFragments.add(homeFragment);
      mFragments.add(commentFragment);
      mAdapter.addFragment(mFragments);
      mViewPager.setAdapter(mAdapter);
  }
}
