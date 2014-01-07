package cn.edu.zju.cadal.zooz.test.sgd;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class RatingsSource implements Runnable {
	private final Configuration config;
	private BufferedReader input;
	private int sequenceNum = 0;
	private long inputStartTime;
	
	public static boolean endOfData = false;
	public static int unfinishedBlock = 0;
	public RatingsSource(Configuration config) {
		this.config = config;
	}
	
	public boolean isDistributed() {
		return false;
	}

	public void run() {
		try {
			input = new BufferedReader(new FileReader(config.inputFilename));
			inputStartTime = System.currentTimeMillis();
			System.out.printf("######## Input started: %1$tY-%1$tb-%1$td %1$tT %tZ\n", inputStartTime);
		} catch (IOException e) {
			System.err.println("######## RatingsSource.fail: input or output error");
			StreamSGD.debugOut.print("######## RatingsSource.fail: input or output error");
			StreamSGD.debugOut.println();
			throw new RuntimeException(e);
		}
		if (input == null){
			if (config.debug) {
				System.err.println("######## RatingsSource.fail: Can't open inputfile:"+config.inputFilename);
				StreamSGD.debugOut.print("######## RatingsSource.fail: Can't open inputfile:");
				StreamSGD.debugOut.println();
			}
			return;
		}
		try {
			int userBlockId, itemBlockId;
			TrainingExample ex;
			while(true){
				String line = input.readLine();
				if (line == null) {			// 输入完毕
					long inputEndTime = System.currentTimeMillis();
					System.out.printf("######## Input finished: %1$tY-%1$tb-%1$td %1$tT %tZ\n", inputEndTime);
					System.out.println("######## Elapsed input time: "
						+ ( inputEndTime - inputStartTime)/1000 + "s");
					
					endOfData = true;
					input.close();
					input = null;
					return;
				} else {
					try {
						String[] token = line.split(" ");		// 获得一个样本
						int userId = Integer.parseInt(token[0]);
						int itemId = Integer.parseInt(token[1]);
						float rating = Float.parseFloat(token[2]);
						
						ex = new TrainingExample(sequenceNum++, userId, itemId, rating);
						userBlockId = config.getUserBlockIdx(userId);
						itemBlockId = config.getItemBlockIdx(itemId);
						synchronized(StreamSGD.blocks[userBlockId][itemBlockId]){
							if(StreamSGD.blocks[userBlockId][itemBlockId].examples.size() == 0)
								unfinishedBlock++;
							StreamSGD.blocks[userBlockId][itemBlockId].examples.add(ex);
						}
						StreamSGD.latestExample[userBlockId][itemBlockId] = ex;
						
						if(config.debug)
						{
							//StreamSGD.debugOut.print("get " + sequenceNum +"th example.");
							//StreamSGD.debugOut.println();
						}					
						
						if (config.inputDelay > 0) {
							Thread.sleep(config.inputDelay);
						}
					} catch (Exception e) {
						System.err.println(e.toString());
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}