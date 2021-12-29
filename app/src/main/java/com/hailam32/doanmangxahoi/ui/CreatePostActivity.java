package com.hailam32.doanmangxahoi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hailam32.doanmangxahoi.R;

public class CreatePostActivity extends AppCompatActivity {

  private TextView txtPostCreatePostText;
  private ImageButton btnCloseCreatePost;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_post);

    this.init();
  }

  private  void init(){
      this.initUI();
      this.initEvent();
  }

  private void initUI() {
    this.txtPostCreatePostText = (TextView) findViewById(R.id.txtPostCreatePostText);
    this.btnCloseCreatePost = (ImageButton) findViewById(R.id.btnCloseCreatePost);
  }

  private void initEvent() {
    this.btnCloseCreatePost.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
}