package java.com.techsanotes.Utility;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;

public class Utility {

    public static CollectionReference getCollectionReference(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
       return FirebaseFirestore.getInstance().collection("notes")
                .document(currentUser.getUid()).collection("my_notes");

    }
}
