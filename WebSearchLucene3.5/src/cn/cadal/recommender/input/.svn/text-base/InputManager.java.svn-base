/*
 * Created on 2004-11-16
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package cn.cadal.recommender.input;

import java.lang.reflect.Constructor;
import java.util.Map;

import cn.cadal.recommender.spi.Configurator;
import cn.cadal.recommender.spi.ItemCollection;
import cn.cadal.recommender.spi.ItemFactory;
import cn.cadal.recommender.spi.RatingCollection;
import cn.cadal.recommender.spi.RatingFactory;
import cn.cadal.recommender.spi.UserCollection;
import cn.cadal.recommender.spi.UserFactory;

/**
 * @author zhangyin
 * 
 * which factory to choose for generating User,Item,Rating
 *  
 */
public class InputManager {

    private static Configurator configurator = null;

    private static Map implMap = null;

    private static ItemFactory itemFactory = null;

    private static ItemCollection itemCollection = null;

    private static UserFactory userFactory = null;

    private static UserCollection userCollection = null;

    private static RatingFactory ratingFactory = null;

    private static RatingCollection ratingCollection = null;

    /**
     * @param configurator
     *            The configurator to set.
     */
    public static void setConfigurator(Configurator configurator) {
        InputManager.configurator = configurator;
        InputManager.implMap = configurator.getImplMap();
    }

    public static void setAllInstance() {
        if (null == itemFactory) {
            setItemFactory();
        }
        if (null == userFactory) {
            setUserFactory();
        }
        if (null == ratingFactory) {
            setRatingFactory();
        }
        if (null == userCollection)
            userCollection = (UserCollection) newInstance("UserCollection",
                    null);

        if (null == itemCollection)
            itemCollection = (ItemCollection) newInstance("ItemCollection",
                    null);

        if (null == ratingCollection)
            ratingCollection = (RatingCollection) newInstance(
                    "RatingCollection", null);
    }

    /**
     * @return Returns the itemFactory.
     */
    public static ItemFactory getItemFactory() {
        return itemFactory;
    }

    /**
     * @return Returns the ratingFactory.
     */
    public static RatingFactory getRatingFactory() {
        return ratingFactory;
    }

    /**
     * @return Returns the userFactory.
     */
    public static UserFactory getUserFactory() {
        return userFactory;
    }

    /**
     * @return Returns the configurator.
     */
    public static Configurator getConfigurator() {
        return configurator;
    }

    /**
     * @return Returns the itemCollection.
     */
    public static ItemCollection getItemCollection() {
        return itemCollection;
    }

    /**
     * @return Returns the ratingCollection.
     */
    public static RatingCollection getRatingCollection() {
        return ratingCollection;
    }

    /**
     * @return Returns the userCollection.
     */
    public static UserCollection getUserCollection() {
        return userCollection;
    }

    private static Class[] getClasses(Object[] params) {
        if ( null == params ) return null;
        
        int length = params.length;
        Class[] clazz = new Class[length];
        for (int i = 0; i < length; i++) {
            clazz[i] = params[i].getClass();
        }
        return clazz;
    }

    private static Object newInstance(String key, Object[] params) {
        Class c = (Class) implMap.get(key);
        if (c == null) {
            System.out.println(key + " Impl class not found");
        }

        try {
            String tempS = c.toString();
            //System.out.println("tempS::" + tempS);

            if (tempS.indexOf("EachMovie") > 0) {
                Class[] paramsClass = getClasses(params);
                Constructor cons = c.getConstructor(paramsClass);
                return cons.newInstance(params);
            }

            if (tempS.indexOf("Cadal") > 0) {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private static InputFactory setInputFactory(String key, Object[] params) {
        return (InputFactory) newInstance(key, params);
    }

    private static void setItemFactory() {
        String emfpi = configurator.getProperty("eachmovie.file.path.item");
        itemFactory = (ItemFactory) setInputFactory("ItemFactory",
                new Object[] { emfpi });
    }

    private static void setUserFactory() {
        String emfpu = configurator.getProperty("eachmovie.file.path.user");
        userFactory = (UserFactory) setInputFactory("UserFactory",
                new Object[] { emfpu });
    }

    private static void setRatingFactory() {
        String emfpr = configurator.getProperty("eachmovie.file.path.rating");
        ratingFactory = (RatingFactory) setInputFactory("RatingFactory",
                new Object[] { emfpr });
    }

}
