package com.hailam32.doanmangxahoi.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hailam32.doanmangxahoi.models.User;


public class UserRespository {
  FirebaseFirestore fs;

  public UserRespository() {
    fs = FirebaseFirestore.getInstance();
  }

  public void getUserById(String id) {
//    System.out.println("dasdsa");
//    fs.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//      @Override
//      public void onComplete(@NonNull Task<QuerySnapshot> task) {
//        if(task.isComplete())  {
//          System.out.println("complete");
//        }
//      }
//    });
    fs.collection("users").document("lBb6JLy6o7XheRn0GDQDjW59yS82").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
          System.out.println(task.getResult().toObject(User.class).toString());
        }
        if (task.isComplete()) {
          System.out.println("compleye");
        }
        if (task.isCanceled()) {
          System.out.println("cancel");
        }
      }
    });
  }
}
