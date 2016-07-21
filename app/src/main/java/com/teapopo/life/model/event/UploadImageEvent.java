package com.teapopo.life.model.event;

import com.teapopo.life.model.BaseEntity;

/**
 * Created by louiszgm on 2016/7/21.
 */
public class UploadImageEvent  {
    public String imageIdFromServer;
    public String iamgePath;
    public boolean isUploadSuccess = false;
}
