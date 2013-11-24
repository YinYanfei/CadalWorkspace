package cn.cadal.rec.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class TagsUniq {

	private String tagSource = "E:/Recommendation/Tags/anaTags_op.dat";
	
	/**
	 * Constructor functions
	 */
	public TagsUniq(){
	}
	public TagsUniq(String tagSource){
		this.tagSource = tagSource;
	}
	
	public void ExtractTags(String dest){
		File file = new File(this.tagSource);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			List<String> tagList = new ArrayList<String>();
			String line = null;
			while((line = reader.readLine()) != null) {
				String [] lineSplit = line.split(" ");
				tagList.add(lineSplit[0]);
			}
			reader.close();
			
			FileWriter writer = new FileWriter(dest);
			for(int i = 0; i < tagList.size(); ++i) {
				writer.write(tagList.get(i) + "\n");
			}
			writer.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(reader != null){
					reader.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test
		TagsUniq tu = new TagsUniq();
		String dest = "E:/Recommendation/Tags/tagUniq.dat";
		
		tu.ExtractTags(dest);
		
	}

}