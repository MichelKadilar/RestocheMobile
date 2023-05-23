package model;

import android.app.Notification;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationComparable  implements Comparable, Parcelable {
    public Notification notification;
    public String title;
    public String description;
    public Date notifDate;
    //this.title = notification.extras.getCharSequence(Notification.EXTRA_TITLE).toString();
    // this.description = notification.extras.getCharSequence(Notification.EXTRA_TEXT).toString();

    public NotificationComparable(Notification notification, Date notifDate, String title, String description){
        this.notification = notification;
        this.notifDate = notifDate;
        this.title = title;
        this.description = description;
    }

    protected NotificationComparable(Parcel in) {
        notification = in.readParcelable(Notification.class.getClassLoader());
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<NotificationComparable> CREATOR = new Creator<NotificationComparable>() {
        @Override
        public NotificationComparable createFromParcel(Parcel in) {
            return new NotificationComparable(in);
        }

        @Override
        public NotificationComparable[] newArray(int size) {
            return new NotificationComparable[size];
        }
    };

    public Notification getNotification() {
        return notification;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getNotifDate() {
        return notifDate;
    }

    public String getDayNotif() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
        return sdf.format(notifDate);
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof NotificationComparable){
            NotificationComparable other = (NotificationComparable) o;
            if (this.notifDate.after(other.getNotifDate())) {
                return 1;
            } else if (this.notifDate.before(other.getNotifDate())) {
                return -1;
            } else{
                if (this.title.compareTo(other.getTitle()) > 0) {
                    return 1;
                } else if (this.title.compareTo(other.getTitle()) < 0) {
                    return -1;
                } else {
                    if (this.description.compareTo(other.getDescription()) > 0) {
                        return 1;
                    } else if (this.description.compareTo(other.getDescription()) < 0) {
                        return -1;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(notification, i);
        parcel.writeString(title);
        parcel.writeString(description);
    }
}
