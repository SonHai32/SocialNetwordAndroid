package com.hailam32.doanmangxahoi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.button.MaterialButton;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendRequestAdapter extends ArrayAdapter<User> {
  private Activity context;
  private ArrayList<User> friendList;
  private int resource;
  private int positionSelected;

  public FriendRequestAdapter(Activity context, int resource, ArrayList<User> friendList) {
    super(context, resource, friendList);
    this.friendList = friendList;
    this.context = context;
    this.resource = resource;
  }

  @SuppressLint({"ResourceAsColor", "ResourceType", "UseCompatLoadingForDrawables"})
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    if (convertView == null) {
      convertView = this.context.getLayoutInflater().inflate(this.resource, null);
    }

    CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.friendRequestItemAvatar);
    TextView username = (TextView) convertView.findViewById(R.id.friendRequestItemUsername);
    LinearLayoutCompat friendListItemMainContainer = (LinearLayoutCompat) convertView.findViewById(R.id.friendRequestItemMainContainer);
    MaterialButton btnAccept = (MaterialButton) convertView.findViewById(R.id.friendRequestItemAcceptBtn);
    MaterialButton btnIgnore = convertView.findViewById(R.id.friendRequestItemIgnoreBtn);
    Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

    User user;

    if (this.friendList != null && !this.friendList.isEmpty()) {

      user = this.friendList.get(position);
      Picasso.get().load(user.getAvatar_url()).into(avatar);
      username.setText(user.getDisplay_name());

      friendListItemMainContainer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          positionSelected = position;
          notifyDataSetChanged();
        }
      });

      if (positionSelected == position) {
        friendListItemMainContainer.setAnimation(fadeIn);
        friendListItemMainContainer.setBackground(convertView.getResources().getDrawable(R.drawable.rounded_hover));
      } else {
        friendListItemMainContainer.setBackground(null);
      }

    }

    return convertView;
  }

}
