package cn.cadal.recommender.spi;
/*
 * Created on 2004-12-4
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */


import java.net.URL;
import java.util.Map;

/**
 * @author zhangyin
 *
 * the interface of setting configuration of recommendation system 
 * 
 */
public interface Configurator {
    void doConfigure(URL url);
    Map getImplMap();
    String getProperty(String keyName);

}
