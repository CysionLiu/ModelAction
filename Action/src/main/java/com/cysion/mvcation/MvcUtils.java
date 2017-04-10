package com.cysion.mvcation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by CysionLiu on 2017/4/7.
 */
public class MvcUtils {

    private static final String MD5 = "MD5";

    /**
     * MD5加密
     * @param string  要加密的字符串
     * @param encoding 字符编码
     * @return  密文
     * @throws Exception
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
     * 检查是否有网络
     *
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
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            System.out.println("bad json: " + json);
            return false;
        }
    }

}

