package com.hailam32.doanmangxahoi.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.adapter.FriendListAdapter;
import com.hailam32.doanmangxahoi.adapter.MessageListAdapter;
import com.hailam32.doanmangxahoi.models.User;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

  private ArrayList<String> messageIdList = new ArrayList<>();
  private ListView lvMessage;
  private MessageListAdapter messageListAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v =
            inflater.inflate(R.layout.fragment_message, container, false);
    initUi(v);
    friendListener();
    return v;
  }

  public void initUi(View v) {
    lvMessage = (ListView) v.findViewById(R.id.lvMessage);
    messageListAdapter = new MessageListAdapter(getActivity(), R.layout.message_custom_listview, messageIdList);
    lvMessage.setAdapter(messageListAdapter);
  }

  private void friendListener() {
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    FirebaseAuth fa = FirebaseAuth.getInstance();
    FirebaseUser us = fa.getCurrentUser();


    fs.collection("users").document(us.getUid()).collection("messages").addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

        if (error != null) {
          return;
        }

        ArrayList<String> list = new ArrayList<>();

        assert value != null;
        for (DocumentChange dc : value.getDocumentChanges()) {
          String id = dc.getDocument().getId();
          if (id != null) {
            list.add(id);
          }
        }
        loadMessage(list);
      }
    });
  }

  private void loadMessage(ArrayList<String> list) {
    messageIdList.addAll(list);
    messageListAdapter.notifyDataSetChanged();
  }
}