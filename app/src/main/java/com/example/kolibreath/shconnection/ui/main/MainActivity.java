package com.example.kolibreath.shconnection.ui.main;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.kolibreath.shconnection.R;
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.anko.appcompat.v7.AppcompatAlertBuilder;

  /**
   * 没有使用系统提供的Toolbar 构建 自定义了layout引入
   */
public class MainActivity extends AppCompatActivity implements
    BottomNavigationBar.OnTabSelectedListener {

  private BottomNavigationBar mBtmBar;
  private ViewPager mViewPager;
  private List<BottomNavigationItem> mItemList = new ArrayList<>();

  private ImageView mBtnProfile;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView();
  }

  private void initView(){
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

    mBtnProfile = findViewById(R.id.btn_profile);
    mBtnProfile.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

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
