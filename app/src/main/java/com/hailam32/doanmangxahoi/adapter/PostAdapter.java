package com.hailam32.doanmangxahoi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.models.post.Post;
import com.hailam32.doanmangxahoi.models.post.PostContent;
import com.hailam32.doanmangxahoi.utils.DateToRelativeTime;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends BaseAdapter {
  private Activity context;
  private LayoutInflater li;
  private ArrayList<Post> postList;
  private int resource;

  public PostAdapter(Activity context, int resource, ArrayList<Post> postList) {
    this.postList = postList;
    this.context = context;
    this.resource = resource;
  }

  @Override
  public int getCount() {
    if (!postList.isEmpty()) {
      return postList.size();
    }
    return 0;
  }

  @Override
  public Object getItem(int position) {
    return postList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @SuppressLint({"UseCompatLoadingForDrawables", "ViewHolder"})
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View gridView;
    if (convertView == null) {
      gridView = new View(context);
    } else {
      gridView = (View) convertView;
    }
    gridView = inflater.inflate(resource, null);

    CircleImageView imgAvatar = (CircleImageView) gridView.findViewById(R.id.imgAvatar);
    TextView txtUsername = (TextView) gridView.findViewById(R.id.txtPostUsername);
    TextView txtTime = (TextView) gridView.findViewById(R.id.txtPostTime);
    ChipGroup chipGroupPost = (ChipGroup) gridView.findViewById(R.id.chipGroupPostTags);
    TextView txtPostTextContent = (TextView) gridView.findViewById(R.id.txtPostTextContent);
    ImageView imgPostImageContent = (ImageView) gridView.findViewById(R.id.imgPostImageContent);
    Post p = postList.get(position);
    Picasso.get().load(p.getAvatar_url()).into(imgAvatar);
    txtUsername.setText(p.getCreate_by_username());
    txtTime.setText(DateToRelativeTime.getRelativeTime(p.getCreated_at().toDate()));

    PostContent postContent = p.getPost_content();
    List<String> postTags = postContent.getHashtag();
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
        chipGroupPost.addView(c);
      }
    }

    if (postContent.getImage_content() != null && !postContent.getImage_content().isEmpty()) {
      Picasso.get().load(postContent.getImage_content().get(0).getSrc()).into(imgPostImageContent);
    }

    txtPostTextContent.setText(postContent.getText_content());

    return gridView;
  }

}
