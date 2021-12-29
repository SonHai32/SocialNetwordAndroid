package com.hailam32.doanmangxahoi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hailam32.doanmangxahoi.adapter.PostListViewAdapter;
import com.hailam32.doanmangxahoi.models.post.Post;
import com.hailam32.doanmangxahoi.models.post.PostContent;
import com.hailam32.doanmangxahoi.ui.CreatePostActivity;
import com.hailam32.doanmangxahoi.ui.LoginActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

  private ListView listViewPost;
  private PostListViewAdapter postListViewAdapter;
  private CircleImageView imgPostCreateBoxLabelAvatar;
  private TextView tvOpenCreatePost;
  ArrayList<Post> postArrayList = new ArrayList<Post>();

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
    postListListener();
  }

  private void navigateToLoginActivity() {
    Intent it = new Intent(MainActivity.this, LoginActivity.class);
    startActivity(it);
    finish();
  }

  private void initUI() {
    listViewPost = (ListView) findViewById(R.id.listViewPost);
    tvOpenCreatePost = (TextView) findViewById(R.id.tvOpenCreatePost);
    postListViewAdapter = new PostListViewAdapter(MainActivity.this, R.layout.post_layout, this.postArrayList);
    listViewPost.setAdapter(postListViewAdapter);
    imgPostCreateBoxLabelAvatar = (CircleImageView) findViewById(R.id.imgPostCreateLabelAvatar);
  }

  private void intEvent(){
    tvOpenCreatePost.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent it = new Intent(MainActivity.this, CreatePostActivity.class);
        startActivity(it);
      }
    });
  }

  private void postListListener() {
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    FirebaseAuth fa = FirebaseAuth.getInstance();
    FirebaseUser us = fa.getCurrentUser();
    if (us != null) {
      Picasso.get().load(us.getPhotoUrl()).into(this.imgPostCreateBoxLabelAvatar);
    }

    fs.collection("posts").orderBy("created_at", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

        if (error != null) {
          return;
        }

        ArrayList<Post> postList = new ArrayList<>();

        assert value != null;
        for (DocumentChange dc : value.getDocumentChanges()) {
          postList.add(dc.getDocument().toObject(Post.class));
        }
        loadPostList(postList);
      }
    });
  }

  private void loadPostList(ArrayList<Post> list) {
    postArrayList.addAll(list);
    postListViewAdapter.notifyDataSetChanged();
  }

}