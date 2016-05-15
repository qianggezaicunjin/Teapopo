package com.teapopo.life.model.erroinfo;

import android.util.Log;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.teapopo.life.model.BaseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/11.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ErroInfo extends BaseEntity {
    public String errcode;

    public String errmsg;

}
