package com.orson.heynews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by orson on 18-3-5.
 */

public class NetworkUtils {

    public enum NetworkType {
        MOBILE_2G,
        MOBILE_3G,
        MOBILE_4G,
        MOBILE_5G,
        WIFI,
        NotConnected,
        Unknown
    }

    public static NetworkUtils.NetworkType getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        NetworkUtils.NetworkType type = NetworkUtils.NetworkType.Unknown;
        if (info == null || !info.isConnected()) {
            type = NetworkUtils.NetworkType.NotConnected;
        }
        else {
            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    type = NetworkUtils.NetworkType.WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    type = getMobileNetworkType(context);
                    break;
            }
        }
        return type;
    }

    public static NetworkType getMobileNetworkType(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NetworkUtils.NetworkType.MOBILE_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NetworkUtils.NetworkType.MOBILE_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NetworkUtils.NetworkType.MOBILE_4G;
            default:
                return NetworkUtils.NetworkType.Unknown;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            try {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            } catch (Throwable e) {
                return false;
            }
        }
        return false;
    }

    public static boolean isWifiNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            }
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
            if (netInfo == null) {
                return false;
            }
            if ((NetworkInfo.State.CONNECTED).equals(netInfo.getState())) {
                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return false;
    }
    public static boolean isMobileNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            }
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
            if (netInfo == null) {
                return false;
            }
            if ((NetworkInfo.State.CONNECTED).equals(netInfo.getState())) {
                if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return false;
    }
}
