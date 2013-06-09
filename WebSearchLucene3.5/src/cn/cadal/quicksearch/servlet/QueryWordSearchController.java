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
import cn.cadal.quicksearch.search.SearchResult;

public class QueryWordSearchController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("queryWord");
		
		System.out.println(id);
		
		try{
			ConsoleSearch conSearch = new ConsoleSearch();
			conSearch.getIndexSearch().readFileArrQueryIndex();
			String queryWord = conSearch.getIndexSearch().getArrQueryIndex().get(Integer.valueOf(id) - 3);
			
			System.out.println(queryWord);
			
			List queryResult = new ArrayList();		// 存放查询结果
			String timeUse = "";
			
			// 查询结果的处理
			long startTime = new Date().getTime();
			conSearch.readMatrix();
			conSearch.topicGet();
			conSearch.search(queryWord);
			queryResult = conSearch.getResultSearch();
			long endTime = new Date().getTime();
			timeUse = String.valueOf(endTime - startTime);
			
			// 返回结果
			RequestDispatcher dispatcher = request.getRequestDispatcher("querysearch.jsp");
			request.setAttribute("queryResult", queryResult);
			request.setAttribute("timeUse", timeUse);
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
	 * 
	 */
/*
	public void fun(String str) {
		String searchWord = str;//request.getParameter("queryWord");
		
		try{
			ConsoleSearch conSearch = new ConsoleSearch();
			
			List queryResult = new ArrayList();		// 存放查询结果
			String timeUse = "";
			
			// 查询结果的处理
			long startTime = new Date().getTime();
			conSearch.readMatrix();
			conSearch.topicGet();
			conSearch.search(searchWord);
			queryResult = conSearch.getResultSearch();
			long endTime = new Date().getTime();
			timeUse = String.valueOf(endTime - startTime);
			
			// 打印结果
			
			for(int i = 0; i < queryResult.size(); ++i) {
				SearchResult tmp = (SearchResult) queryResult.get(i);
				System.out.println(tmp.getBookNo() + "  " + tmp.getPress() + "  " 
								   + tmp.getQueryWord() + "   " + tmp.getTitle());
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	

	}
	public static void main(String[] args) {
		QueryWordSearchController con = new QueryWordSearchController();
		con.fun("linux");
	}
*/
}
