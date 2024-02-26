package com.example.calendrier;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import Entity.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    List<Event> eventsList = new ArrayList<>();
    private Date selectedTime;
    private LinearLayout eventsContainer;


    private void addEventView(Event event) {
        CardView eventCard = (CardView) getLayoutInflater().inflate(R.layout.event_card, this.eventsContainer, false);

        TextView eventTitle = eventCard.findViewById(R.id.eventTitle);
        TextView eventDetails = eventCard.findViewById(R.id.eventDetails);
        Button btnDelete = eventCard.findViewById(R.id.btnDelete);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateText = sdf.format(event.getDate());

        final int eventIndex = eventsList.indexOf(event);

        eventTitle.setText(event.getTitle() + " - " + dateText);
        eventDetails.setText(event.getDescription());

        btnDelete.setTag(eventIndex);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int eventIndex = (Integer) v.getTag();
                eventsList.remove(eventIndex);
                displayEventsForSelectedDate(selectedTime);
                Toast.makeText(MainActivity.this, "Suppréssion de l'évenement avec succès", Toast.LENGTH_SHORT).show();
            }
        });


        this.eventsContainer.addView(eventCard);
    }
    //Affichage Event par date
    private void displayEventsForSelectedDate(Date selectedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedSelectedDate = sdf.format(selectedDate);

        eventsContainer.removeAllViews();

        for (Event event : eventsList) {
            String eventDate = sdf.format(event.getDate());
            if (formattedSelectedDate.equals(eventDate)) {
                addEventView(event);
            }
        }
    }

    //Popup Création Evenement
    private void showAddEventPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_add_event, null);
        builder.setView(dialogView);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog alertDialog = (AlertDialog) dialog;
                EditText titleInput = alertDialog.findViewById(R.id.editTextEventTitle);
                EditText detailsInput = alertDialog.findViewById(R.id.editTextEventDescription);
                Button btnDelete = alertDialog.findViewById(R.id.btnDelete);

                String eventTitle = titleInput.getText().toString();
                String eventDetails = detailsInput.getText().toString();


                Event newEvent = new Event(selectedTime, eventTitle, eventDetails);
                eventsList.add(newEvent);
                displayEventsForSelectedDate(selectedTime);
                Toast.makeText(MainActivity.this, "Création de l'évenement avec succès", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Chargement d'évenements de test

    private void loadExistingEvents() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            eventsList.add(new Event(sdf.parse("26-03-2024"), "Event 1", "Quoicoubeh"));
            eventsList.add(new Event(sdf.parse("27-03-2024"), "Event 2", "azpofjdsofsjdfdpfqjpd"));
            eventsList.add(new Event(sdf.parse("28-03-2024"), "Event 3", "AAAAAAAAAAAAAAAAAAAAAAAAAAA"));
            eventsList.add(new Event(sdf.parse("28-03-2024"), "Event 4", "damn"));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CardView eventCard = (CardView) getLayoutInflater().inflate(R.layout.event_card, this.eventsContainer, false);
        setContentView(R.layout.activity_main);

        eventsContainer = findViewById(R.id.eventsContainer);
        CalendarView calendarView = findViewById(R.id.calendarView);
        Button btnAddEvent = findViewById(R.id.btnAddEvent);

        if(eventsList.isEmpty()) {
            loadExistingEvents();
        }

            btnAddEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddEventPopup();
                }
            });

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                selectedTime = calendar.getTime();
                displayEventsForSelectedDate(selectedTime);
            }
            });
        }



}