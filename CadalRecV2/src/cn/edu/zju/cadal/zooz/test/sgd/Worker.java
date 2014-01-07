package cn.edu.zju.cadal.zooz.test.sgd;

import java.util.Random;

public class Worker implements Runnable {	
	private final Random random = new Random(); 
	private final Configuration config;
	public Worker(Configuration config) {
		this.config = config;
	}
	
	public void run() {
		boolean isAvailable;
		int userBlockId, itemBlockId;
		while(true){
			// choice a block
			isAvailable = true;
			userBlockId = random.nextInt(config.numUserBlocks);
			synchronized(StreamSGD.workingUser){
				if(StreamSGD.workingUser[userBlockId]){
					for(int i=userBlockId+1; i<config.numUserBlocks; i++)
					{
						//need a lock
						if(!StreamSGD.workingUser[i])
						{
							StreamSGD.workingUser[userBlockId] = true;
							userBlockId = i;
							break;
						}
						if(i == config.numUserBlocks-1)
							i = -1;
						if(i == userBlockId)
							isAvailable = false;
					}
				}else
					StreamSGD.workingUser[userBlockId] = true;
			}
			if(!isAvailable)
			{
				try {
					Thread.sleep(config.processDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			
			itemBlockId = random.nextInt(config.numItemBlocks);
			synchronized(StreamSGD.workingUser){
				if(StreamSGD.workingItem[itemBlockId]){
					for(int i=itemBlockId+1; i<config.numItemBlocks; i++)
					{
						if(!StreamSGD.workingItem[i]){
							StreamSGD.workingItem[itemBlockId] = true;
							itemBlockId = i;
							break;
						}
						if(i == config.numItemBlocks-1)
							i = -1;
						if(i == itemBlockId)
							isAvailable = false;
					}
				}else
					StreamSGD.workingItem[itemBlockId] = true;
			}
			if(!isAvailable)
			{
				StreamSGD.workingUser[userBlockId] = false;
				try {
					Thread.sleep(config.processDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			
			synchronized(StreamSGD.workingUser){
				StreamSGD.debugOut.println(Thread.currentThread());
				int count = 0;
				for(int i = 0; i < config.numUserBlocks; ++i){
					StreamSGD.debugOut.printf("%b ", StreamSGD.workingUser[i]);
					if(StreamSGD.workingUser[i]) ++count;
				}
				StreamSGD.debugOut.println(" " + count);
				count = 0;
				for(int i = 0; i < config.numItemBlocks; ++i){
					StreamSGD.debugOut.printf("%b ", StreamSGD.workingItem[i]);
					if(StreamSGD.workingItem[i]) ++count;
				}
				StreamSGD.debugOut.println(" " + count);
				StreamSGD.debugOut.println();
			}
			

			update(userBlockId, itemBlockId, StreamSGD.blocks[userBlockId][itemBlockId]);
			synchronized(StreamSGD.workingUser){
				StreamSGD.workingUser[userBlockId] = false;
				StreamSGD.workingItem[itemBlockId] = false;
			}
			

			//check is finished
			if(RatingsSource.endOfData
					&& StreamSGD.latestExample[userBlockId][itemBlockId] != null
					&& StreamSGD.latestExample[userBlockId][itemBlockId].numTrainingIters >= config.maxTrainingIters)
			{
				RatingsSource.unfinishedBlock--;
				if(RatingsSource.unfinishedBlock <= 0)
					return;
			}
		}
	}
	
	private void update(int userBlockId, int itemBlockId, WorkingBlock workingBlock) {
		
		if(workingBlock.examples.size() == 0) return;
		TrainingExample[] examples;
		synchronized(workingBlock){
			examples = workingBlock.examples.toArray(new TrainingExample[workingBlock.examples.size()]);
			//PermutationUtils.permute(examples);
		}
		
		for (TrainingExample ex : examples) {
			if (ex.numTrainingIters >= config.maxTrainingIters) continue;
			int i = ex.userId;
			int j = ex.itemId;
			
			float dotProduct = 0.0f;
			for (int k = 0; k < config.numLatent; ++k) {
				dotProduct += StreamSGD.userVector[i][k] * StreamSGD.itemVector[j][k];
			}
			float ratingDiff = dotProduct - ex.rating;
			
			++ex.numTrainingIters;
			float stepSize = 2 * config.initialStepSize / ex.numTrainingIters; 
			
			for (int k = 0; k < config.numLatent; ++k) {
				float oldUserWeight = StreamSGD.userVector[i][k];
				float oldItemWeight = StreamSGD.itemVector[j][k];
				if(oldUserWeight < 0) oldUserWeight = 0.1f;
				if(oldUserWeight > 1) oldUserWeight = 0.9f;
				if(oldItemWeight < 0) oldItemWeight = 0.1f;
				if(oldItemWeight > 1) oldItemWeight = 0.9f;
				StreamSGD.userVector[i][k] -= stepSize*(ratingDiff * oldItemWeight + config.userPenalty * oldUserWeight);
				StreamSGD.itemVector[j][k] -= stepSize*(ratingDiff * oldUserWeight + config.itemPenalty * oldItemWeight);
			}
		}
	}
}