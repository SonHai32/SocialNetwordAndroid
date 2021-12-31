package com.hailam32.doanmangxahoi.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.models.User;
import com.hailam32.doanmangxahoi.models.post.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdapter extends ArrayAdapter<User> {
  private Activity context;
  private LayoutInflater li;
  private ArrayList<User> friendList;
  private int resource;

  private CircleImageView avatar;
  private TextView username;
  private ImageButton messageButton;

  public FriendListAdapter(Activity context, int resource, ArrayList<User> friendList) {
    super(context, resource, friendList);
    this.friendList = friendList;
    this.context = context;
    this.resource = resource;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = this.context.getLayoutInflater().inflate(this.resource, null);
    }

    initUi(convertView);
    setUiData((friendList.get(position)));

    return convertView;
  }

  public void initUi(View v) {
    avatar = (CircleImageView) v.findViewById(R.id.friendListItemAvatar);
    username = (TextView) v.findViewById(R.id.friendListItemUsername);
    messageButton = (ImageButton) v.findViewById(R.id.friendListItemMessageBtn);
  }

  private void setUiData(User u) {
    if (u != null) {
      Picasso.get().load(u.getAvatar_url()).into(avatar);
      username.setText(u.getDisplayName());
    }
  }
}

