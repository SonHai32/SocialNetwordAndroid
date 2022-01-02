package com.hailam32.doanmangxahoi.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.adapter.MessageContentListAdapter;
import com.hailam32.doanmangxahoi.models.Message;
import com.hailam32.doanmangxahoi.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

  private String friendId, curentUserId;
  private ArrayList<Message> messageList = new ArrayList<>();
  private ListView lvMessageContent;
  private MessageContentListAdapter messageContentListAdapter;

  private CircleImageView friendAvatar;
  private TextView friendUsername;
  private TextInputEditText txtInputMessage;
  private ImageButton btnSendMessage;
  private User friend = null;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_message);

    initUi();
    initEvent();
  }

  private void initUi() {
    Bundle bundle = getIntent().getExtras();
    lvMessageContent = (ListView) findViewById(R.id.lvMessageContent);
    friendAvatar = (CircleImageView) findViewById(R.id.friendMessageAvatar);
    friendUsername = (TextView) findViewById(R.id.friendMessageUsername);
    txtInputMessage = (TextInputEditText) findViewById(R.id.txtFriendMessage);
    btnSendMessage = (ImageButton) findViewById(R.id.btnSendMessage);

    if (bundle != null) {
      this.friendId = bundle.getString("friendId");
      messageContentListAdapter = new MessageContentListAdapter(MessageActivity.this, R.layout.message_content_list_custom_listview, messageList);
      lvMessageContent.setAdapter(messageContentListAdapter);
      FirebaseFirestore.getInstance().collection("users").document(friendId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
          User us = documentSnapshot.toObject(User.class);
          friend = us;
          if (us != null) {
            Picasso.get().load(us.getAvatar_url()).into(friendAvatar);
            friendUsername.setText(us.getDisplay_name());
          }
        }
      });
      FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
      curentUserId = us.getUid();
      FirebaseFirestore.getInstance().collection("users")
              .document(us.getUid())
              .collection("messages")
              .document(this.friendId)
              .collection("message_content")
              .orderBy("created_at", Query.Direction.ASCENDING)
              .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                  if (error != null) return;
                  ArrayList<Message> list = new ArrayList<>();
                  for (DocumentChange dc : value.getDocumentChanges()
                  ) {
                    list.add(dc.getDocument().toObject(Message.class));
                  }
                  loadMessageContentList(list);
                }
              });
    } else {
      finish();
    }
  }

  private void initEvent() {
    if (curentUserId != null && friendId != null) {
      btnSendMessage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (!txtInputMessage.getText().equals("")) {
            Message message = new Message();
            message.setTextMessage(txtInputMessage.getText().toString());
            message.setCreated_at(Timestamp.now());
            message.setSendByID(curentUserId);

            FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(curentUserId)
                    .collection("messages")
                    .document(friendId).set(friend)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                          FirebaseFirestore
                                  .getInstance()
                                  .collection("users")
                                  .document(curentUserId)
                                  .collection("messages")
                                  .document(friendId).collection("message_content")
                                  .add(message).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                              if (task.isComplete()) {
                                txtInputMessage.setText("");
                                return;
                              }
                              if (task.isCanceled()) {
                                Toast t = Toast.makeText(MessageActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG);
                                t.show();
                              }
                            }
                          });
                        }
                      }
                    });
          }
        }
      });
    }
  }


  private void loadMessageContentList(ArrayList<Message> list) {
    this.messageList.addAll(list);
    this.messageContentListAdapter.notifyDataSetChanged();
  }
}