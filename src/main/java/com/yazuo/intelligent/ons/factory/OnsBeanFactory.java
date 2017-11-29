package com.yazuo.intelligent.ons.factory;

import com.yazuo.intelligent.ons.config.ExportOnsConfig;

/**
 * description
 * <p>
 * 2017-11-29 14:35
 *
 * @author scvzerng
 **/
public interface  OnsBeanFactory<C,P>  {

    C createConsumer(ExportOnsConfig config);
    P createProducer(ExportOnsConfig config);

}
