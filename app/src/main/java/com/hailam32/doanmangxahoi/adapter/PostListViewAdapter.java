package com.hailam32.doanmangxahoi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.common.collect.ArrayListMultimap;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.models.post.Post;
import com.hailam32.doanmangxahoi.models.post.PostContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostListViewAdapter extends ArrayAdapter<Post> {
  private Activity context;
  private LayoutInflater li;
  private ArrayList<Post> postList;
  private int resource;

  private CircleImageView imgAvatar;
  private TextView txtUsername;
  private TextView txtTime;
  private ChipGroup chipGroupPost;
  private Chip chipPost;
  private TextView txtPostTextContent;
  private ImageView imgPostImageContent;
  private LinearLayoutCompat linearPostChipGroup;


  public PostListViewAdapter(Activity context, int resource, ArrayList<Post> postList) {
    super(context, resource, postList);
    this.postList = postList;
    this.context = context;
    this.resource = resource;
    System.out.println(postList);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = this.context.getLayoutInflater().inflate(this.resource, null);
    }

    initUI(convertView);
    setUIData((postList.get(position)));

    return convertView;
  }

  private void initUI(View v) {
    this.imgAvatar = (CircleImageView) v.findViewById(R.id.imgAvatar);
    this.txtUsername = (TextView) v.findViewById(R.id.txtPostUsername);
    this.txtTime = (TextView) v.findViewById(R.id.txtPostTime);
    this.chipGroupPost = (ChipGroup) v.findViewById(R.id.chipGroupPostTags);
    this.txtPostTextContent = (TextView) v.findViewById(R.id.txtPostTextContent);
    this.imgPostImageContent = (ImageView) v.findViewById(R.id.imgPostImageContent);
    this.linearPostChipGroup = (LinearLayoutCompat) v.findViewById(R.id.linearPostChipGroup);
  }

  @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
  private void setUIData(Post p) {
    Picasso.get().load(p.getAvatar_url()).into(this.imgAvatar);
    this.txtUsername.setText(p.getCreate_by_username());
    this.txtTime.setText(
            DateUtils.getRelativeTimeSpanString(p.getCreated_at().toDate().getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE));
    PostContent postContent = p.getPost_content();
    List<String> postTags = postContent.getHashtag();
    ArrayList<View> listTag = new ArrayList<>();
    System.out.println(chipGroupPost);
    if (postTags != null && !postTags.isEmpty()) {
      for (String postTag : postTags) {
        Chip c = new Chip(this.context);
        c.setId(ViewCompat.generateViewId());
        c.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        c.setCheckable(false);
        c.setCloseIconEnabled(false);
        c.setChipBackgroundColor(ColorStateList.valueOf(this.context.getResources().getColor(R.color.colorTag)));
        c.setTextColor(ColorStateList.valueOf(this.context.getResources().getColor(R.color.white)));
        c.setChipIcon(this.context.getResources().getDrawable(R.drawable.ic_label_outline_white));
        c.setText(postTag);
        this.chipGroupPost.removeAllViews();
        this.chipGroupPost.addView(c);
        //issue with chip: fix after
      }
    }
    else{
      chipGroupPost.setVisibility(View.GONE);
    }
    System.out.println(chipGroupPost.toString() + chipGroupPost.getId() + "");
//    chipPost.setVisibility(View.GONE);
    if (postContent != null) {
      if (postContent.getImage_content() != null && !postContent.getImage_content().isEmpty()) {
        Picasso.get().load(postContent.getImage_content().get(0).getSrc()).into(this.imgPostImageContent);
      }
      if (postContent.getText_content() != null) {
        this.txtPostTextContent.setText(postContent.getText_content());
      }
    }
//    Picasso.get(p.)
  }


}
