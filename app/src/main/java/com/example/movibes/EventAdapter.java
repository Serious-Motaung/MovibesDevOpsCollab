package com.example.movibes;

import static com.example.movibes.API.DidUserVibe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    int row_index = -1;
    private RecyclerViewClickListener listener;
    private Context context;
    private List<Events> list;

    public EventAdapter(Context context, List<Events> list, RecyclerViewClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new EventAdapter.ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Events event = list.get(position);

        holder.tvDescription.setText(event.getDescription());
        holder.tvDatePosted.setText(event.getDatePosted());
        holder.tvVenue.setText(event.getVenue());
        holder.tvVibes.setText(event.getEventVibes());
        holder.tvReviews.setText(event.getEventReviews());
        //Events events = getItemId(getAdapterPosition());
        SessionManager sessionManager = new SessionManager(context);
        int iUserId = Integer.parseInt(sessionManager.getUserId());


        Glide.with(context).load(event.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivFlyer);
        Glide.with(context).load(event.getProfilePic()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivProfilePic);
        //holder.ibVibes.setImageResource(R.drawable.ic_vibe);

        holder.ibReviews.setImageResource(R.drawable.ic_review);
        holder.ibReviews.setColorFilter(holder.ibReviews.getContext().getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        holder.ibShare.setImageResource(R.drawable.ic_share);
        holder.ibShare.setColorFilter(holder.ibShare.getContext().getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        //DidUserVibe(iUserId,Integer.parseInt(event.getEventID()),holder.ibVibes);

        holder.ibVibes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = holder.getAdapterPosition();
                //notifyDataSetChanged();
                vibeManagement(iUserId,Integer.parseInt(event.getEventID()));
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        if(row_index == holder.getAdapterPosition())
        {
            holder.ibVibes.setImageResource(R.drawable.ic_vibe);
            holder.ibVibes.setColorFilter(holder.ibVibes.getContext().getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
        }
        else
        {
            holder.ibVibes.setImageResource(R.drawable.ic_vibe);
            holder.ibVibes.setColorFilter(holder.ibVibes.getContext().getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        }
    }
    private void DidUserVibe(int iProfileId,int iEventId,ImageButton ibVibes)
    {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest( Request.Method.GET, DidUserVibe + iProfileId + "&_profileId=" + iEventId, new Response.Listener<String>() {
            @Override
            public void onResponse( String s ) {
                boolean bMyResponse = Boolean.parseBoolean(s);
                if(bMyResponse)
                {
                    //ibVibes.setColorFilter(ibVibes.getContext().getResources().getColor(R.color.teal_200), PorterDuff.Mode.SRC_ATOP);
                    ibVibes.setImageResource(R.drawable.ic_vibe);
                    ibVibes.setColorFilter(ibVibes.getContext().getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                }
                else
                {
                    //ibVibes.setColorFilter(ibVibes.getContext().getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                    ibVibes.setImageResource(R.drawable.ic_vibe);
                    ibVibes.setColorFilter(ibVibes.getContext().getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }, volleyError -> {
            try {
                String responseBody = new String( volleyError.networkResponse.data, "utf-8" );
                JSONObject jsonObject = new JSONObject( responseBody );
            } catch ( JSONException e ) {
                //Handle a malformed json response
            } catch (UnsupportedEncodingException error){

            }
        });
        requestQueue.add(request);
    }
    private String vibeManagement(int profileId,int eventId){
        String url = profileId+"&_eventId="+eventId;
        //Utils.enableProgressBar(progressBar);
        String sResults = "";
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.VibesManagement + url,

                response ->
                {

                    //Utils.hideProgressDialog(progressBar);
                    //Utils.hideProgressDialog(progressBar);
                    //Utils.alertDialogShow(context, "Event Added.");
                    //PositiveResponse(response);
                    //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    //requestQueue.stop();

                },(VolleyError volleyError) -> {
            //Utils.hideProgressDialog(progressBar);
            //Toast.makeText(RegisterActivity.this, "No Connection", Toast.LENGTH_LONG).show();
            if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                Toast.makeText(context, "" + volleyError.toString(), Toast.LENGTH_LONG).show();
            } else if (volleyError instanceof AuthFailureError) {
                Toast.makeText(context, "Authentication Failure", Toast.LENGTH_LONG).show();
            } else if (volleyError instanceof ServerError) {
                Toast.makeText(context, ""+volleyError.toString(), Toast.LENGTH_LONG).show();
            } else if (volleyError instanceof NetworkError) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show();
            } else if (volleyError instanceof ParseError) {
                Toast.makeText(context, "Parse Error", Toast.LENGTH_LONG).show();
            }
            requestQueue.stop();
        })
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        //Starts Request
        requestQueue.add(stringRequest);
        //adapter.notifyItemChanged(1);
        //adapter.notifyDataSetChanged();
        return sResults;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvVenue, tvDatePosted, tvDescription, tvVibes, tvReviews;
        public ImageView ivFlyer, ivProfilePic;
        public ImageButton ibVibes, ibReviews, ibShare;
        public LinearLayout llProfileSpace;


        public ViewHolder(@NonNull View itemView, final RecyclerViewClickListener listener) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDatePosted = itemView.findViewById(R.id.tvPostedDate);
            tvVenue = itemView.findViewById(R.id.tvEstablishment);
            ivFlyer = itemView.findViewById(R.id.ivFlyer);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvVibes = itemView.findViewById(R.id.tvVibes);
            tvReviews = itemView.findViewById(R.id.tvReviews);
            ibVibes = itemView.findViewById(R.id.ibVibes);
            ibReviews = itemView.findViewById(R.id.ibReviews);
            ibShare = itemView.findViewById(R.id.ibShare);
            llProfileSpace = itemView.findViewById(R.id.llProfileSpace);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onRowClicked(getAdapterPosition());
                    }
                }
            });
            ibVibes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        listener.onVibeClicked(v, getAdapterPosition());
                        //row_index = getAdapterPosition();
                        //ibVibes.setImageResource(R.drawable.ic_location);
                        //SessionManager sessionManager = new SessionManager(context);
                        //int iUserId = Integer.parseInt(sessionManager.getUserId());
                        //Events events = new Events();
                        //int iEventId = Integer.parseInt(events.getEventID());
                        //vibeManagement(iUserId,iEventId);
                        //notifyItemChanged(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }
            });

            ibReviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onReviewClicked(v, getAdapterPosition());
                    }
                }
            });
            ibShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onShareClicked(v, getAdapterPosition());
                    }
                }
            });
            llProfileSpace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onProfilePicClicked(getAdapterPosition());
                    }
                }
            });
        }
    }
}
