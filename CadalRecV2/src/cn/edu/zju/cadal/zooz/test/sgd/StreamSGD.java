package cn.edu.zju.cadal.zooz.test.sgd;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;

public class StreamSGD {
	public static WorkingBlock[][] blocks;
	public static float[][] userVector;
	public static float[][] itemVector;
	public static boolean[] workingUser;
	public static boolean[] workingItem;
	public static PrintWriter debugOut;
	public static TrainingExample[][] latestExample;
	
	private static PrintWriter userOutput, itemOutput;
	private static Random random = new Random();
	
	public static void main(String[] args) throws Exception {
		if (args.length < 6) {
			System.err.println("######## Wrong number of arguments");
			System.err.println("######## required args: local|production numUsers numItems"
							   + " inputFilename userOutputFilename itemOutputFilename");
			return;
		}
		
		Properties props = new Properties();  //���Լ�����ֵ�ԣ�
		File propFile = new File("data/collabstream.properties");
		if (propFile.exists()) { //��������ļ��Ƿ����
			FileReader in = new FileReader(propFile);
			props.load(in); //��file�ж�ȡ����
			in.close();
		}
		//���ò���
		int numUsers = Integer.parseInt(args[1]); //�û�����
		int numItems = Integer.parseInt(args[2]);  //��Ʒ����
		int numLatent = Integer.parseInt(props.getProperty("numLatent", "10")); //Ĭ��10��������
		int numUserBlocks = Integer.parseInt(props.getProperty("numUserBlocks", "10"));
		int numItemBlocks = Integer.parseInt(props.getProperty("numItemBlocks", "10"));
		float userPenalty = Float.parseFloat(props.getProperty("userPenalty", "0.1"));
		float itemPenalty = Float.parseFloat(props.getProperty("itemPenalty", "0.1"));
		float initialStepSize = Float.parseFloat(props.getProperty("initialStepSize", "0.1"));//SGD��������
		int maxTrainingIters = Integer.parseInt(props.getProperty("maxTrainingIters", "30"));//ÿ��R(i,j)����30��
		String inputFilename = args[3];
		String userOutputFilename = args[4];
		String itemOutputFilename = args[5];
		long inputDelay = Long.parseLong(props.getProperty("inputDelay", "0"));
		long processDelay = Long.parseLong(props.getProperty("processDelay", "0"));
		boolean debug = true;//Boolean.parseBoolean(props.getProperty("debug", "true"));
		
		Configuration config = new Configuration(numUsers, numItems, numLatent, numUserBlocks, numItemBlocks,
				 userPenalty, itemPenalty, initialStepSize, maxTrainingIters,
				 inputFilename, userOutputFilename, itemOutputFilename,
				 inputDelay, processDelay, debug); //dsgd������

		latestExample = new TrainingExample[config.numUserBlocks][config.numItemBlocks];
		blocks = new WorkingBlock[config.numUserBlocks][config.numItemBlocks];
		for(int i=0; i<config.numUserBlocks; i++)
			for(int j=0; j<config.numItemBlocks; j++)
				blocks[i][j] = new WorkingBlock();
		userVector = new float[config.numUsers][config.numLatent];
		itemVector = new float[config.numItems][config.numLatent];
		for (int i = 0; i < config.numUsers; ++i) {
			for (int j = 0; j < config.numLatent; ++j) {
				userVector[i][j] = random.nextFloat();
			}
		}
		for (int i = 0; i < config.numItems; ++i) {
			for (int j = 0; j < config.numLatent; ++j) {
				itemVector[i][j] = random.nextFloat();
			}
		}
		

		debugOut = new PrintWriter(new BufferedWriter(new FileWriter("data/debug.txt")));
		RatingsSource ratingsSource = new RatingsSource(config);
		Thread t_rating = new Thread(ratingsSource,"Ratings Thread");
		t_rating.start();
		
		workingUser = new boolean[config.numUserBlocks];
		workingItem = new boolean[config.numItemBlocks];
		Worker worker = new Worker(config);
//		Thread t_worker = new Thread(worker,"Worker Thread");
//		t_worker.start();
		Thread[] t_worker = new Thread[10];
		for(int i=0; i<config.numUserBlocks; i++)
		{
			t_worker[i] = new Thread(worker,"Woker Thread" + i);
			t_worker[i].start();
		}
		
 		t_rating.join();
//		t_worker.join();
		for(int i=0; i<config.numUserBlocks; i++)
			t_worker[i].join();
		
		//predict vector write to file
		userOutput = new PrintWriter(new BufferedWriter(new FileWriter(config.userOutputFilename)));
		itemOutput = new PrintWriter(new BufferedWriter(new FileWriter(config.itemOutputFilename)));
		for(int i=0; i<config.numUsers; i++)
		{
			userOutput.print(i);
			for(int j=0; j<config.numLatent; j++)
			{
				userOutput.print(' ');
				userOutput.print(userVector[i][j]);
			}
			userOutput.println();
		}

		for(int i=0; i<config.numItems; i++)
		{
			itemOutput.print(i);
			for(int j=0; j<config.numLatent; j++)
			{
				itemOutput.print(' ');
				itemOutput.print(itemVector[i][j]);
			}
			itemOutput.println();
		}
		
		userOutput.close();
		itemOutput.close();
		debugOut.close();
		long endTime = System.currentTimeMillis();
		System.out.printf("######## Process finished: %1$tY-%1$tb-%1$td %1$tT %tZ\n", endTime);
	}
}
