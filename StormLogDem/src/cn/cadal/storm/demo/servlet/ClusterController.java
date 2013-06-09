package cn.cadal.storm.demo.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cadal.storm.demo.deepOp.QueryBCPRelation;
import cn.cadal.storm.demo.deepOp.QueryPgCbook;
import cn.cadal.storm.demo.readRecFile.ReadRecFile;

public class ClusterController extends HttpServlet {
	
	static private final Log LOG = LogFactory.getLog(ReadRecFile.class);
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String cluster = "";
		String resultNum = "";
		
		try {
			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			
			// Receive parameters
			cluster = request.getParameter("cluster");
			resultNum = request.getParameter("resultNum");
			
			System.out.println("cluster: " + cluster + ", " + "resultNum" + resultNum);
			
			// Deal
			ReadRecFile rrf = new ReadRecFile();
			QueryBCPRelation qbr = new QueryBCPRelation();
			QueryPgCbook qpc = new QueryPgCbook();
			
			rrf.ProcessReadFile(cluster);
			rrf.ProcessGetResult(Integer.valueOf(resultNum).intValue());
			
			qbr.QueryFromBCPRelation(rrf.ReadResult);
			List<String> bookTitleList = qpc.GetBookTitle(rrf.ReadResult);
			
			// Deliver result
			RequestDispatcher dispatcher = request.getRequestDispatcher("indexs.jsp");
			request.setAttribute("finalResult", qbr.finalResult);		// <<"07018720", "chapter 1", "chapter 2", "chapter 3">,<"07018721", "chapter 1">,...>
			request.setAttribute("bookTitle", bookTitleList);			// <"BookTitle1", "BookTitle2">
			dispatcher.forward(request, response);

		} catch (UnsupportedEncodingException e1) {
			LOG.warn("cluster: " + cluster + ", " + "resultNum" + resultNum);
			e1.printStackTrace();
		} catch (ServletException e) {
			LOG.warn("Deliver result: " + cluster);
			e.printStackTrace();
		} catch (IOException e) {
			LOG.warn("Deliver result: " + cluster);
			e.printStackTrace();
		}
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String cluster = "2";
		String resultNum = "10";
		
		// Test
		ReadRecFile rrf = new ReadRecFile();
		QueryBCPRelation qbr = new QueryBCPRelation();
		QueryPgCbook qpc = new QueryPgCbook();
		
		rrf.ProcessReadFile(cluster);
		rrf.ProcessGetResult(Integer.valueOf(resultNum).intValue());
		
		qbr.QueryFromBCPRelation(rrf.ReadResult);
		List<String> bookTitleList = qpc.GetBookTitle(rrf.ReadResult);
		
		System.out.println(qbr.finalResult.size());
		for (int i = 0; i < qbr.finalResult.size(); ++i) {
			System.out.println(bookTitleList.get(i));
			System.out.println(qbr.finalResult.get(i));
		}

	}

}
