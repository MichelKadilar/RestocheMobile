package com.restoche;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationActivity extends AppCompatActivity {
    private EditText editTextTextEmailAddress,editTextTextPassword;
    private FirebaseAuth firebaseAuth;
    private Button button;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private String fcmToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        databaseReference = FirebaseDatabase.getInstance().getReference("Notifications");
        progressDialog = new ProgressDialog(this);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        button = findViewById(R.id.button);
        firebaseAuth = FirebaseAuth.getInstance();
        getFcmToken();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = editTextTextEmailAddress.getText().toString();
                String Password =  editTextTextPassword.getText().toString();
                if(Email.isBlank() || Password.isBlank())
                {
                    Toast.makeText(NotificationActivity.this, "all fields required", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    sendNotification("Android",fcmToken,"Login","User has been loggedIn",fcmToken);
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("Notifications","Login");
                    hashMap.put("NotificationsTime",""+System.currentTimeMillis());
                    hashMap.put("NotificationsMessage","User has been loggedIn");
                    databaseReference.child(String.valueOf(System.currentTimeMillis())).updateChildren(hashMap);
                    startActivity(new Intent(NotificationActivity.this, AfterLogin.class));
                    finish();

                }
            }
        });
    }
    public void getFcmToken(){

        SharedPreferences sharedPreferences = getSharedPreferences("My_Notification", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(task.isSuccessful()) {
                            fcmToken = task.getResult().toString();

                        }
                    }
                });
    }
    public static void sendNotification(final String osType, final String fcmToken, final String title, final String body, String others) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    MediaType JSON
                            = MediaType.parse("application/json; charset=utf-8");
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("body", body);
                    dataJson.put("title", title);
                    dataJson.put("Chat", others);
                    json.put("data", dataJson);
                    json.put("to", fcmToken);
                    if (osType.equals("Apple")) {
                        json.put("notification", dataJson);
                    }
                    Log.e("finalResponse", "token is " + fcmToken);
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request request = new Request.Builder()
//                        .header("Authorization", "key=AIzaSyANhp-yl7w4fKmgD-cuV_7U72CKCb3UA78") //Legacy Server Key
                            .header("Authorization", "key=AAAA_VWFCMM:APA91bFV_3A5iqZ51IBANlRBvhQ5_khi55Twz6hxQwtKqck2fVPkdJ3nK2OylyBt99l_Ax7K9PbqMthhRn-qRutCF_xQa11Z6odA8D50086tp5DVCKaeaTu8UvPJgvSYZhWiVYGT-IVe") //Legacy Server Key
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
                    Log.e("finalResponse", finalResponse);
                } catch (Exception e) {
                    //Log.d(TAG,e+"");
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

}