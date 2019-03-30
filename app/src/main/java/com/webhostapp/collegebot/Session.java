package com.webhostapp.collegebot;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private Context context;
    private SharedPreferences sharedPreferences;
    Session(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
    }
    public boolean getLogin(){
        return sharedPreferences.getBoolean("login",false);
    }

    public void setLogin(boolean login){
        sharedPreferences.edit().putBoolean("login",login).apply();
    }

    public String getId(){
        return sharedPreferences.getString("id","");
    }

    public void setId(String id){
        sharedPreferences.edit().putString("id", id).apply();
    }
}
