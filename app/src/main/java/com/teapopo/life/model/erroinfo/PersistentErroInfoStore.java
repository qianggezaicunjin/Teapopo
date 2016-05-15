package com.teapopo.life.model.erroinfo;

import android.util.Log;

import com.teapopo.life.data.remote.cookie.SerializableOkHttpCookies;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

import okhttp3.Cookie;
import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/15.
 */
public class PersistentErroInfoStore {



    protected String encodeErroInfo(SerializableErroInfo erroInfo){
        if (erroInfo == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(erroInfo);
        } catch (IOException e) {
            Timber.d("IOException in encodeCookie:%s", e.toString());
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param erroinfoString erroinfo string
     * @return ErroInfo object
     */
    protected ErroInfo decodeErroInfo(String erroinfoString) {
        byte[] bytes = hexStringToByteArray(erroinfoString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ErroInfo erroInfo = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            erroInfo = ((SerializableErroInfo) objectInputStream.readObject()).getErroInfo();
        } catch (IOException e) {
            Timber.d("IOException in decodeCookie");
        } catch (ClassNotFoundException e) {
            Timber.d("ClassNotFoundException in decodeCookie");
        }

        return erroInfo;
    }
    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
