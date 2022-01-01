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
import com.hailam32.doanmangxahoi.adapter.FriendSuggestAdapter;
import com.hailam32.doanmangxahoi.models.User;

import java.util.ArrayList;

public class FriendSuggestFragment extends Fragment {

  private ArrayList<User> friendSuggestList = new ArrayList<>();
  private ListView lvFriendSuggest;
  private FriendSuggestAdapter friendSuggestAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v =
            inflater.inflate(R.layout.fragment_friend_suggest, container, false);
    initUi(v);
    friendListener();
    return v;
  }

  public void initUi(View v) {
    lvFriendSuggest = (ListView) v.findViewById(R.id.lvFriendSuggest);

    friendSuggestAdapter = new FriendSuggestAdapter(getActivity(), R.layout.friend_suggest_custom_listview, friendSuggestList);
    lvFriendSuggest.setAdapter(friendSuggestAdapter);
  }

  private void friendListener() {
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    FirebaseAuth fa = FirebaseAuth.getInstance();
    FirebaseUser us = fa.getCurrentUser();


    fs.collection("users").document(us.getUid()).collection("friend_list").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

  private void loadFriendList(ArrayList<User> list) {
    friendSuggestList.addAll(list);
    friendSuggestAdapter.notifyDataSetChanged();
  }
}