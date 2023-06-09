package com.restoche;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AfterLogin extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    ContactAdapter contactAdapter;
    ArrayList<NotificationModel> notificationModels;
    private RecyclerView contactRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Notifications");

        contactRecycler = findViewById(R.id.recyclerView);
        contactRecycler.setHasFixedSize(true);
        contactRecycler.setLayoutManager(new LinearLayoutManager(AfterLogin.this));

        notificationModels = new ArrayList<>();

        contactAdapter = new ContactAdapter(AfterLogin.this, AfterLogin.this, notificationModels);
        contactRecycler.setAdapter(contactAdapter);
        getNotif(firebaseAuth.getCurrentUser().getUid().toString());
    }
    public void getNotif(String userId)
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationModels.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    NotificationModel model = new NotificationModel();
                    model.setId(dataSnapshot.getKey());
                    model.setTitle(dataSnapshot.child("Notifications").getValue().toString());
                    model.setMessage(dataSnapshot.child("NotificationsMessage").getValue().toString());
                    model.setDateTime(dataSnapshot.child("NotificationsTime").getValue().toString());
                    notificationModels.add(model);
                }
                contactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
        ArrayList<NotificationModel> data;
        Context context;
        Activity activity;
        String TAG;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView title,dateTime,message;
            private ImageView trash;
            public MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                dateTime = view.findViewById(R.id.dateTime);
                message = view.findViewById(R.id.message);
                trash = view.findViewById(R.id.trash);
            }
        }

        public ContactAdapter(Context c, Activity a, ArrayList<NotificationModel> CompanyJobModal) {
            this.data = CompanyJobModal;
            context = c;
            activity = a;
            TAG = "***Adapter";
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notification_adapter, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
            NotificationModel model = data.get(position);
            viewHolder.title.setText(model.getTitle());
            viewHolder.message.setText(model.getMessage());
            // Create a SimpleDateFormat object with the desired date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

// Convert the timestamp to a Date object
            Date date = new Date(Long.parseLong(model.dateTime));

// Format the Date object into a String representation of the date
            String formattedDate = sdf.format(date);
            viewHolder.dateTime.setText(formattedDate);

            viewHolder.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.child(model.getId()).removeValue();
                    Toast.makeText(context, "Notification has been removed", Toast.LENGTH_SHORT).show();
                    contactAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
//        return  5;
            return data.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }


}