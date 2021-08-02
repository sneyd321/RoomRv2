package com.sneydr.roomrv2.Services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.sneydr.roomrv2.App.NotificationHelper;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Services.State.FailureState;
import com.sneydr.roomrv2.Services.State.NotificationContext;
import com.sneydr.roomrv2.Services.State.NotificationState;
import com.sneydr.roomrv2.Services.State.PendingState;
import com.sneydr.roomrv2.Services.State.SuccessState;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class NotificationEventListener implements EventListener<DocumentSnapshot> {

    private NotificationContext notificationContext;
    private Context context;
    private NotificationHelper notificationHelper;


    public NotificationEventListener(Context context) {
        this.notificationContext = new NotificationContext();
        this.context = context;
        notificationHelper = new NotificationHelper(this.context);
    }


    @Override
    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
        if (error != null) return;
        if (value == null || !value.exists()) return;

        Map<String, Object> document = value.getData();
        if (document == null) return;

        String state = getStateFromDocument(document);
        if (state.equals("COMPLETE")) return;

        switch (state) {
            case "PENDING":
                notificationContext.setState(new PendingState());
                break;
            case "SUCCESS":
                notificationContext.setState(new SuccessState());
                break;
            case "FAILURE":
                notificationContext.setState(new FailureState());
                break;
        }
        NotificationState notificationState = notificationContext.getState();
        notificationState.buildNotification(notificationHelper, document);
    }


    protected String getStateFromDocument(Map<String, Object> document) {
        String state = (String) document.get("state");
        if (state == null || state.isEmpty())
            return "";
        return state;
    }
}
