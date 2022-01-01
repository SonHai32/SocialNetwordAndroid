package com.hailam32.doanmangxahoi.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hailam32.doanmangxahoi.R;
import com.hailam32.doanmangxahoi.adapter.FriendRequestAdapter;
import com.hailam32.doanmangxahoi.models.User;

import java.util.ArrayList;

public class FriendRequestFragment extends Fragment {

  private ArrayList<User> listFriendRequest = new ArrayList<>();
  private FriendRequestAdapter friendRequestAdapter;
  private ListView lvFriendRequest;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_friend_request, container, false);
    initUi(v);
    friendRequestListener();
    return v;
  }

  private void initUi(View v) {
    lvFriendRequest = (ListView) v.findViewById(R.id.lvFriendRequest);
    friendRequestAdapter = new FriendRequestAdapter(getActivity(), R.layout.friend_request_custom_listview, listFriendRequest);

    lvFriendRequest.setAdapter(friendRequestAdapter);
  }

  private void friendRequestListener() {
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    if (user != null) {
      fs.collection("users").document(user.getUid()).collection("friend_list").addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

          if (error != null) {
            return;
          }

          ArrayList<User> list = new ArrayList<>();

          assert value != null;
          for (DocumentChange dc : value.getDocumentChanges()) {
            User u = dc.getDocument().toObject(User.class);
            System.out.println(u);
            if (u != null) {
              list.add(u);
            }
          }
          loadFriendList(list);
        }
      });
    }
  }

  private void loadFriendList(ArrayList<User> list) {
    listFriendRequest.addAll(list);
    friendRequestAdapter.notifyDataSetChanged();
  }
}