package com.example.pokedox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokemonActivity extends AppCompatActivity {
  private TextView mname;
  private TextView mnumber;
  private TextView type1textview;
  private TextView type2textview;
  private String url;
  private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        String url=getIntent().getStringExtra("url");
        mRequestQueue= Volley.newRequestQueue(getApplicationContext());
        url=getIntent().getStringExtra("url");
        mname=findViewById(R.id.pokemon_name);
        mnumber=findViewById(R.id.pokemon_number);
        type1textview=findViewById(R.id.pokemon_type1);
        type2textview=findViewById(R.id.pokemon_type2);
        mname.setText("name");
        load();


    }
    public void load()
    {
        type1textview.setText("");
        type2textview.setText("");
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                       mname.setText(response.getString("name"));
                       mnumber.setText(String.format("#03d",response.getInt("id")));
                           JSONArray types=response.getJSONArray("types");
                           for(int i=0;i< types.length();i++){
                               JSONObject typeEntry=types.getJSONObject(i);
                               int slot=typeEntry.getInt("slot");
                               String key=typeEntry.getJSONObject("type").getString("name");
                             if(slot==1){
                                 type1textview.setText(key);}
                               else  if(slot==2){
                                 type2textview.setText(key);}

                           }
                    }
                    catch (JSONException e) {
                    Log.e("pokedox", "jsonex[oception", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("pokedox", "jsonexception123");
            }
        });
    mRequestQueue.add(request);}
}
