package com.teapopo.life.util;

import com.teapopo.life.util.databinding.DBRecyclerView;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.internal.bytecode.InstrumentationConfiguration;

/**
 * Created by louiszgm-pc on 2016/6/26.
 */
public class CustomShadowTestRunner extends RobolectricGradleTestRunner {
    public CustomShadowTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public InstrumentationConfiguration createClassLoaderConfig() {
        InstrumentationConfiguration.Builder builder = InstrumentationConfiguration.newBuilder();
        /**
         * 添加要进行Shadow的对象
         */
        builder.addInstrumentedClass(DBRecyclerView.class.getName());
        return builder.build();
    }
}
