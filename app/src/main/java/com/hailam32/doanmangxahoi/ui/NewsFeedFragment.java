package com.hailam32.doanmangxahoi.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.hailam32.doanmangxahoi.MainActivity;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.adapter.PostListViewAdapter;
import com.hailam32.doanmangxahoi.models.post.Post;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class NewsFeedFragment extends Fragment {
  private ListView listViewPost;
  private PostListViewAdapter postListViewAdapter;
  private CircleImageView imgPostCreateBoxLabelAvatar;
  private TextView tvOpenCreatePost;
  private ArrayList<Post> postArrayList = new ArrayList<Post>();


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_news_feed, container, false);
    initUI(v);
    initEvent();
    postListListener();
    return v;
  }

  private void initUI(View v) {
    listViewPost = (ListView) v.findViewById(R.id.listViewPost);
    tvOpenCreatePost = (TextView) v.findViewById(R.id.tvOpenCreatePost);

    postListViewAdapter = new PostListViewAdapter(getActivity(), R.layout.post_layout, this.postArrayList);
    listViewPost.setAdapter(postListViewAdapter);
    imgPostCreateBoxLabelAvatar = (CircleImageView) v.findViewById(R.id.imgPostCreateLabelAvatar);


  }

  private void initEvent() {
    tvOpenCreatePost.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent it = new Intent(v.getContext(), CreatePostActivity.class);
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