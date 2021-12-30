package com.hailam32.doanmangxahoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.hailam32.doanmangxahoi.ui.FriendsFragment;
import com.hailam32.doanmangxahoi.ui.LoginActivity;
import com.hailam32.doanmangxahoi.ui.MenuFragment;
import com.hailam32.doanmangxahoi.ui.MessageFragment;
import com.hailam32.doanmangxahoi.ui.NewsFeedFragment;
import com.hailam32.doanmangxahoi.ui.NotificationFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

  private ChipNavigationBar bottomNavigation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FirebaseAuth fa = FirebaseAuth.getInstance();
    fa.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() != null) {
          initActivity();
        } else {
          navigateToLoginActivity();
        }
      }
    });
  }

  private void initActivity() {
    setContentView(R.layout.activity_main);
    initUI();
    intEvent();
  }

  private void navigateToLoginActivity() {
    Intent it = new Intent(MainActivity.this, LoginActivity.class);
    startActivity(it);
    finish();
  }

  private void initUI() {
    bottomNavigation = (ChipNavigationBar) findViewById(R.id.bottomNavigation);
    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new NewsFeedFragment()).commit();
    bottomNavigation.setItemSelected(R.id.fragmentNewFeesNavItem, true);
  }

  private void intEvent() {
    bottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
      @SuppressLint("NonConstantResourceId")
      @Override
      public void onItemSelected(int i) {
        Fragment f = null;
        switch (i) {
          case R.id.fragmentNewFeesNavItem:
            f = new NewsFeedFragment();
            break;
          case R.id.fragmentMessageNavItem:
            f = new MessageFragment();
            break;
          case R.id.fragmentFriendsNavItem:
            f = new FriendsFragment();
            break;
          case R.id.fragmentNotifications:
            f = new NotificationFragment();
            break;
          case R.id.fragmentMenuNavItem:
            f = new MenuFragment();
            break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, f).commit();
      }
    });


  }
}