package com.example.movibes;

import static com.example.movibes.API.DidUserVibe;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String TAG = "RecyclerAdapter";
    List<Water> moviesList;
    private RecyclerViewClickInterface recyclerViewClickInterface;
    Context context;
    private int selectedPosition = -1;

    public RecyclerAdapter(Context context,List<Water> moviesList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.moviesList = moviesList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Water water = moviesList.get(position);
        Glide.with(context).load(water.getsImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.rowCountTextView.setText(water.getsImage());
        holder.textView.setText(water.getsName());

        SessionManager sessionManager = new SessionManager(context);
        int iUserId = Integer.parseInt(sessionManager.getUserId());
        DidUserVibe(1,2,holder.ivVibes);
    }
    private void DidUserVibe(int iProfileId, int iEventId, ImageView ivVibes)
    {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest( Request.Method.GET, DidUserVibe + iProfileId + "&_profileId=" + iEventId, new Response.Listener<String>() {
            @Override
            public void onResponse( String s ) {
                boolean bMyResponse = Boolean.parseBoolean(s);
                if(bMyResponse)
                {
                    ivVibes.setImageResource(R.drawable.ic_vibe_yellow);
                }
                else
                {
                    ivVibes.setImageResource(R.drawable.ic_vibe);
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

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,ivVibes;
        TextView textView, rowCountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
            ivVibes = itemView.findViewById(R.id.ivVibes);

            ivVibes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ivVibes.setImageResource(R.drawable.ic_vibe_yellow);
                    selectedPosition = getAdapterPosition();
                    recyclerViewClickInterface.onVibeClick(getAdapterPosition(),v);

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
//                    moviesList.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());

                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());

                    return true;
                }
            });

        }

    }

}
