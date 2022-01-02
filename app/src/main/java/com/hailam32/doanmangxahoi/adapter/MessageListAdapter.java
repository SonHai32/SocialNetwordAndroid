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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.models.Message;
import com.hailam32.doanmangxahoi.models.User;
import com.hailam32.doanmangxahoi.ui.MessageActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageListAdapter extends ArrayAdapter<String> {
  private Activity context;
  private ArrayList<String> listMessageId;
  private int resource;
  private int positionSelected;

  public MessageListAdapter(Activity context, int resource, ArrayList<String> listId) {
    super(context, resource, listId);
    this.listMessageId = listId;
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

    CircleImageView avatar = (CircleImageView) gridView.findViewById(R.id.messageListItemAvatar);
    TextView username = (TextView) gridView.findViewById(R.id.messageListItemUsername);
    TextView lastMessage = (TextView) gridView.findViewById(R.id.messageListItemLastMessage);
    LinearLayoutCompat messageListItemMainContainer = (LinearLayoutCompat) gridView.findViewById(R.id.messageListItemMainContainer);
    Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

    String id = null;
    if (listMessageId != null && !listMessageId.isEmpty()) {
      id = listMessageId.get(position);
    }

    if (id != null) {
      FirebaseFirestore.getInstance().collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
          if (task.isComplete()) {
            User us = task.getResult().toObject(User.class);
            Picasso.get().load(us.getAvatar_url()).into(avatar);
            username.setText(us.getDisplay_name());

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(currentUser.getUid())
                    .collection("messages")
                    .document(us.getId()).collection("message_content")
                    .orderBy("created_at", Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                      @SuppressLint("SetTextI18n")
                      @Override
                      public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Message message = queryDocumentSnapshots.getDocuments().get(0).toObject(Message.class);
                        if (message != null) {
                          if (!message.getSendByID().equals(currentUser.getUid())) {
                            if (message.getTextMessage() != null && !message.getTextMessage().equals("")) {
                              String s = message.getTextMessage();
                              if (s.length() > 8)
                                s = s.substring(0, 8) + "...";
                              lastMessage.setText(s);
                            } else {
                              lastMessage.setText(context.getResources().getString(R.string.SentAnImage));
                            }
                          } else {
                            if (message.getTextMessage() != null && !message.getTextMessage().equals("")) {
                              String s = message.getTextMessage();
                              if (s.length() > 8)
                                s = s.substring(0, 8) + "...";
                              lastMessage.setText(context.getResources().getString(R.string.Me) + s);
                            } else {
                              lastMessage.setText(context.getResources().getString(R.string.Me) + context.getResources().getString(R.string.SentAnImage));
                            }
                          }
                        }
                      }
                    });
          }
        }
      });

      String finalId = id;
      messageListItemMainContainer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          positionSelected = position;
          notifyDataSetChanged();
          Intent it = new Intent(getContext(), MessageActivity.class);
          it.putExtra("friendId", finalId);
          getContext().startActivity(it);

        }
      });
    }

    if (positionSelected == position) {
      messageListItemMainContainer.setAnimation(fadeIn);
      messageListItemMainContainer.setBackground(gridView.getResources().getDrawable(R.drawable.rounded_hover));

    } else {
      messageListItemMainContainer.setBackground(null);
    }

    return gridView;
  }

}
