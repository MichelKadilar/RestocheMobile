import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.restoche.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.NotificationComparable;

public class ListAdaptaterNotification extends ArrayAdapter<NotificationComparable> {
    public ListAdaptaterNotification(Context context, ArrayList<NotificationComparable> notifArrayList) {
        super(context, R.layout.conso_layout, notifArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NotificationComparable notificationComp = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_layout, parent, false);
        }

        TextView notifName = convertView.findViewById(R.id.notificationNameTextView);
        TextView notifDescription = convertView.findViewById(R.id.notificationDateTextView);
        TextView notifDate = convertView.findViewById(R.id.dateTextView);
        ImageView deleteButton = convertView.findViewById(R.id.deleteImageView);

        notifName.setText(notificationComp.getTitle());
        notifDescription.setText(notificationComp.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        String currentDateAndTime = sdf.format(notificationComp.getNotifDate());
        notifDate.setText(currentDateAndTime);

        deleteButton.setOnClickListener(view -> {
            remove(notificationComp);
        });

        return convertView;
    }
}