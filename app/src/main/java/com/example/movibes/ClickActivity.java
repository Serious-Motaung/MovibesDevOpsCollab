package com.example.movibes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClickActivity extends AppCompatActivity implements RecyclerViewClickInterface{

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    List<Water> moviesList;
    Water water;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);

        moviesList = new ArrayList<>();
        water = new Water();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(),moviesList, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        ReadEventsFromAPI();
        //water.setsName("Bloem Water");
        //water.setsImage("https://picsum.photos/id/1000/5626/3635");
        //moviesList.add(water);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                moviesList.add("Black Widow (2020)");
//                moviesList.add("The Eternals (2020)");
//                moviesList.add("Shang-Chi and the Legend of the Ten Rings (2021)");
//                moviesList.add("Doctor Strange in the Multiverse of Madness (2021)");
//                moviesList.add("Thor: Love and Thunder (2021)");

                recyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private void ReadEventsFromAPI()
    {
        //final ProgressDialog progressDialog = new ProgressDialog(this);
        //progressDialog.setMessage("Loading...");
        //progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(API.GetEventByPostedDateDescending, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Water water = new Water();

                        water.setsName(jsonObject.getString("Description"));
                        water.setsImage(jsonObject.getString("ImageUrl"));

                        moviesList.add(water);
                        //DidUserVibe(1,1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //progressDialog.dismiss();
                    }
                }

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
    //    These are the interface Methods from our custom RecyclerViewClickInterface
    @Override
    public void onItemClick(int position) {
        //Intent intent = new Intent(this, NewActivity.class);
        //intent.putExtra("MOVIE_NAME", moviesList.get(position));
        //startActivity(intent);

            //Toast.makeText(getApplicationContext(),"" + moviesList.get(position).getsName(),Toast.LENGTH_SHORT).show();

        //moviesList.set(position,"Ntsima Matang");
        //recyclerAdapter.notifyItemChanged(position);
        //Toast.makeText(getApplicationContext(),""+moviesList.get(position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongItemClick(final int position) {
        //moviesList.remove(position);
        //recyclerAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onVibeClick(int position, View view) {
        String string = moviesList.get(position).getsName();
        //if(string.equals("Bloem Water"))
        //{
            //water.setsImage("https://picsum.photos/id/10/2500/1667");
            //moviesList.set(position,water);
            //recyclerAdapter.notifyItemRemoved(position);
        //}
        //String string = String.valueOf(view.getId());
        Toast.makeText(ClickActivity.this,"" + string, Toast.LENGTH_LONG).show();
    }

}