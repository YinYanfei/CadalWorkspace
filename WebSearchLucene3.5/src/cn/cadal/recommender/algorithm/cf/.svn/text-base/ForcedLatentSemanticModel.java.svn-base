/*
 * Created on 2004-12-15
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.algorithm.cf;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import cn.cadal.recommender.algorithm.SortedItemMap;
import cn.cadal.recommender.algorithm.output.EachMovieOutput;
import cn.cadal.recommender.input.RatingComparator;
import cn.cadal.recommender.input.URIRatingComparator;
import cn.cadal.recommender.input.eachmovie.EachMovieRating;
import cn.cadal.recommender.input.eachmovie.EachMovieRatingCollection;
import cn.cadal.recommender.spi.CollaborativeFiltering;
import cn.cadal.recommender.spi.Item;
import cn.cadal.recommender.spi.ItemCollection;
import cn.cadal.recommender.spi.Output;
import cn.cadal.recommender.spi.Rating;
import cn.cadal.recommender.spi.RatingCollection;
import cn.cadal.recommender.spi.User;
import cn.cadal.recommender.spi.UserCollection;
import cn.cadal.recommender.spi.Visitor;
import cn.cadal.util.Statistics;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ForcedLatentSemanticModel implements CollaborativeFiltering,
						  Serializable {

    private static Logger logger = Logger
	.getLogger(ForcedLatentSemanticModel.class);

    private int z = 40;//TODO: find the optimal value

    private int loopNum = 30; //TODO: find the optimal value

    private double totalMean = 0.303;

    private double totalVar = 0.2;

    private double[][] tableUz = null;

    private double[][] tableMeanYz = null;//TODO: to support multiple pdf

    transient private double[][] tableVarYz = null;

    private Random random = new Random();

    private RatingCollection ratingCollection = null;

    /**
     *  Offline Training
     */
    public ForcedLatentSemanticModel(UserCollection uC, ItemCollection iC,
				     RatingCollection rC) {
	ratingCollection = rC;
	// Init tableUz;
	tableUz = new double[uC.size()][z];
	for (int row = 0; row < uC.size(); row++) {
	    double accu = 0;
	    for (int col = 0; col < z; col++) {
		tableUz[row][col] = random.nextFloat();
		accu += tableUz[row][col];
	    }
	    for (int col = 0; col < z; col++) {
		tableUz[row][col] /= accu;
	    }
	}
	// Init tableMeanYz
	tableMeanYz = new double[iC.size()][z];
	for (int row = 0; row < iC.size(); row++) {
	    for (int col = 0; col < z; col++) {
		tableMeanYz[row][col] = totalMean;
	    }
	}
	// Init tableVarYz
	tableVarYz = new double[iC.size()][z];
	for (int row = 0; row < iC.size(); row++) {
	    for (int col = 0; col < z; col++) {
		tableVarYz[row][col] = totalVar;
	    }
	}

	train(uC, iC, rC);

    }

    /**
     * predict the possible SCORE the USER gives the ITEM
     */
    public double predict(User user, Item item) {
	// fault duration
	if (null == user)
	    return -1;
	if (null == item)
	    return -1;
	int userId = user.getUserId();
	int itemId = item.getItemId();
	double predictedScore = 0;
	//Done: NaN can't appear
	try {
	    for (int colZ = 0; colZ < z; colZ++)
		predictedScore += tableUz[userId][colZ]
		    * tableMeanYz[itemId][colZ];
	    if (Double.isNaN(predictedScore)) {
		logger.error("userID:" + user.getUserId() + "item:" + item);
		//System.out.println("user:"+user+"item:"+item);
	    }
	} catch (ArithmeticException ae) {
	    ae.printStackTrace();
	}
	return predictedScore;
    }
	 
    public void predict (User user, ItemCollection itemCollection,
			 RatingCollection predRC){
	if ( predRC.size() > 0)
	    System.out.println ("[error] the passed RatingCollection should be empty");

	double predScore = 0;
	Item item;
	int rIdx = 0 ;
	for (int idx = 0; idx < itemCollection.size(); idx++) {
	    item = itemCollection.get(idx);
	    if (null != item) {
		predScore = predict(user, item);
	//TODO: refactor predRc, not direct use EachMovieRatingCollection, move it to fun signature?
		Rating rating = new EachMovieRating(user,item,predScore);
		predRC.add(rIdx++,rating);
	    }
	}
    }

    /**
     * rank in descent order the predicted score set with given item collection
     * @param predRC: empty collection for 
     */
      
    public SortedItemMap rank(ItemCollection itemCollection,
			      RatingCollection predRC,
			      RatingComparator ratingComparator) {
	Item item;
	Rating rating;

	SortedItemMap sIM = new SortedItemMap(ratingComparator);
	//if (logger.isDebugEnabled())
	//System.out.println("item collection size" + itemCollection.size());

	int rIdx = 0;
	for ( rIdx = 0; rIdx < predRC.size(); rIdx ++){
	    rating  =  predRC.get(rIdx);
	    item = itemCollection.get(rating.getItemId());
	    sIM.put(rating, item);
	}
		
	return (SortedItemMap)sIM;
    }


    public void train(UserCollection uC, ItemCollection iC, RatingCollection rC) {
	double[][] tableQz = new double[rC.size()][z];
	try {
	    for (int loop = 0; loop < loopNum; loop++) {
		System.out.println("loop: " + loop);

		//E-step:update tableQz
		for (int row = 0; row < tableQz.length; row++) {
		    double accu = 0;
		    Rating rating = (Rating) rC.get(row);
		    for (int colZ = 0; colZ < tableQz[row].length; colZ++) {
			tableQz[row][colZ] = tableUz[rating.getUserId()][colZ]
			    * Statistics.normPdf(rating.getRatingScore(),
						 tableMeanYz[rating.getItemId()][colZ],
						 tableVarYz[rating.getItemId()][colZ]);
			accu += tableQz[row][colZ];
			if (logger.isDebugEnabled()) {
			}
		    }
		    for (int colZ = 0; colZ < tableQz[row].length; colZ++) {
			if (Double.isNaN(tableQz[row][colZ]))
			    throw new ArithmeticException("tableQz[" + row
							  + "]" + "[" + colZ + "]"
							  + " mustn't be NaN");
			tableQz[row][colZ] /= accu;

		    }
		}

		//M-step:update tableUz
		for (int row = 0; row < tableUz.length; row++) {
		    User user = (User) uC.get(row);
		    if (null == user)
			continue;
		    List idxList = user.getRatingIdxList();
		    if (0 == idxList.size()) {
			// tableUz[row][0] as mark to
			// indicate this row needn't be
			// updated
			if (-1 == tableUz[row][0]) {
			} else {
			    for (int colZ = 0; colZ < tableUz[row].length; colZ++)
				tableUz[row][colZ] = -1;
			}
			continue;
		    }

		    for (int colZ = 0; colZ < tableUz[row].length; colZ++) {
			double accu = 0;
			for (int rId = 0; rId < idxList.size(); rId++) {
			    int ratingIdx = ((Integer) idxList.get(rId))
				.intValue();
			    accu += tableQz[ratingIdx][colZ];
			}

			tableUz[row][colZ] = accu / idxList.size();
						
			if (Double.isNaN(tableUz[row][colZ]))
			    throw new ArithmeticException("tableUz[" + row
							  + "]" + "[" + colZ + "]"
							  + " mustn't be NaN");
		    }
		}

		//M-step:update tableMeanYz,tableVarYz
		//if (logger.isDebugEnabled()) {
		//    System.out.println("tableMeanYz:" + tableMeanYz.length);
		//}

		for (int row = 0; row < tableMeanYz.length; row++) {
		    //if (logger.isDebugEnabled()) {
		    //    System.out.println("tableMeanYz: row:" + row);
		    // }
		    Item item = (Item) iC.get(row);
		    if (null == item)
			continue;
		    List idxList = item.getRatingIdxList();
		    if (0 == idxList.size()) {
			// tableMeanYz[row][0] as mark to
			// indicate this row needn't be
			// updated
			if (-1 == tableMeanYz[row][0]) {
			} else {
			    for (int colZ = 0; colZ < tableUz[row].length; colZ++) {
				tableMeanYz[row][0] = -1;
				tableVarYz[row][0] = 0;
			    }
			}
			continue;
		    }

		    for (int colZ = 0; colZ < tableMeanYz[row].length; colZ++) {
			double accuMeanUp = 0, accuVarUp = 0, accuDown = 0;
			for (int rId = 0; rId < idxList.size(); rId++) {
			    int ratingIdx = ((Integer) idxList.get(rId))
				.intValue();
			    double rScore = rC.get(ratingIdx).getRatingScore();
			    accuMeanUp += rScore * tableQz[ratingIdx][colZ];
			    accuDown += tableQz[ratingIdx][colZ];
			}
			tableMeanYz[row][colZ] = accuMeanUp / accuDown;
			if (Double.isNaN(tableMeanYz[row][colZ]))
			    throw new ArithmeticException("tableMeanYz[" + row
							  + "]" + "[" + colZ + "]"
							  + " mustn't be NaN");
			for (int rId = 0; rId < idxList.size(); rId++) {
			    int ratingIdx = ((Integer) idxList.get(rId))
				.intValue();
			    double rScore = rC.get(ratingIdx).getRatingScore();
			    accuMeanUp += rScore * tableQz[ratingIdx][colZ];
			    accuVarUp += Math.pow(
						  (rScore - tableMeanYz[row][colZ]), 2)
				* tableQz[ratingIdx][colZ];
			}
			tableVarYz[row][colZ] = ((accuVarUp > 0) ? (accuVarUp / accuDown)
						 : totalVar);
		    }
		}
	    }
	} catch (ArithmeticException ae) {
	    ae.printStackTrace();
	}
    }

    //TODO: realtime update trained model

    public void onlineTrain(Rating rating, UserCollection uC,
			    ItemCollection iC, RatingCollection rC) {

    }

    public void write(Output output) {
	//output.write(this, "FLSM.dat");
	writeTableUz();
	writeTableMeanYz();
	writeTableVarYz();
    }

    private void writeTable(String fileName, double[][] table) {
	try {
	    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
									      fileName));
	    StringBuffer sB = new StringBuffer();
	    for (int idx = 0; idx < table.length; idx++) {
		sB.append(idx);
		sB.append(new char[] { ':', '\t' });
		for (int colZ = 0; colZ < table[idx].length; colZ++) {
		    sB.append(colZ);
		    sB.append(':');
		    sB.append(table[idx][colZ]);
		    sB.append('\t');

		}
		sB.append('\n');
		bufferedWriter.write(sB.toString());// minimise memory
		// consumption
		sB.delete(0, sB.length());
	    }

	    //bufferedWriter.
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    private void writeTableUz() {
	writeTable("TableUz.txt", tableUz);
    }

    private void writeTableMeanYz() {
	writeTable("TableMeanYz.txt", tableMeanYz);
    }

    private void writeTableVarYz() {
	writeTable("TableVarYz.txt", tableVarYz);
    }

    public static void main(String[] args) {
	try {
	    ObjectInputStream in = new ObjectInputStream
		(new FileInputStream("d:/zhangyin/CADAL/eachmovie.dat"));
	    UserCollection uC = (UserCollection) in.readObject();
	    ItemCollection iC = (ItemCollection) in.readObject();
	    RatingCollection rC = (RatingCollection) in.readObject();

	    if (false) {// Disable
		uC.traverse("All User who has Interest:", new Visitor() {
			public void performedAction(Object obj) {
			    if (null == obj)
				return;
			    if (((User) obj).getRatingIdxList().size() > 0)
				System.out.println((User) obj);
			}
		    });
	    }
	    if (false) {
		iC.traverse("All Item which is interested:", new Visitor() {
			public void performedAction(Object obj) {
			    if (null == obj)
				return;
			    if (((Item) obj).getRatingIdxList().size() > 0)
				System.out.println((Item) obj);
			}
		    });
		//rC.printAll();
	    }

	    CollaborativeFiltering cf = new ForcedLatentSemanticModel
		(uC, iC, rC);

	    cf.write(new EachMovieOutput());

	    ObjectInputStream cfin = new ObjectInputStream
		(new FileInputStream("d:/zhangyin/CADAL/FLSM.dat"));
	    
	    //CollaborativeFiltering cf = (ForcedLatentSemanticModel) cfin
	    //	.readObject();

	    User user = uC.get(1);
	    RatingCollection uRatingCollection= new EachMovieRatingCollection ();
	    
	    cf.predict (user, iC, uRatingCollection);

	    SortedItemMap sIM = cf.rank(iC,
					uRatingCollection,
					new URIRatingComparator ());
			
	    SortedItemMap uIM = new SortedItemMap(new URIRatingComparator ());
	    RatingCollection userRC = rC.subCollection(user,iC);
	    //debug
	    System.out.println("RatingCollection size:"+ userRC.size());
			
	    for ( int idx =0 ; idx < userRC.size(); idx++ ){
		Rating rating = userRC.get(idx);
		
		Item item = iC.get(rating.getItemId());
		uIM.put(rating, item);
	    }
	    System.out.println(uIM);

	    BufferedWriter bfw = new BufferedWriter
		(new FileWriter ("UserRCSortedByURI.txt"));

	    BufferedWriter bfw2 = new BufferedWriter
		(new FileWriter ("UserOrgRCSortedByURI.txt"));

			
	    try {
		bfw.write(sIM.toString());
		bfw2.write(uIM.toString());
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
