package com.teapopo.life.util.navigator;

import android.content.Context;
import android.os.Bundle;



/**
 * Created by Eric on 15/10/11.
 */
public class Navigator extends BaseNavigator {

    private static Navigator instance = new Navigator();

    private Navigator() {
    }

    public static Navigator getInstance() {
        return instance;
    }

}
