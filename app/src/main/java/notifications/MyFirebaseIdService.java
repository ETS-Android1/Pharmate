package notifications;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

import models.Token;

public class MyFirebaseIdService extends FirebaseMessagingService {
    private FirebaseFirestore firebaseFirestore;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if (firebaseUser != null) {
            updateToken(refreshToken);
        }
    }
    private void updateToken(String refreshToken) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Token token1 = new Token(refreshToken);
        firebaseFirestore.collection("Tokens").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(token1);

    }
}