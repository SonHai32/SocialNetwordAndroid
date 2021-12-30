package com.hailam32.doanmangxahoi.ui;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.models.post.Post;
import com.hailam32.doanmangxahoi.models.post.PostContent;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreatePostActivity extends AppCompatActivity {

  private final int PICK_IMAGE_STATUS_CODE = 123456;

  private CircleImageView avatar;
  private TextView tvUsername;
  private TextView txtPostCreatePostText;
  private ImageButton btnCloseCreatePost;
  private Chip chipAddHashtag, chipAddPhoto;
  private ChipGroup chipGroupHashtag;
  private TextInputEditText txtInputHashtag;
  private TextInputLayout inputLayoutHashtag;
  private MaterialButton btnAddPost;
  private boolean toggleInputChip = false;
  private FirebaseUser currentUser;
  List<String> listHashtag = new ArrayList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_post);

    this.init();
  }

  private void init() {
    firebaseProcess();
    this.initUi();
    this.initUiData();
    this.initEvent();
  }

  private void toggleUi(boolean state) {

    this.txtPostCreatePostText.setEnabled(state);
    this.inputLayoutHashtag.setEnabled(state);
    this.btnCloseCreatePost.setEnabled(state);
    this.chipAddHashtag.setEnabled(state);
    this.chipAddPhoto.setEnabled(state);
    this.chipGroupHashtag.setEnabled(state);
    this.txtInputHashtag.setEnabled(state);
    this.btnAddPost.setEnabled(state);
    this.avatar.setEnabled(state);
    this.tvUsername.setEnabled(state);
  }


  private void initUi() {
    this.txtPostCreatePostText = (TextView) findViewById(R.id.txtPostCreatePostText);
    this.inputLayoutHashtag = (TextInputLayout) findViewById(R.id.inputLayoutHashTag);
    this.btnCloseCreatePost = (ImageButton) findViewById(R.id.btnCloseCreatePost);
    this.chipAddHashtag = (Chip) findViewById(R.id.chipBtnInputHashtag);
    this.chipAddPhoto = (Chip) findViewById(R.id.chipBtnUploadPhoto);
    this.chipGroupHashtag = (ChipGroup) findViewById(R.id.chipGroupCreatePostHashtag);
    this.txtInputHashtag = (TextInputEditText) findViewById(R.id.txtPostCreateInputHashtag);
    this.btnAddPost = (MaterialButton) findViewById(R.id.btnCreatePostPost);
    this.avatar = (CircleImageView) findViewById(R.id.imgCreatePostAvatar);
    this.tvUsername = (TextView) findViewById(R.id.tvCreatePostUsername);
  }

  private void initUiData() {
    Picasso.get().load(this.currentUser.getPhotoUrl()).into(this.avatar);
    this.tvUsername.setText(this.currentUser.getDisplayName());
  }

  private void firebaseProcess() {
    currentUser = FirebaseAuth.getInstance().getCurrentUser();
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

    this.chipAddPhoto.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openMediaDialog();
      }
    });


    this.chipGroupHashtag.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(ChipGroup group, int checkedId) {
        System.out.println("hehe");
      }
    });
    this.chipAddHashtag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        System.out.println("dasdsa");
      }
    });

    this.txtInputHashtag.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        System.out.println(event);
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
          addNewHashTag(v);
        }
        return false;
      }
    });

    this.btnAddPost.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        uploadPost();
      }
    });
  }

  private void openMediaDialog() {

    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    pickIntent.setType("image/*");
    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    Intent chooserIntent = Intent.createChooser(pickIntent, "Select Image");
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePicture});


    startActivityForResult(chooserIntent, PICK_IMAGE_STATUS_CODE);
  }


  private void handleToggleInputChip() {
    if (this.toggleInputChip)
      this.inputLayoutHashtag.setVisibility(View.VISIBLE);
    else
      this.inputLayoutHashtag.setVisibility(View.GONE);
  }

  private void addNewHashTag(View v) {
    String text = this.txtInputHashtag.getText() + "";
    this.txtInputHashtag.setText("");
    this.txtInputHashtag.clearFocus();
    InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    loadChipGroup(text);
  }

  private void loadChipGroup(String text) {
    this.listHashtag.add(text);
    Chip c = new Chip(this);
    c.setText(text);
    chipGroupHashtag.addView(c);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PICK_IMAGE_STATUS_CODE) {
      ArrayList imagePathList = new ArrayList<>();

      if (data.getClipData() != null) {

        int count = data.getClipData().getItemCount();
        for (int i = 0; i < count; i++) {
          Uri imageUri = data.getClipData().getItemAt(i).getUri();
          System.out.println(imageUri);
//          getImageFilePath(imageUri);
        }
      } else if (data.getData() != null) {
        Uri imgUri = data.getData();
//        getImageFilePath(imgUri);
        System.out.println(imgUri);
      }
      super.onActivityResult(requestCode, resultCode, data);
    } else return;

  }

  private void uploadPost() {
    this.toggleUi(false);
    Post p = new Post();
    PostContent pt = new PostContent();
    if (!this.txtPostCreatePostText.getText().toString().isEmpty()) {
      pt.setText_content(this.txtPostCreatePostText.getText().toString());
    }

    if (!this.listHashtag.isEmpty()) {
      pt.setHashtag(this.listHashtag);
    }

    p.setPost_content(pt);
    p.setCreated_at(Timestamp.now());
    p.setAvatar_url(this.currentUser.getPhotoUrl().toString());
    p.setCreated_by_id(this.currentUser.getUid());
    p.setCreate_by_username(this.currentUser.getDisplayName());


    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    fs.collection("posts").add(p).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
      @Override
      public void onComplete(@NonNull Task<DocumentReference> task) {
        if (task.isComplete()) {
          Log.v("POST", "Complete");
          toggleUi(true);
        } else if (task.isSuccessful()) {
          Log.v("POST", "Success");
          toggleUi(true);
        }
        else {
          toggleUi(true);
          Log.v("POST", "Fail" + task.getException().getMessage());
        }
      }
    });
  }

}