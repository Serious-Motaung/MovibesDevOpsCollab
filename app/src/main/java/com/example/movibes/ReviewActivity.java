package com.example.movibes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity implements RecyclerReviewListener{

    private List<Reviews> eventsList;
    private RecyclerView mList;

    private ImageView ivReviewEventFlyer;

    private RecyclerView.Adapter adapter;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    BottomNavigationView reviewNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TextView tvEvent = findViewById(R.id.tvReviewEvent);
        TextView tvReviews = findViewById(R.id.tvReviewReviews);
        Button btnAddReview = findViewById(R.id.btnReviewAdd);
        EditText etEventReview = findViewById(R.id.etEventReview);
        ivReviewEventFlyer = findViewById(R.id.ivReviewEventFlyer);
        eventsList = new ArrayList<>();
        mList = findViewById(R.id.review_list);

        adapter = new ReviewAdapter(getApplicationContext(),eventsList,this);

        reviewNavigation = findViewById(R.id.reviewNavigation);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(),linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        Bundle eventBundle = getIntent().getExtras();

        String sEvent = eventBundle.getString("Description");
        String sReviews = eventBundle.getString("Reviews");
        String sEventId = eventBundle.getString("EventId");
        String sFlyer = eventBundle.getString("Flyer");

        Glide.with(getApplicationContext())
                .load(sFlyer)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivReviewEventFlyer);

        tvEvent.setText("Reviewing " + sEvent);

        GetEventReviews(Integer.parseInt(sEventId));
        int iReviews = Integer.parseInt(sReviews);
        String sWording = "";
        if(iReviews > 1)
        {
            sWording = "reviews.";
        }
        else
        {
            sWording = "review.";
        }
        tvReviews.setText("This event has " + sReviews + " " + sWording);

        //sEventReviews = etEventReview.getText().toString();

        int iEventId = Integer.parseInt(sEventId);
        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEventReview = etEventReview.getText().toString();
                AddReview(1,iEventId,sEventReview);
                //Toast.makeText(ReviewActivity.this,sEventReview,Toast.LENGTH_SHORT).show();
            }
        });

        reviewNavigation.setOnItemSelectedListener(item ->
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
    private void AddReview(int sProfileId,int sEventId,String sReview){
        //String url = "http://movibez.af-south-1.elasticbeanstalk.com/Authentication/Register/";
        //String url ="http://movibez.af-south-1.elasticbeanstalk.com/Authentication/Register?_username=poster&_password=poster";
        //String sProfileId = "",sEventId="",sReview="";
        String url ="http://movibez.af-south-1.elasticbeanstalk.com/Reviews/AddReview?_profileId="+sProfileId+"" +
                "&_eventId="+sEventId+"" +
                "&_review="+sReview;
        //Utils.enableProgressBar(progressBar);

        final RequestQueue requestQueue = Volley.newRequestQueue(ReviewActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                response ->
                {

                    //Utils.hideProgressDialog(progressBar);
                    //Utils.hideProgressDialog(progressBar);
                    //Utils.alertDialogShow(context, "Event Added.");
                    Toast.makeText(ReviewActivity.this, "Review added successfully.", Toast.LENGTH_SHORT).show();
                    //requestQueue.stop();

                },(VolleyError volleyError) -> {
            //Utils.hideProgressDialog(progressBar);
            //Toast.makeText(RegisterActivity.this, "No Connection", Toast.LENGTH_LONG).show();
            if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                Toast.makeText(ReviewActivity.this, "" + volleyError.toString(), Toast.LENGTH_LONG).show();
            } else if (volleyError instanceof AuthFailureError) {
                Toast.makeText(ReviewActivity.this, "Authentication Failure", Toast.LENGTH_LONG).show();
            } else if (volleyError instanceof ServerError) {
                Toast.makeText(ReviewActivity.this, ""+volleyError.toString(), Toast.LENGTH_LONG).show();
            } else if (volleyError instanceof NetworkError) {
                Toast.makeText(ReviewActivity.this, "Network Error", Toast.LENGTH_LONG).show();
            } else if (volleyError instanceof ParseError) {
                Toast.makeText(ReviewActivity.this, "Parse Error", Toast.LENGTH_LONG).show();
            }
            requestQueue.stop();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        //Starts Request
        requestQueue.add(stringRequest);
    }

    private void GetEventReviews(int eventId)
    {
        String url = "http://movibez.af-south-1.elasticbeanstalk.com/Reviews/GetEventReviews?_eventId="+eventId;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Reviews review = new Reviews();

                        review.setReviewComment(jsonObject.getString("Review"));
                        review.setReviewProfileId(jsonObject.getString("ProfileId"));
                        //review.setDatePosted("Date Posted : " + sEventDate);
                        eventsList.add(review);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //progressDialog.dismiss();
                    }
                }
                //adapter.notifyDataSetChanged();
                //progressDialog.dismiss();
                //mList.setVisibility(View.VISIBLE);
                //mShimmerViewContainer.stopShimmerAnimation();
                //mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley",error.toString());
                //progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void onRowClicked(int position) {

    }
}