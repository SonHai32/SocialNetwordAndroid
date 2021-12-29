package com.hailam32.doanmangxahoi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hailam32.doanmangxahoi.R;

public class RegisterActivity extends AppCompatActivity {

  View viewBtnRegisterProgress;
  ProgressButton registerProgress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    this.init();
  }

  private void init() {
    this.viewBtnRegisterProgress = (View) findViewById(R.id.btnRegisterProgress);
    this.registerProgress = new ProgressButton(RegisterActivity.this, viewBtnRegisterProgress, "Register");

    this.viewBtnRegisterProgress.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        registerProgress.buttonActive();
      }
    });
  }

  private  void toggleUI(boolean disable){

  }

}