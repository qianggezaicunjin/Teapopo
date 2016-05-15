package com.teapopo.life.model.erroinfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;

/**
 * Created by louiszgm-pc on 2016/5/15.
 */
public class SerializableErroInfo implements Serializable {
    private transient ErroInfo mErroInfo;
    private transient ErroInfo mFromReadObject;
    public SerializableErroInfo(ErroInfo erroInfo){
        this.mErroInfo = erroInfo;
    }

    public ErroInfo getErroInfo() {
        ErroInfo result = mErroInfo;
        if (mFromReadObject != null) {
            result = mFromReadObject;
        }
        return result;
    }
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(mErroInfo.errcode);
        out.writeObject(mErroInfo.errmsg);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String errcode = (String) in.readObject();
        String errmsg = (String) in.readObject();
        ErroInfo erroInfo = new ErroInfo();
        erroInfo.errcode = errcode;
        erroInfo.errmsg = errmsg;
    }
}
