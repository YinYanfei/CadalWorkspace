/*
 * Created on 2004-12-1
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package cn.cadal.recommender;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import cn.cadal.recommender.input.InputManager;
import cn.cadal.recommender.input.eachmovie.EachMovieItemCollection;
import cn.cadal.recommender.input.eachmovie.EachMovieRatingCollection;
import cn.cadal.recommender.input.eachmovie.EachMovieUserCollection;
import cn.cadal.recommender.spi.ItemCollection;
import cn.cadal.recommender.spi.ItemFactory;
import cn.cadal.recommender.spi.RatingCollection;
import cn.cadal.recommender.spi.RatingFactory;
import cn.cadal.recommender.spi.User;
import cn.cadal.recommender.spi.UserCollection;
import cn.cadal.recommender.spi.UserFactory;
import cn.cadal.recommender.spi.Visitor;
import cn.cadal.util.Loader;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Controller {

    private static Logger logger = Logger.getLogger(Controller.class.getName());

    public static void main(String[] args) {
        Loader.load();

        InputManager.setConfigurator(Loader.getConfigurator());
        InputManager.setAllInstance();

        UserFactory uF = InputManager.getUserFactory();
        UserCollection uC = InputManager.getUserCollection();
        uC.makeAllInstance(uF);

        ItemFactory iF = InputManager.getItemFactory();
        ItemCollection iC = InputManager.getItemCollection();
        iC.makeAllInstance(iF);

        RatingCollection rC = InputManager.getRatingCollection();
        RatingFactory rF = InputManager.getRatingFactory();
        rC.makeAllInstance(rF);
        
        if (EachMovieRatingCollection.class.isInstance(rC)) {
            ((EachMovieRatingCollection) rC).addRatingIdxToUserAndItem(uC, iC);
            if (EachMovieUserCollection.class.isInstance(uC))
                if (EachMovieItemCollection.class.isInstance(iC)) {
                    try {
                        ObjectOutputStream out = new ObjectOutputStream(
                                new FileOutputStream("eachmovie.dat"));
                        out.writeObject(uC);
                        if (logger.isDebugEnabled()) {
                            uC.traverse("All User who has Interest:",
                                    new Visitor() {
                                        public void performedAction(Object obj) {
                                            if (null == obj)
                                                return;
                                            if (((User) obj).getRatingIdxList()
                                                    .size() > 0)
                                                System.out.println((User) obj);
                                        }
                                    });
                        }
                        out.writeObject(iC);
                        out.writeObject(rC);
                        out.close();

                        logger.info(" Serialization done");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
