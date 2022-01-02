package com.hailam32.doanmangxahoi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.enums.NotificationEnum;
import com.hailam32.doanmangxahoi.models.Notification;
import com.hailam32.doanmangxahoi.utils.DateToRelativeTime;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends ArrayAdapter<Notification> {
  private Activity context;
  private ArrayList<Notification> notificationList;
  private int resource;
  private int positionSelected;

  public NotificationAdapter(Activity context, int resource, ArrayList<Notification> notification) {
    super(context, resource, notification);
    this.notificationList = notification;
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

    CircleImageView avatar = (CircleImageView) gridView.findViewById(R.id.notificationItemAvatar);
    TextView username = (TextView) gridView.findViewById(R.id.notificationItemUsername);
    TextView title = (TextView) gridView.findViewById(R.id.notificationItemTitle);
    TextView time = (TextView) gridView.findViewById(R.id.notificationItemTime);
    ImageView status = (ImageView) gridView.findViewById(R.id.notificationItemStatus);
    LinearLayoutCompat notificationItemMainContainer = (LinearLayoutCompat) gridView.findViewById(R.id.notificationItemMainContainer);
    Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

    Notification notification;

    if (this.notificationList != null && !this.notificationList.isEmpty()) {

      notification = this.notificationList.get(position);
      Picasso.get().load(notification.getByUser().getAvatar_url()).into(avatar);
      username.setText(notification.getByUser().getDisplay_name());
      time.setText(DateToRelativeTime.getRelativeTime(notification.getCreated_at().toDate()));
      if (notification.getType() == NotificationEnum.message)
        title.setText(gridView.getResources().getText(R.string.SendFriendRequest));
      else
        title.setText(gridView.getResources().getText(R.string.SendYouMessage));
      if (notification.isSeen())
        status.setVisibility(View.GONE);


      notificationItemMainContainer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          positionSelected = position;
          notifyDataSetChanged();
        }
      });

      if (positionSelected == position) {
        notificationItemMainContainer.setAnimation(fadeIn);
        notificationItemMainContainer.setBackground(gridView.getResources().getDrawable(R.drawable.rounded_hover));
      } else {
        notificationItemMainContainer.setBackground(null);
      }
    }

    return gridView;
  }

}

