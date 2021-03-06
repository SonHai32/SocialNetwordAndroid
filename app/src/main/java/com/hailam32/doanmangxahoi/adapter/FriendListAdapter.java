package com.hailam32.doanmangxahoi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.models.User;
import com.hailam32.doanmangxahoi.ui.MessageActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdapter extends ArrayAdapter<User> {
  private Activity context;
  private ArrayList<User> friendList;
  private int resource;
  private int positionSelected;

  public FriendListAdapter(Activity context, int resource, ArrayList<User> friendList) {
    super(context, resource, friendList);
    this.friendList = friendList;
    this.context = context;
    this.resource = resource;
  }

  @SuppressLint({"ResourceAsColor", "ResourceType", "UseCompatLoadingForDrawables", "ViewHolder"})
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

    CircleImageView avatar = (CircleImageView) gridView.findViewById(R.id.friendListItemAvatar);
    TextView username = (TextView) gridView.findViewById(R.id.friendListItemUsername);
    ImageButton messageButton = (ImageButton) gridView.findViewById(R.id.friendListItemMessageBtn);
    LinearLayoutCompat friendListItemMainContainer = (LinearLayoutCompat) gridView.findViewById(R.id.friendListItemMainContainer);
    Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

    User user = null;

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
        friendListItemMainContainer.setBackground(gridView.getResources().getDrawable(R.drawable.rounded_hover));
      } else {
        friendListItemMainContainer.setBackground(null);
      }

    }

    User finalUser = user;
    messageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent it = new Intent(getContext(), MessageActivity.class);
        it.putExtra("friendId", finalUser.getId());
        getContext().startActivity(it);
      }
    });

    return gridView;
  }

}

