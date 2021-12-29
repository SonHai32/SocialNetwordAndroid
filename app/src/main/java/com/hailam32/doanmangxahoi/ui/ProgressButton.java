package com.hailam32.doanmangxahoi.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hailam32.doanmangxahoi.R;

public class ProgressButton {
  private CardView cardView;
  private ConstraintLayout constraintLayout;
  private ProgressBar progressBar;
  private TextView textView;

  private Animation fadeIn;
  private String name;

  public ProgressButton(Context ct, View v, String textViewText) {
    cardView = (CardView) v.findViewById(R.id.cardViewProgressBtn);
    constraintLayout = (ConstraintLayout) v.findViewById(R.id.constraintProgressBtn);
    progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
    textView = (TextView) v.findViewById(R.id.txtProgressBtn);

    this.name = textViewText;
    textView.setText(textViewText);
    fadeIn = AnimationUtils.loadAnimation(ct, R.anim.fade_in);

  }

  @SuppressLint("SetTextI18n")
  public void buttonActive() {
    progressBar.setVisibility(View.VISIBLE);
    textView.setText("...");
    cardView.setAnimation(this.fadeIn);
    constraintLayout.setAnimation(this.fadeIn);
    progressBar.setAnimation(this.fadeIn);
    textView.setAnimation(this.fadeIn);
  }

  public void buttonFinish() {
    progressBar.setVisibility(View.GONE);
    textView.setText(this.name);
  }

}
