/*
 * Created on 2004-12-16
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.util;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cn.cadal.recommender.spi.Configurator;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Loader {
    private static Logger logger = Logger.getLogger(Loader.class);

    private static final String log4jConfigFileName = "log4j.properties";

    private static final String recommenderConfigFileName = "recommender.properties";

    /**
     * @return Returns the configurator.
     */
    public static Configurator getConfigurator() {
        return configurator;
    }

    private static Configurator configurator = null;

    public static URL getResource(String resource) {
        URL url = null;
        ClassLoader classLoader = Loader.class.getClassLoader();
        if (classLoader != null) {
            logger.info("Trying to find [" + resource + "] using "
                    + classLoader + " class loader.");
            url = classLoader.getResource(resource);
            if (logger.isDebugEnabled())
                logger.debug("URL:" + url);
        }
        return url;
    }

    public static void load() {
        URL recommenderConfigUrl = Loader
                .getResource(recommenderConfigFileName);
        URL log4jConfigUrl = Loader.getResource(log4jConfigFileName);

        configurator = new PropertiesConfigurator();
        PropertyConfigurator.configure(log4jConfigUrl.getFile());
        configurator.doConfigure(recommenderConfigUrl);
    }
}
