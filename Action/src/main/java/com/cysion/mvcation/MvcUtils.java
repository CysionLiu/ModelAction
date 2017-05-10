package com.cysion.mvcation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by CysionLiu on 2017/4/7.
 */
public class MvcUtils {

    private static final String MD5 = "MD5";

    /**
     * MD5 encryption
     *
     * @param string   the string to be encrypted
     * @param encoding charset
     * @return result
     */
    public static String MD5encrypt(String string, String encoding) {
        if (string == null || encoding == null) {
            throw new IllegalArgumentException("no content");
        }

        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance(MD5).digest(string.getBytes());
        } catch (NoSuchAlgorithmException aE) {
            aE.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * @param context the context
     * @return the boolean
     */
    public static boolean isNetAvailable(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            return info.isAvailable();
        }
        return false;
    }


    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            return true;
        } catch (Exception e) {
            System.out.println("bad json: " + json);
            return false;
        }
    }

}

