package com.hailam32.doanmangxahoi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hailam32.doanmangxahoi.MainActivity;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.repository.UserRespository;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

  private View progressBtnView;
  private TextInputEditText txtEmail;
  private TextInputEditText txtPassword;
  private MaterialCheckBox checkBoxRemember;
  private TextView tvRegister;
  ProgressButton loginProgressButton;
  private FirebaseAuth fAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    this.init();

  }

  private void init() {

    this.txtEmail = (TextInputEditText) findViewById(R.id.txtLoginEmail);
    this.txtPassword = (TextInputEditText) findViewById(R.id.txtLoginPassword);
    this.checkBoxRemember = (MaterialCheckBox) findViewById(R.id.checkBoxLogin);
    this.tvRegister = (TextView) findViewById(R.id.tvRegister);
    this.progressBtnView = (View) findViewById(R.id.btnLoginProgress);
    loginProgressButton = new ProgressButton(LoginActivity.this, progressBtnView, "Login");

    this.tvRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(it);
      }
    });

    this.progressBtnView.setOnClickListener(new View.OnClickListener() {


      @Override
      public void onClick(View v) {
        System.out.println(TextUtils.isEmpty(txtEmail.getError()));
        System.out.println(TextUtils.isEmpty(txtPassword.getError()));
        if (checkEmailValid() && checkPasswordValid()) {
          try {
            handleSignIn();
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        } else {
          Toast t = Toast.makeText(LoginActivity.this, "Invalid input", Toast.LENGTH_LONG);
          t.show();
        }

      }
    });
  }

  private void handleSignIn() {

    loginProgressButton.buttonActive();
    toggleUI(false);

    fAuth = FirebaseAuth.getInstance();
    fAuth.signInWithEmailAndPassword(this.txtEmail.getText() + "", this.txtPassword.getText() + "").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isCanceled()) {
          System.out.println("Cancel");
          toggleUI(true);
          loginProgressButton.buttonFinish();
        }
        if (task.isComplete()) {
          try {

            UserRespository userRespository = new UserRespository();
            userRespository.getUserById("ElNlfI9X95UqJUgFaK4yIHrzc7E3");
            FirebaseUser user = fAuth.getCurrentUser();
            if (user != null) {
              Intent t = new Intent(LoginActivity.this, MainActivity.class);
              startActivity(t);
              finish();
            } else {
              toggleUI(true);
              loginProgressButton.buttonFinish();
              Toast t = Toast.makeText(LoginActivity.this, "Email or password incorect !", Toast.LENGTH_LONG);
              t.show();
            }
          } catch (Exception ex) {
            System.out.println(ex.getMessage());
          }

        }
      }
    });
  }

  private boolean checkPasswordValid() {
    if (TextUtils.isEmpty(this.txtPassword.getText())) {
      this.txtPassword.setError("Please input your password");
      return false;
    } else if (Objects.requireNonNull(this.txtPassword.getText()).length() < 6 || this.txtPassword.getText().length() > 32) {
      this.txtPassword.setError("Password must be 6 - 32 character");
      return false;
    }

    return true;
  }

  private boolean checkEmailValid() {
    if (TextUtils.isEmpty(this.txtEmail.getText()) || !Patterns.EMAIL_ADDRESS.matcher(Objects.requireNonNull(this.txtEmail.getText())).matches()) {
      this.txtEmail.setError("Email is invalid");
      return false;
    }
    return true;
  }

  private void toggleUI(boolean enable) {
    this.txtEmail.setEnabled(enable);
    this.txtPassword.setEnabled(enable);
    this.checkBoxRemember.setEnabled(enable);
    this.tvRegister.setEnabled(enable);
  }

  private void validateForm() {
    this.txtEmail.setError("Email invalid");
  }
}