package cn.cadal.rec.usercf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.cadal.rec.common.Book;

/**
 * 
 * @author hongxin
 * 
 */
public class RecommendationUserCF {

	private String userid_userList_file = "D:\\CADAL\\Recommendation\\common\\usercf_result_total_pri_op.res";
	private String userid_bookList_file = "D:\\CADAL\\Recommendation\\common\\accesslog_user_bookid_uniq_userno_bookno.dat";

	public HashMap<Integer, List<Integer>> useridUseridListMap;
	public HashMap<Integer, List<Integer>> useridBookidListMap;

	public boolean ReadMapFile() {
		return (this.ReadUseridBookListMap() && this
				.ReadUseridUseridSimListMap());
	}

	/**
	 * 从文件中读取与每个用户最相似的最多10个用户
	 * 
	 * @return
	 */
	private boolean ReadUseridUseridSimListMap() {
		File file = new File(this.userid_userList_file);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(" ");
				Integer key = Integer.parseInt(temp[0]);
				List<Integer> userList = new ArrayList<Integer>();
				for (int i = 1; i < temp.length; i = i + 2) {
					double sim = Double.parseDouble(temp[i + 1]);
					if ((int) sim == 1) {
						continue;
					} else {
						int userid = Integer.parseInt(temp[i]);
						userList.add(userid);
					}
				}
				this.useridUseridListMap.put(key, userList);
			}
			System.out.println("Finish Reading userid_userList_file");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 从文件读取用户读过的书的列表
	 * 
	 * @return
	 */
	private boolean ReadUseridBookListMap() {
		File file = new File(this.userid_bookList_file);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] lineSplit = line.split(" ");

				Integer key = Integer.parseInt(lineSplit[0]); // userid
				List<Integer> bookList = new ArrayList<Integer>();

				for (int i = 1; i < lineSplit.length; ++i) {
					bookList.add(Integer.parseInt(lineSplit[i]));
				}
				this.useridBookidListMap.put(key, bookList);
			}
			System.out.println("Finish Reading userid_bookList_file");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public RecommendationUserCF() {
		this.useridBookidListMap = new HashMap<Integer, List<Integer>>();
		this.useridUseridListMap = new HashMap<Integer, List<Integer>>();
	}

	public RecommendationUserCF(String userid_userList_file,
			String userid_bookList_file) {
		this.userid_userList_file = userid_userList_file;
		this.userid_bookList_file = userid_bookList_file;
		this.useridBookidListMap = new HashMap<Integer, List<Integer>>();
		this.useridUseridListMap = new HashMap<Integer, List<Integer>>();
	}

	/**
	 * 为用户id为userid的用户推荐图书
	 * 
	 * @param userid
	 * @return 一个图书列表
	 */
	public List<Book> getRecommendation(int userid) {
		List<Book> res = new ArrayList<Book>();
		List<Integer> userList = new ArrayList<Integer>();
		userList = this.useridUseridListMap.get(userid);
		if (userList.size()>0) {
			List<Integer> bookList = new ArrayList<Integer>();
			for (int i = 0; i < userList.size(); ++i) {
				bookList.clear();
				bookList = this.useridBookidListMap.get(userList.get(i));
				for (int j = 0; j < bookList.size(); ++j) {
					String bkNo = Book.getBookNobyId(bookList.get(j));
					List<String> info = Book.getBookInfo(bkNo);
					if(info==null){
						System.out.println("不存在这本书，转化后的id为： " + bookList.get(j));
						continue;
					}
					String bkName = info.get(1);
					String bkPress = info.get(2);
					String bkAuthor = info.get(3);
//					System.out.println(info.get(0) + " " + info.get(1) +  " " + info.get(2) + " " +  info.get(3));
					Book book = new Book(bkNo, bkName, bkPress, bkAuthor);
					res.add(book);
				}
			}
		}
		try {
			Book.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// for test
		System.out.println("+++++++++++++++");
		RecommendationUserCF rec = new RecommendationUserCF();
//		rec.ReadMapFile();
//		List<Integer> list = rec.useridUseridListMap.get(16);
//		
//		for(int i : list)
//			System.out.println(i);
		
		rec.ReadMapFile();
		List<Book> list = rec.getRecommendation(16);
		if (list != null) {
			System.out.println(list.size());
			for (int i = 0; i < list.size(); ++i) {
				System.out.println("----");
				Book book = list.get(i);
				System.out.println(book.getBookNo() + " " + book.getBookName()
						+ " " + book.getAuthor() + " " + book.getPress());
			}
		}
	}
}
