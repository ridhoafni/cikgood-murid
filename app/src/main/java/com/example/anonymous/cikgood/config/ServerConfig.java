package com.example.anonymous.cikgood.config;

public class ServerConfig {
    public static final String DOMAIN_SERVER = "http://arslyn.com/cikgood/";
    public static final String SERVER_URL = DOMAIN_SERVER+"api/v1/";
    public static final String  API_ENDPOINT = SERVER_URL;
    public static final String UPLOAD_FOTO_ENDPOINT = DOMAIN_SERVER+"api/upload/upload.php";
    public static final String UPLOAD_FOTO_MURID_ENDPOINT = DOMAIN_SERVER+"api/upload/upload-profile-murid.php";
    public static final String UPLOAD_UPDATE_FOTO_MURID_ENDPOINT = DOMAIN_SERVER+"api/upload/upload-update-profile-murid.php";
    public static final String GOOGLE_API_ENDPOINT = "https://maps.googleapis.com/maps/api/";
    public static final String GOOGLE_API_KEY = "AIzaSyABTxpRVD2u_czfqMX7bb6H_WYBxbeXvC4";
    public static final String GURU_PATH = DOMAIN_SERVER+"backend/web/upload/images/guru/profile/";
    public static final String MURID_PROFILE_PATH = DOMAIN_SERVER+"backend/web/upload/images/murid/profile/";
    public static final String MURID_PATH = DOMAIN_SERVER+"backend/web/upload/images/murid/";

    public static final String URL_REGISTER_DEVICE = "http://192.168.1.102/FcmExample/RegisterDevice.php";
    public static final String URL_SEND_SINGLE_PUSH = DOMAIN_SERVER+"api/fcm/sendSinglePush.php";
    public static final String URL_SEND_SINGLE_PUSH_GURU =DOMAIN_SERVER+"api/fcm/sendSinglePushGuru.php";
}
