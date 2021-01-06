package notifications;


import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.pharmate.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireBaseMessagingService extends FirebaseMessagingService {
    String title, message;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title = remoteMessage.getData().get("Title");
        message = remoteMessage.getData().get("Message");

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_baseline_check_24)
                        .setContentTitle(title)
                        .setContentText(message);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}

//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//    @Override
//    public void onNewToken(@NonNull String s) {
//        super.onNewToken(s);
//        Log.e("newToken", s);
//
//        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        String refreshToken= FirebaseInstanceId.getInstance().getToken();
//        if(firebaseUser!=null){
//            updateToken(refreshToken);
//        }
//
//        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
//
//    }
//
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//        getFirebaseMessage(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//    }
//
//    public void getFirebaseMessage(String title, String body) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myFirebaseChannel")
//                .setSmallIcon(R.drawable.ic_baseline_check_24)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setAutoCancel(true);
//
//        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
//        manager.notify(101, builder.build());
//    }
//
//    public static String getToken(Context context) {
//        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
//    }
//    private void updateToken(String refreshToken){
//        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        Token token1= new Token(refreshToken);
//        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
//    }
//}
