import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.EventLogTags;

import java.util.Objects;

public class NotificationActivity extends Application {
    public static final String channel_ID="channel";

    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }

    static NotificationManager notificationManager ;
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel("channel coupon","Date expiration", NotificationManager.IMPORTANCE_DEFAULT);
    }

    private void createNotificationChannel(String name,String description, int importance) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel(channel_ID,name,importance);
            channel.setDescription(description);
            notificationManager=getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);

        }
    }
}
