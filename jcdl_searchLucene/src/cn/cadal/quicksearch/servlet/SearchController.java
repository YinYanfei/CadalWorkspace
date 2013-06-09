package cn.cadal.quicksearch.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cadal.quicksearch.search.ConsoleSearch;
import cn.cadal.quicksearch.search.IndexSearch;
import cn.cadal.quicksearch.search.SearchResult;
import cn.cadal.quicksearch.search.TopicSearchResult;

public class SearchController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		String searchWord = request.getParameter("searchWord");
		try {
			byte[] b = searchWord.getBytes("ISO-8859-1");
			searchWord = new String(b);
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}

		String topic1 = request.getParameter("topic1");
		String topic2 = request.getParameter("topic2");
		String topic3 = request.getParameter("topic3");
		
		try{
			ConsoleSearch conSearch = new ConsoleSearch();
			IndexSearch indexSearch = new IndexSearch();
			
			List searchResult = new ArrayList();		// 最底层的查询结果
			List topTopic = new ArrayList();			// 中间层的TopicID
			List topTopicName = new ArrayList();		// 中间层的TopicName
			List indexResult1 = new ArrayList();		// Topic-1对应的类别
			List indexResult2 = new ArrayList();		// Topic-2对应的类别
			List indexResult3 = new ArrayList();		// Topic-3对应的类别
			String timeUse = "";						// 本次查询所用的时间
			
			// 查询结果的处理
			long startTime = new Date().getTime();
			conSearch.readMatrix();
			conSearch.topicGet();
			conSearch.search(searchWord);
			searchResult = conSearch.getResultSearch();
			long endTime = new Date().getTime();
			timeUse = String.valueOf(endTime - startTime);
			
			
			System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[" + topic1);
			System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[" + topic2);
			System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[" + topic3);
			// Topic的处理
			conSearch.dealMatrix();
			conSearch.topTopicGet();
			if(topic1 == null || topic1.equalsIgnoreCase("")){
				for(int i = 0; i < 3; ++i) {
					topTopic.add(conSearch.getTopTopic()[i]);			// TopicID存放
					topTopicName.add(conSearch.getTopTopicName()[i]);	// TopicName
				}
			}else{				
				int []intArr = new int[3];
				intArr[0] = Integer.valueOf(topic1) - 1;
				intArr[1] = Integer.valueOf(topic2) - 1;
				intArr[2] = Integer.valueOf(topic3) - 1;
				
				for(int i = 0; i < 3; ++i) {
					topTopic.add(intArr[i]);
					topTopicName.add(conSearch.getIndexSearch().getArrTopic().get(intArr[i]));
					conSearch.setTopTopic(intArr);
				}
			}
			
			for(int i = 0; i < 3; i++) {
				System.out.println(conSearch.getTopTopic()[i]);
			}
			
			// 类别的处理
			indexSearch.readFileArrQueryIndex();
			indexSearch.readFileArrTopic();
			indexSearch.search(conSearch.getTopTopic()[0]);
			for(int j = 0; j < indexSearch.countTmp; ++j) {
				indexResult1.add(indexSearch.getListResult()[j]);
			}
			indexSearch.search(conSearch.getTopTopic()[1]);
			for(int j = 0; j < indexSearch.countTmp; ++j) {
				indexResult2.add(indexSearch.getListResult()[j]);
			}
			indexSearch.search(conSearch.getTopTopic()[2]);
			for(int j = 0; j < indexSearch.countTmp; ++j) {
				indexResult3.add(indexSearch.getListResult()[j]);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("indexs.jsp");
			request.setAttribute("searchResult",searchResult);
			request.setAttribute("topTopic", topTopic);
			request.setAttribute("indexResult1", indexResult1);
			request.setAttribute("indexResult2", indexResult2);
			request.setAttribute("indexResult3", indexResult3);
			request.setAttribute("timeUse", timeUse);
			request.setAttribute("returnSearchWord", searchWord);
			request.setAttribute("topTopicName", topTopicName);
			dispatcher.forward(request, response);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException{
    	doPost(request, response);
    }   

    /**
     * Test Function
     */
    /*
    public boolean testFun(String str) {
    	boolean signal = false;
		String searchWord = str;
		
		try{
			ConsoleSearch conSearch = new ConsoleSearch();
			IndexSearch indexSearch = new IndexSearch();
			
			List searchResult = new ArrayList();			// 最底层的查询结果
			List topTopic = new ArrayList();			// 中间层的TopicID
			List topTopicName = new ArrayList();		// 中间层的TopicName
			List indexResult1 = new ArrayList();		// Topic-1对应的类别
			List indexResult2 = new ArrayList();		// Topic-2对应的类别
			List indexResult3 = new ArrayList();		// Topic-3对应的类别
			String timeUse = "";						// 本次查询所用的时间
			
			// 查询结果的处理
			long startTime = new Date().getTime();
			conSearch.readMatrix();
			conSearch.topicGet();
			conSearch.search(searchWord);
			searchResult = conSearch.getResultSearch();
			long endTime = new Date().getTime();
			timeUse = String.valueOf(endTime - startTime);
			
			// Topic的处理
			conSearch.dealMatrix();
			conSearch.topTopicGet();
			for(int i = 0; i < 3; ++i) {
				topTopic.add(conSearch.getTopTopic()[i]);			// TopicID存放
				topTopicName.add(conSearch.getTopTopicName()[i]);	// TopicName
			}
			
			// 类别的处理
			indexSearch.readFileArrQueryIndex();
			indexSearch.readFileArrTopic();
			indexSearch.search(conSearch.getTopTopic()[0]);
			for(int j = 0; j < indexSearch.countTmp; ++j) {
				indexResult1.add(indexSearch.getListResult()[j]);
			}
			indexSearch.search(conSearch.getTopTopic()[1]);
			for(int j = 0; j < indexSearch.countTmp; ++j) {
				indexResult2.add(indexSearch.getListResult()[j]);
			}
			indexSearch.search(conSearch.getTopTopic()[2]);
			for(int j = 0; j < indexSearch.countTmp; ++j) {
				indexResult3.add(indexSearch.getListResult()[j]);
			}
			
			//
			System.out.println("-----------------searchResult-----------------" + searchResult.size());
			for(int i = 0; i < 100; ++i) {
				SearchResult tmp = (SearchResult) searchResult.get(i);
				System.out.println(tmp.getCreator());
			}
			System.out.println("--------------------------------------------------------");
			for(int i = 0; i < 5; i++) {
				TopicSearchResult tmp = new TopicSearchResult();
				tmp = (TopicSearchResult) indexResult3.get(i);
				System.out.println(tmp.getID() + "  " + tmp.getQueryWord() + "   " + tmp.getTimes());
			}
			System.out.println("------------------------------------------------");
			for(int i = 0; i < 3; i++) {
				System.out.println(topTopic.get(i).toString() + "   " + topTopicName.get(i));
			}
			//
			
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
    }
	public static void main(String[] args) {
		// Test
		SearchController control = new SearchController();
		
		control.testFun("春秋");

	}
	*/
}
