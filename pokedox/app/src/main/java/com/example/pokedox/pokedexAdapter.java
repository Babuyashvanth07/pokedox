package com.example.pokedox;

import android.content.Context;
import android.content.Intent;
import android.text.GetChars;
import android.util.Log;
import  android.view.LayoutInflater;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokedox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public  class pokedexAdapter extends RecyclerView.Adapter<pokedexAdapter.pokedexViewHolder> {

    public static class pokedexViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout containerlayout;
        public TextView mTextView;

        pokedexViewHolder(View view){
            super(view);
            containerlayout=view.findViewById(R.id.pokedox_row);
            mTextView=view.findViewById(R.id.pokedox_row_text);
            containerlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pokemon current=(pokemon)containerlayout.getTag();
                    Intent intent=new Intent(v.getContext(),PokemonActivity.class);
                    intent.putExtra("url",current.getUrl());
                    //intent.putExtra("number",current.getNumber());
                    v.getContext().startActivity(intent);
                }
            });
        }



    }


    private List<pokemon>  Pokemon=new  ArrayList<>();
    private RequestQueue mRequestQueue;
    pokedexAdapter(Context context){
        mRequestQueue= Volley.newRequestQueue(context);
        loadPockemon();
    }

    public void loadPockemon(){
        String url= "https://pokeapi.co/api/v2/pokemon?limit=151";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");

                    for(int i=0;i<results.length();i++){
                        JSONObject result=results.getJSONObject(i);
                        String name=result.getString(("name"));
                        name=name.substring(0,1).toUpperCase()+name.substring(1);
                                     Pokemon.add(new pokemon(result.getString("name"),result.getString("url")));
                    }
                 notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("pokedox", "jsonexception", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("pokedox", "jsonexception");
            }
        });
            mRequestQueue.add(request);
        }


    }
    @NonNull
    @Override
    public pokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pokedox_row,parent,false);

        return new pokedexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pokedexViewHolder holder, int position) {
        pokemon current=Pokemon.get(position);
        holder.mTextView.setText(current.getName());
        holder.containerlayout.setTag(current);

    }

    @Override
    public int getItemCount() {
        return Pokemon.size();
    }
}

