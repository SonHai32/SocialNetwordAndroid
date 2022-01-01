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
import com.hailam32.doanmangxahoi.adapter.NotificationAdapter;
import com.hailam32.doanmangxahoi.models.Notification;
import com.hailam32.doanmangxahoi.models.User;

import java.util.ArrayList;


public class NotificationFragment extends Fragment {
  private ArrayList<Notification> notificationsList = new ArrayList<>();
  private ListView lvNotification;
  private NotificationAdapter notificationAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v =
            inflater.inflate(R.layout.fragment_notification, container, false);
    initUi(v);
    friendListener();
    return v;
  }

  public void initUi(View v) {
    lvNotification = (ListView) v.findViewById(R.id.lvNotification);

    notificationAdapter = new NotificationAdapter(getActivity(), R.layout.notification_custom_listview, notificationsList);
    lvNotification.setAdapter(notificationAdapter);
  }

  private void friendListener() {
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    FirebaseAuth fa = FirebaseAuth.getInstance();
    FirebaseUser us = fa.getCurrentUser();


    fs.collection("users").document(us.getUid()).collection("notifications").addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

        if (error != null) {
          return;
        }

        ArrayList<Notification> list = new ArrayList<>();

        assert value != null;
        for (DocumentChange dc : value.getDocumentChanges()) {
          Notification notification = dc.getDocument().toObject(Notification.class);
          System.out.println(notification);
          if (notification.getId() != null && notification.getByUser() != null) {
            list.add(notification);
          }
        }
        loadFriendList(list);
      }
    });
  }

  private void loadFriendList(ArrayList<Notification> list) {
    notificationsList.addAll(list);
    notificationAdapter.notifyDataSetChanged();
  }
}