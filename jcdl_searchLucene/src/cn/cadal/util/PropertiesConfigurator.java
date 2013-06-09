/*
 * Created on 2004-12-6
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.cadal.recommender.spi.Configurator;

/**
 * @author zhangyin
 * 
 * Getting deploy information of system
 *  
 */
public class PropertiesConfigurator implements Configurator {
    private static Logger logger = Logger
            .getLogger(PropertiesConfigurator.class);

    private static Properties appProps = new Properties();

    static String[] propNameList = { "UserFactory", "RatingFactory",
            "ItemFactory", "UserCollection", "RatingCollection",
            "ItemCollection" };

    private static Map implMap = null;

    public String getProperty(String keyName) {
        String tempS = appProps.getProperty(keyName);
        if (null == tempS)
            logger.warn(" null Property:" + keyName);
        return tempS;
    }

    private static synchronized void initMapIfNecessary() {
        if (implMap == null) {

            implMap = new HashMap();

            String classList = appProps
                    .getProperty("cadal.recommender.input.list");
            String[] cns = classList.split(",");
            String clazz = appProps.getProperty("cadal.recommender.input");
            Arrays.sort(cns);
            if (Arrays.binarySearch(cns, clazz) < 0) {
                clazz = appProps.getProperty("cadal.recommender.input.default");

            }

            for (int i = 0; i < propNameList.length; i++) {
                try {
                    String tempS = "cn.cadal.recommender.input."
                            + clazz.toLowerCase() + "." + clazz
                            + propNameList[i];
                    if (logger.isDebugEnabled())
                        logger.debug(tempS);
                    Class c = Class.forName(tempS);
                    implMap.put(propNameList[i], c);
                } catch (ClassNotFoundException e) {
                    logger.error("Couldn't find class: " + clazz
                            + propNameList[i]);
                }
            }
        }
    }

    public void doConfigure(URL url) {
        try {
            if (logger.isDebugEnabled())
                logger.debug(url.getFile());
            FileInputStream in = new FileInputStream(url.getFile());
            appProps.load(in);
            
        } catch (FileNotFoundException fe) {
            logger.error("file not found ");
            System.exit(1);
        } catch (IOException ie) {
            logger.error("IO error");
            System.exit(2);
        }
        initMapIfNecessary();
    }

    public Map getImplMap() {
        if (logger.isDebugEnabled())
            logger.debug("Map::" + implMap);
        return implMap;
    }

    public static void main(String[] args) {
    }
}
