package com.hailam32.doanmangxahoi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.hailam32.doanmangxahoi.R;

import java.util.ArrayList;
import java.util.List;

public class CreatePostActivity extends AppCompatActivity {

  private TextView txtPostCreatePostText;
  private ImageButton btnCloseCreatePost;
  private Chip chipAddHashtag, chipAddPhoto;
  private ChipGroup chipGroupHashtag;
  private TextInputEditText txtInputHashtag;
  private MaterialButton btnAddPost;
  private boolean toggleInputChip = false;
  List<String> listHashtag = new ArrayList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_post);

    this.init();
  }

  private void init() {
    this.initUI();
    this.initEvent();
  }

  private void initUI() {
    this.txtPostCreatePostText = (TextView) findViewById(R.id.txtPostCreatePostText);
    this.btnCloseCreatePost = (ImageButton) findViewById(R.id.btnCloseCreatePost);
    this.chipAddHashtag = (Chip) findViewById(R.id.chipBtnInputHashtag);
    this.chipAddPhoto = (Chip) findViewById(R.id.chipBtnUploadPhoto);
    this.chipGroupHashtag = (ChipGroup) findViewById(R.id.chipGroupCreatePostHashtag);
    this.txtInputHashtag = (TextInputEditText) findViewById(R.id.txtPostCreateInputHashtag);
    this.btnAddPost = (MaterialButton) findViewById(R.id.btnCreatePostPost);
  }

  private void initEvent() {
    this.btnCloseCreatePost.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    this.chipAddHashtag.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        toggleInputChip = !toggleInputChip;
        handleToggleInputChip();
      }
    });

    this.txtInputHashtag.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
          System.out.println("Ok");
        }
        return false;
      }
    });
  }

  private void handleToggleInputChip() {
    if (this.toggleInputChip)
      this.txtInputHashtag.setVisibility(View.VISIBLE);
    else
      this.txtInputHashtag.setVisibility(View.GONE);
  }
}