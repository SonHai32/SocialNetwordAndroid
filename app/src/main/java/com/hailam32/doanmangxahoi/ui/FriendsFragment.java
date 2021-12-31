package com.hailam32.doanmangxahoi.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.ChipGroup;
import com.hailam32.doanmangxahoi.R;

public class FriendsFragment extends Fragment {

  private LinearLayoutCompat mainFriendContainer;
  private ChipGroup friendFragmentChipGroup;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.fragment_friends, container, false);

    initUi(v);
    initEvent();

    return v;
  }

  private void initUi(View v) {
    mainFriendContainer = v.findViewById(R.id.friendFragmentMainContainer);
    friendFragmentChipGroup = (ChipGroup) v.findViewById(R.id.friendFragmentChipGroup);

    getFragmentManager().beginTransaction().replace(mainFriendContainer.getId(), new FriendListFragment()).commit();
  }

  private void initEvent() {
    friendFragmentChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
      @SuppressLint("NonConstantResourceId")
      @Override
      public void onCheckedChanged(ChipGroup group, int checkedId) {
        switch (checkedId) {
          case R.id.friendFragmentChipFriendList:
            getFragmentManager().beginTransaction().replace(mainFriendContainer.getId(), new FriendListFragment()).commit();
            break;
          case R.id.friendFragmentChipFriendRequest:
            getFragmentManager().beginTransaction().replace(mainFriendContainer.getId(), new FriendRequestFragment()).commit();
            break;
          case R.id.friendFragmentChipFriendSuggest:
            getFragmentManager().beginTransaction().replace(mainFriendContainer.getId(), new FriendSuggestFragment()).commit();
            break;
        }
      }
    });
//    this.friendTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//      @SuppressLint("NonConstantResourceId")
//      @Override
//      public void onTabSelected(TabLayout.Tab tab) {
//        System.out.println(tab.getPosition());
//        switch (tab.getPosition()) {
//          case 0:
//            assert getFragmentManager() != null;
//            break;
//          case 1:
//            assert getFragmentManager() != null;
//            break;
//          case 2:
//            break;
////                  .getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new NewsFeedFragment()).commit();
//        }
//      }
//
//      @Override
//      public void onTabUnselected(TabLayout.Tab tab) {
//
//      }
//
//      @Override
//      public void onTabReselected(TabLayout.Tab tab) {
//
//      }
//    });
  }
}