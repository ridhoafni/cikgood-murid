package com.example.anonymous.cikgood.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context _context;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String ID_MURID = "id";
    public static final String NAMA = "nama";
    public static final String TOKEN = "token";
    public static final String HP = "no_hp";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ALAMAT = "alamat";
    public static final String JK = "jk";
    public static final String NISN = "nisn";
    public static final String KELAS = "kelas";
    public static final String NAMA_SEKOLAH = "nama_sekolah";
    public static final String PHOTO = "photo";

    public Context get_context(){
        return _context;
    }

    //constructor
    public SessionManager(Context context){
        this._context       = context;
        sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(context);
        editor              = sharedPreferences.edit();
    }

    public void createLoginSession(Integer id, String nama, String token, String no_hp,
                                   String email, String password, String alamat,
                                   String jk, String nisn, String kelas, String nama_sekolah,
                                   String photo){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putInt(ID_MURID, id);
        editor.putString(NAMA, nama);
        editor.putString(TOKEN, token);
        editor.putString(HP, no_hp);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.putString(ALAMAT, alamat);
        editor.putString(JK, jk);
        editor.putString(NISN, nisn);
        editor.putString(KELAS, kelas);
        editor.putString(NAMA_SEKOLAH, nama_sekolah);
        editor.putString(PHOTO, photo);
        editor.apply();
    }

    public HashMap<String, String> getMuridProfile(){
        HashMap<String,String> murid = new HashMap<>();
        murid.put(ID_MURID, String.valueOf(sharedPreferences.getInt(ID_MURID,0)));
        murid.put(NAMA, sharedPreferences.getString(NAMA,null));
        murid.put(TOKEN, sharedPreferences.getString(TOKEN,null));
        murid.put(HP, sharedPreferences.getString(HP,null));
        murid.put(EMAIL, sharedPreferences.getString(EMAIL,null));
        murid.put(PASSWORD, sharedPreferences.getString(PASSWORD,null));
        murid.put(ALAMAT, sharedPreferences.getString(ALAMAT,null));
        murid.put(JK, sharedPreferences.getString(JK,null));
        murid.put(NISN, sharedPreferences.getString(NISN,null));
        murid.put(KELAS, sharedPreferences.getString(KELAS,null));
        murid.put(NAMA_SEKOLAH, sharedPreferences.getString(NAMA_SEKOLAH,null));
        murid.put(PHOTO, sharedPreferences.getString(PHOTO,null));
        return murid;
    }

    public void logoutMurid(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

}
