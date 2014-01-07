package cn.edu.zju.cadal.zooz.test.sgd;

public class TrainingExample {
	// Not a system timestamp; just a sequence number. Using type int to save memory; as a consequence,
	// cannot handle more than 2^31 training examples.
	public final int timestamp;
	public final int userId, itemId;
	public final float rating;
	public int numTrainingIters = 0;
	
	public TrainingExample(int timestamp, int userId, int itemId, float rating) {
		this.timestamp = timestamp;
		this.userId = userId;
		this.itemId = itemId;
		this.rating = rating;
	}
	
	public String toString() {
		return "(<" + timestamp + ">," + userId + "," + itemId + "," + rating + "," + numTrainingIters + ")";
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof TrainingExample) {
			TrainingExample ex = (TrainingExample)obj;
			return this.userId == ex.userId && this.itemId == ex.itemId;
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		int userHi = userId >>> 16;
		int userLo = userId & 0xFFFF;
		int itemHi = itemId >>> 16;
		int itemLo = itemId & 0xFFFF;
		
		return ((userLo ^ itemHi) << 16) | (userHi ^ itemLo);
	}
}