package com.hailam32.doanmangxahoi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.models.Message;
import com.hailam32.doanmangxahoi.models.User;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageContentListAdapter extends ArrayAdapter<Message> {
  private Activity context;
  private ArrayList<Message> messageContentList;
  private int resource;
  private int positionSelected;

  public MessageContentListAdapter(Activity context, int resource, ArrayList<Message> messageContentList) {
    super(context, resource, messageContentList);
    this.messageContentList = messageContentList;
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

    CircleImageView avatar = (CircleImageView) gridView.findViewById(R.id.messageContentFriendMessageAvatar);
    FlexboxLayout friendMessageContainer = (FlexboxLayout) gridView.findViewById(R.id.flexboxFriendMessageTextContent);
    FlexboxLayout selfMessageContainer = (FlexboxLayout) gridView.findViewById(R.id.flexboxSelfMessageTextContent);
    TextView txtFriendMessageTextContent = (TextView) gridView.findViewById(R.id.txtFriendMessageTextContent);
    TextView txtSelfMessageTextContent = (TextView) gridView.findViewById(R.id.txtSelfMessageTextContent);

    selfMessageContainer.setVisibility(View.GONE);
    friendMessageContainer.setVisibility(View.GONE);


    if (this.messageContentList != null && !this.messageContentList.isEmpty()) {

      FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
      Message message = this.messageContentList.get(position);

      if (currentUser != null) {
        if (!message.getTextMessage().isEmpty()) {
          if (message.getSendByID().equals(currentUser.getUid())) {
            friendMessageContainer.setVisibility(View.GONE);
            selfMessageContainer.setVisibility(View.VISIBLE);
            txtSelfMessageTextContent.setText(message.getTextMessage());
          } else {
            friendMessageContainer.setVisibility(View.VISIBLE);
            selfMessageContainer.setVisibility(View.GONE);
            txtFriendMessageTextContent.setText(message.getTextMessage());

            FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(message.getSendByID())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
              @Override
              public void onSuccess(DocumentSnapshot documentSnapshot) {
                Picasso.get().load(documentSnapshot.toObject(User.class).getAvatar_url()).into(avatar);
              }
            });
          }
        }
      }

    }

    return gridView;
  }

}
