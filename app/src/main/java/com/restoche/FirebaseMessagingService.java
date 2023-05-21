package com.restoche;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        String myMsg = message.getNotification().getBody();
        Log.d("FirebaseMessage", "Vous venez de recevoir une notification");
    }
}
