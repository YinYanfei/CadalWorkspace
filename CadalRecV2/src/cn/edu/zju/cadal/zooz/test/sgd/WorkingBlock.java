package cn.edu.zju.cadal.zooz.test.sgd;

import java.util.HashSet;
import java.util.Set;

public class WorkingBlock{
	public final Set<TrainingExample> examples = new HashSet<TrainingExample>();
	
	public String toString() {
		StringBuilder b = new StringBuilder(24*examples.size() + 32);
		
		b.append("examples={");
		boolean first = true;
		for (TrainingExample ex : examples) {
			if (first) {
				first = false;
			} else {
				b.append(", ");
			}
			b.append(ex.toString());
		}
		b.append('}');
		return b.toString();
	}
	
	public TrainingExample getLatestExample() {
		TrainingExample latest = null;
		for (TrainingExample ex : examples) {
			if (latest == null || latest.timestamp < ex.timestamp) {
				latest = ex;
			}
		}
		return latest;
	}
}