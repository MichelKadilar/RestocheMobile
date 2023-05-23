import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.restoche.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

import controller.NotificationController;
import model.NotificationComparable;


public class ManageNotificationActivity extends AppCompatActivity {

    private ArrayList<NotificationComparable> listNotificationOriginal;
    private ArrayList<NotificationComparable> listNotification;
    private ArrayList<NotificationComparable> listNotificationFilter;

    private int indice = 0;
    private ListAdaptaterNotification listAdaptaterNotifPlan;

    private int year, month, day;
    private Calendar calendar;
    private Date selectedDate = null;

    Button setDate;
    EditText titleEdit;
    EditText descriptionEdit;
    TextView noNotifText;

    View filterView;
    boolean hideFilterView = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ManageNotificationActivity);
        Intent intent = getIntent();
        /*if (intent != null){
            this.listNotificationOriginal = intent.getParcelableExtra("listNotif");
            Toast.makeText(getApplicationContext(), "YYYESSS in notif",
                    Toast.LENGTH_SHORT)
                    .show();
        }*/
        this.noNotifText = findViewById(R.id.noNotiftextView);
        this.filterView = findViewById(R.id.filterView);
        this.listNotificationOriginal  = new ArrayList<>();
        this.listNotificationOriginal = NotificationController.listNotificationOriginal;
        this.listNotification = new ArrayList<>();
        this.listNotificationFilter = new ArrayList<>();
        //for(int i= 0 ; i < 5 ; i++)listNotificationOriginal.add(createNotification("Notification Init","C'est la notif "));

        this.listNotification = cloneList(listNotificationOriginal);
        setupListView(listNotification);

        setDate = (Button) findViewById(R.id.setDateButton);
        titleEdit = findViewById(R.id.titleEditText);
        descriptionEdit = findViewById(R.id.descriptionEditText);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        View FilterContainer = findViewById(R.id.FilterContainer);
        FilterContainer.setOnClickListener(view -> {
            //ImageView arrow = findViewById(R.id.arrowImageView);
            if(hideFilterView){
                hideFilterView = false;
                filterView.setVisibility(LinearLayout.VISIBLE);
            }
            else{
                hideFilterView = true;
                filterView.setVisibility(LinearLayout.GONE);
            }        });


        Button addNotif = (Button) findViewById(R.id.addNotifButton);
        addNotif.setOnClickListener(view -> {
            addNotification();
        });

        Button filter = (Button) findViewById(R.id.filterValidationButton);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterNotif();
                ImageView deleteFilter = (ImageView) findViewById(R.id.deleteFilterImageView);
                deleteFilter.setVisibility(LinearLayout.VISIBLE);
            }
        });
        //deleteFilterImageView
        ImageView deleteFilter = (ImageView) findViewById(R.id.deleteFilterImageView);
        deleteFilter.setOnClickListener(view -> {
            this.listNotification = cloneList(this.listNotificationOriginal);
            setupListView(listNotification);
            descriptionEdit.setText("");
            titleEdit.setText("");
            setDate.setText("Selectionner une date");
            selectedDate = null;
            deleteFilter.setVisibility(LinearLayout.INVISIBLE);
        });

        //setDateButton
        Button setDate = (Button) findViewById(R.id.setDateButton);
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressWarnings("deprecation")
            public void onClick(View v) {
                showDialog(999);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                @SuppressWarnings("deprecation")
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    //showDate(arg1, arg2+1, arg3);
                    Date date = new Date(arg1, arg2, arg3);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
                    setDate.setText(sdf.format(date));
                    Toast.makeText(getApplicationContext(), sdf.format(date),
                                    Toast.LENGTH_SHORT)
                            .show();
                    selectedDate = date;
                }
            };

    private void setupListView(ArrayList<NotificationComparable> list){
        if(list.size()>0)this.noNotifText.setVisibility(LinearLayout.GONE);
        else this.noNotifText.setVisibility(LinearLayout.VISIBLE);
        listAdaptaterNotifPlan = new ListAdaptaterNotification(ManageNotificationActivity.this, list);
        ListView notifListView = findViewById(R.id.notifListView);
        notifListView.setAdapter(listAdaptaterNotifPlan);
    }

    private void addNotification() {
        this.noNotifText.setVisibility(LinearLayout.GONE);
        NotificationComparable notif = createNotification("Notification","Je suis une notif");
        NotificationActivity.getNotificationManager().notify(indice++, notif.getNotification());
        listNotificationOriginal.add(notif);
        listNotification.add(notif);
        Collections.sort(listNotification);
        Collections.reverse(listNotification);
        listAdaptaterNotifPlan.notifyDataSetChanged();
    }

    NotificationComparable createNotification(String titre, String text){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channel_ID)
                .setSmallIcon(com.google.firebase.database.ktx.R.drawable.common_full_open_on_phone)
                .setContentTitle(titre+indice)
                .setContentText(text);

        Notification notif = notification.build();
        return new NotificationComparable(notif, new Date(), titre, text);
    }

    public void filterNotif(){
        this.listNotificationFilter = cloneList(this.listNotificationOriginal);
        //recupère les différents filter émis par l'utilisateur
        String title = titleEdit.getText().toString();
        String description = descriptionEdit.getText().toString();


        //appelle les méthodes de ces filtres
        if(title != "")
            this.listNotificationFilter = filterByTitle(listNotificationFilter, title);
        if(description != "")
            this.listNotificationFilter = filterByDescription(listNotificationFilter, description);
        if(selectedDate != null){
            RadioButton before = findViewById(R.id.beforeRadioButton);
            RadioButton after = findViewById(R.id.afterRadioButton);
            if(before.isChecked())
                this.listNotificationFilter = filterByBeforeDate(listNotificationFilter, selectedDate);
            else if(after.isChecked())
                this.listNotificationFilter = filterByAfterDate(listNotificationFilter, selectedDate);
            else
                this.listNotificationFilter = filterByDate(listNotificationFilter, selectedDate);
        }
        //réinstancie la listview
        this.listNotification = listNotificationFilter;
        setupListView(listNotification);
    }


    public ArrayList<NotificationComparable> filterByTitle(ArrayList<NotificationComparable> list, String title){
        return list.stream()
                .filter(
                        notif -> notif.getTitle().contains(title))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<NotificationComparable> filterByDescription(ArrayList<NotificationComparable> list, String description){
        return list.stream().filter(notif -> notif.getDescription().contains(description)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<NotificationComparable> filterByDate(ArrayList<NotificationComparable> list, Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
        return list.stream().filter(notif -> notif.getDayNotif().equals(sdf.format(date))).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<NotificationComparable> filterByBeforeDate(ArrayList<NotificationComparable> list, Date date){
        return list.stream().filter(notif -> notif.getNotifDate().before(date)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<NotificationComparable> filterByAfterDate(ArrayList<NotificationComparable> list, Date date){
        return list.stream().filter(notif -> notif.getNotifDate().after(date)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<NotificationComparable> cloneList(ArrayList<NotificationComparable> list) {
        ArrayList<NotificationComparable> clone = new ArrayList<NotificationComparable>(list.size());
        for (NotificationComparable item : list) clone.add(new NotificationComparable(item.getNotification(), item.getNotifDate(), item.getTitle(), item.getDescription()));
        return new ArrayList<NotificationComparable>(new HashSet<NotificationComparable>(clone));
    }
}