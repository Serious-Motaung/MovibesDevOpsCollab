package com.example.movibes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class VolleyResponseActivity extends AppCompatActivity implements VolleyResponse{

    EditText etName;
    Button btnValidate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_response);

        etName = findViewById(R.id.etVolleyName);
        btnValidate = findViewById(R.id.btnVolleyValidate);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckUserExistence(etName.getText().toString().trim());
                boolean isValid = PositiveResponse(this.toString());
                Toast.makeText(getApplicationContext(),"" + isValid,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void CheckUserExistence(String sUsername)
    {
        String url = "http://movibez.af-south-1.elasticbeanstalk.com/Authentication/CheckIfUserExist?_sUsername="+ sUsername;

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest( Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse( String s ) {
                //isValid = Boolean.parseBoolean(s);
                PositiveResponse(s);
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
    public boolean PositiveResponse(String positiveResponse) {
        boolean myResponse;
        if(positiveResponse.equals("false"))
        {
            myResponse = false;
        }
        else
        {
            myResponse = true;
        }
        return myResponse;
    }

    @Override
    public void NegativeResponse(String negativeResponse) {

    }

    private void NegativeResponse()
    {
        Toast.makeText(getApplicationContext(),"Try again.",Toast.LENGTH_SHORT).show();
    }

    private void PositiveResponse()
    {
        Toast.makeText(getApplicationContext(),"Happy...",Toast.LENGTH_SHORT).show();
    }
}