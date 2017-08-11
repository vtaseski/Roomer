package com.roomer.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vladotaseski on 8/7/17.
 */

public class Data {

    private Context ctx;

    public Data(Context ctx) {
        this.ctx = ctx;

    }

    public void login(String username, String password, String token, boolean logged) {
        SharedPreferences settings = ctx.getSharedPreferences("Pref", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("access_token", token);
        editor.putBoolean("logged", logged);
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences settings = ctx.getSharedPreferences("Pref", 0);
        return settings.getBoolean("logged", false);
    }

    public void logout() {
        SharedPreferences settings = ctx.getSharedPreferences("Pref", 0);
        settings.edit().clear().commit();

    }

    public String accessToken() {
        SharedPreferences settings = ctx.getSharedPreferences("Pref", 0);
        return settings.getString("access_token", "");
    }
}
