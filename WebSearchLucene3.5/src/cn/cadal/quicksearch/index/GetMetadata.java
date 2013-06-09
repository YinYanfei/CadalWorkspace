package cn.cadal.quicksearch.index;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import org.hibernate.Session;


import cn.cadal.entity.Cbook;
import cn.cadal.entity.CjournalMetadata;
import cn.cadal.quicksearch.QuickSearchConfig;


import common.utils.HibernateUtil;

public class GetMetadata {
	
	public static void main (String[] args){

		long startTime = new Date().getTime();
		
		final String[] types = {"ancient", "dissertation", "english", "journal", "minguo", "modern"};
		final String[] tables = {"Cancient", "Cdissertation", "Cenglish", "CjournalMetadata", "Cminguo", "Cmodern"};
		
		//final String[] types = {"journal"};
		//final String[] tables = {"CjournalMetadata"};
		
		
		int total = 0;
		for(int i=0; i<types.length; i++)
		{
			int count = 0;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Iterator iter = session.createQuery("from " + tables[i]).iterate();
	
			BufferedWriter output = null;
			try {
				File f = new File(QuickSearchConfig.getTxtDir() + "/" + types[i] + ".txt");
				if(f.exists())
					f.delete();
				f.createNewFile();
				output = new BufferedWriter(new FileWriter(f));
			
				while(iter.hasNext()){
					count++;
					
					if(types[i].equalsIgnoreCase("journal"))
					{
						CjournalMetadata cb = (CjournalMetadata)iter.next();
						output.write(cb.getBookNo().toString() + " #### " + cb.getTitle().replaceAll("\n", " | ") + " #### " + cb.getCreator().replaceAll("\n", " | ") + " #### " + cb.getSubject().replaceAll("\n", " | ") + " #### " + cb.getPublisher().replaceAll("\n", " | ") + "\n");
					}
					else
					{
						Cbook cb = (Cbook)iter.next();
						output.write(cb.getBookNo().replaceAll("\n", " | ") + " #### " + cb.getTitle().replaceAll("\n", " | ") + " #### " + cb.getCreator().replaceAll("\n", " | ") + " #### " + cb.getSubject().replaceAll("\n", " | ") + " #### " + cb.getPublisher().replaceAll("\n", " | ") + "\n");
					}
				}
				session.getTransaction().commit();
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(types[i] + "\t\t\t: " + count);
			total += count;
		}
		System.out.println("total" + "\t\t\t: " + total);
		
		long endTime = new Date().getTime();
		System.out.println("Time costs: " + (endTime - startTime)/1000 + "s");
	}
}
