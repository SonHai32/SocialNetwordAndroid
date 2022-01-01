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
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.models.User;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendSuggestAdapter extends ArrayAdapter<User> {
  private Activity context;
  private ArrayList<User> friendSuggest;
  private int resource;
  private int positionSelected;

  public FriendSuggestAdapter(Activity context, int resource, ArrayList<User> friendSuggest) {
    super(context, resource, friendSuggest);
    this.friendSuggest = friendSuggest;
    this.context = context;
    this.resource = resource;
  }

  @SuppressLint({"ResourceAsColor", "ResourceType", "UseCompatLoadingForDrawables"})
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    if (convertView == null) {
      convertView = this.context.getLayoutInflater().inflate(this.resource, null);
    }

    CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.friendSuggestItemAvatar);
    TextView username = (TextView) convertView.findViewById(R.id.friendSuggestItemUsername);
    ImageButton addFriendBtn = (ImageButton) convertView.findViewById(R.id.friendSuggestItemMessageBtn);
    LinearLayoutCompat friendSuggestItemMainContainer = (LinearLayoutCompat) convertView.findViewById(R.id.friendSuggestItemMainContainer);
    Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

    User user;

    if (this.friendSuggest != null && !this.friendSuggest.isEmpty()) {

      user = this.friendSuggest.get(position);
      Picasso.get().load(user.getAvatar_url()).into(avatar);
      username.setText(user.getDisplay_name());

      friendSuggestItemMainContainer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          positionSelected = position;
          notifyDataSetChanged();
        }
      });

      if (positionSelected == position) {
        friendSuggestItemMainContainer.setAnimation(fadeIn);
        friendSuggestItemMainContainer.setBackground(convertView.getResources().getDrawable(R.drawable.rounded_hover));
      } else {
        friendSuggestItemMainContainer.setBackground(null);
      }

    }

    return convertView;
  }

}

