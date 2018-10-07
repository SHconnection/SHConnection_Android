package com.example.kolibreath.shconnection.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.kolibreath.shconnection.R;
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
  private BottomNavigationBar mBtmBar;
  private ViewPager mViewPager;
  private List<BottomNavigationItem> mItemList = new ArrayList<>();

    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView();
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
  }

  //todo set it for the new navigation item
  private void initBottomNavigationItem(){

    BottomNavigationItem item1 = new BottomNavigationItem(R.drawable.ic_launcher_background,"通讯录");
    BottomNavigationItem item2 = new BottomNavigationItem(R.drawable.ic_launcher_background,"家校圈");
    BottomNavigationItem item3 = new BottomNavigationItem(R.drawable.ic_launcher_background,"孩子评价");

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
}
