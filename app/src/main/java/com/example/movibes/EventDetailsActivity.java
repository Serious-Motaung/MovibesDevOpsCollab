package com.example.movibes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EventDetailsActivity extends AppCompatActivity {

    TextView tvVenue,tvDate,tvEntranceFee,tvDescription,tvLineUp,tvNotes;
    ImageView ivEventFlyer;
    Bundle eventBundle;
    BottomNavigationView detailsNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        tvVenue = findViewById(R.id.tvEventVenue);
        tvDate = findViewById(R.id.tvEventDate);
        tvEntranceFee = findViewById(R.id.tvEventEntranceFee);
        tvDescription = findViewById(R.id.tvEventDescription);
        tvLineUp = findViewById(R.id.tvEventLineUp);
        tvNotes = findViewById(R.id.tvEventNotes);
        ivEventFlyer = findViewById(R.id.ivEventFlyer);

        detailsNavigation = findViewById(R.id.detailsNavigation);

        eventBundle = getIntent().getExtras();
        if(eventBundle != null)
        {
            PopulateEventDetails(eventBundle);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No event details.",Toast.LENGTH_SHORT).show();
        }

        tvVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sAddress = "109 President Reitz Ave, Westdene, Bloemfontein, 9301";
                MoVibesTools.NavigateToEvent(getApplicationContext(),sAddress);
            }
        });

        tvLineUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(EventDetailsActivity.this);
                //We have added a title in the custom layout. So let's disable the default title.
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
                dialog.setCancelable(true);
                //Mention the name of the layout of your custom dialog.
                dialog.setContentView(R.layout.custom_lineup_dialog);

                //Initializing the views of the dialog.
                final TextView tvDetails_Lineup = dialog.findViewById(R.id.tvDetails_Lineup);
                //final EditText etVIP = dialog.findViewById(R.id.age_et);
                //final CheckBox termsCb = dialog.findViewById(R.id.details_term_cb);
                Button submitButton = dialog.findViewById(R.id.details_button);

                tvDetails_Lineup.setText(eventBundle.getString("LineUp"));


                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //String sVIP = etVIP.getText().toString();
                        //Boolean hasAccepted = termsCb.isChecked();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        detailsNavigation.setOnItemSelectedListener(item ->
        {
            // do stuff
            switch (item.getItemId()){

                case R.id.nav_home:
                    Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                    return true;

                case R.id.nav_profile:
                    Intent profileIntent = new Intent(getApplicationContext(),EditProfileActivity.class);
                    startActivity(profileIntent);
                    finish();
                    return true;

                case R.id.nav_vibey:
                    Intent vibeyIntent = new Intent(getApplicationContext(),VibeyActivity.class);
                    startActivity(vibeyIntent);
                    finish();
                    return true;
            }
            return false;
        });
    }
    public void PopulateEventDetails(Bundle eventBundle)
    {
        String sVenue = eventBundle.getString("Venue");
        String sDate = eventBundle.getString("Date");
        String sEntranceFee = eventBundle.getString("EntranceFee");
        String sDescription = eventBundle.getString("Description");
        String sLineUp = eventBundle.getString("LineUp");
        String sNotes = eventBundle.getString("Notes");
        String sImageUrl = eventBundle.getString("ImageUrl");
        String _sStartDate = eventBundle.getString("StartDate");
        String _sEndDate = eventBundle.getString("EndDate");

        String _sRawStartTime = _sStartDate.substring(_sStartDate.indexOf("T")+1);
        _sRawStartTime.trim();

        String _sRawEndTime = _sEndDate.substring(_sEndDate.indexOf("T")+1);
        _sRawEndTime.trim();

        String sStartTime = GateTimeFromDate(_sRawStartTime);
        String sEndTime = GateTimeFromDate(_sRawEndTime);

        tvVenue.setText(sVenue+" >");
        tvDate.setText("Date: " + sDate + " @ " + sStartTime + " - " + sEndTime);
        tvEntranceFee.setText("Entrance Fee: " + sEntranceFee);
        tvDescription.setText(sDescription);
        tvLineUp.setText("Line-up: " + sLineUp);
        tvNotes.setText("Please note: " + sNotes);

        Glide.with(getApplicationContext())
                .load(sImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivEventFlyer);
    }
    public String GateTimeFromDate(String sTime) {
        String sFinalTime="";
        if (sTime != null ) {
            sFinalTime = sTime.substring(0, sTime.length() - 3);
        }
        else
        {
            sFinalTime = "No Date";
        }
        return sFinalTime;
    }
}