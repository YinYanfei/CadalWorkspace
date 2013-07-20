package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import cn.cadal.rec.common.Book;

public final class RecTest_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html>\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t<title>CADAL Recommendation Demo!</title>\r\n");
      out.write("\r\n");
      out.write("\t\t<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("\t\t<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n");
      out.write("\t\t<meta http-equiv=\"expires\" content=\"0\">\r\n");
      out.write("\t\t<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">\r\n");
      out.write("\t\t<meta http-equiv=\"description\" content=\"This is my page\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t<style type=text/css media=all>\r\n");
      out.write(".lay {solid #FF6699;\r\n");
      out.write("\twidth: 180px;\r\n");
      out.write("\tfloat: left\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("\t</head>\r\n");
      out.write("\r\n");
      out.write("\t<body>\r\n");
      out.write("\t\t<div>\r\n");
      out.write("\t\t\t<div>\r\n");
      out.write("\t\t\t\t<table valign=\"top\" border=\"0\" align=center cellspacing=\"0\"\r\n");
      out.write("\t\t\t\t\tcellpadding=\"0\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t<form name=\"infoNeeded\" action=\"UniqAlgoServ\" method=\"post\">\r\n");
      out.write("\t\t\t\t\t\t\t\t所用算法：\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" name=\"algoType\"></input>\r\n");
      out.write("\t\t\t\t\t\t\t\t原始信息：\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" name=\"id\"></input>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"submit\" value=\"提交\"></input>\r\n");
      out.write("\t\t\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t<hr width=\"100%\" noshade color=\"#C0C0C0\" size=\"1\">\r\n");
      out.write("\t\t");

			//输出用户提交的书号对应的书本的信息
			List<Book> resultBookInfoList = null;
			String bookInfo = null;
			if ((request.getAttribute("resBookInfo")) != null) {
				resultBookInfoList = (List) request.getAttribute("resBookInfo");
			}
		
      out.write("\r\n");
      out.write("\t\t<div align=\"center\">\r\n");
      out.write("\t\t\t<div id=\"recommend-result-container\">\r\n");
      out.write("\t\t\t\t");

					if ((resultBookInfoList != null)
							&& (resultBookInfoList.size() != 0)) {
						int count = 0;
						for (Book book : resultBookInfoList) {
							count++;
							String bookNo = book.getBookNo();
							String bookName = book.getBookName();
							String press = book.getPress();
							String author = book.getAuthor();
				
      out.write("\r\n");
      out.write("\t\t\t\t<div style=\"height: 250px\" align=\"center\" class=\"lay\">\r\n");
      out.write("\t\t\t\t\t<div id=\"bookimg\">\r\n");
      out.write("\t\t\t\t\t\t<a href=\"http://www.cadal.zju.edu.cn/book/");
      out.print(bookNo);
      out.write("\"> <img\r\n");
      out.write("\t\t\t\t\t\t\t\twidth=\"100\" height=\"140\" title=\"");
      out.print(bookName);
      out.write("\"\r\n");
      out.write("\t\t\t\t\t\t\t\tsrc=\"http://www.cadal.zju.edu.cn/cover/");
      out.print(bookNo);
      out.write("\"> </a>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"bookinfo\">\r\n");
      out.write("\t\t\t\t\t\t<span id=\"bookName\">");
      out.print(bookName);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t<br>\r\n");
      out.write("\t\t\t\t\t\t<span id=\"author\">");
      out.print(author);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t<br>\r\n");
      out.write("\t\t\t\t\t\t<span id=\"press\">");
      out.print(press);
      out.write("</span>\r\n");
      out.write("\t\t\t\t\t\t<br>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t");

					if (count == 8) {
						count = 0;
				
      out.write("\r\n");
      out.write("\t\t\t\t<hr width=\"100%\" noshade color=\"#C0C0C0\" size=\"1\">\r\n");
      out.write("\t\t\t\t");

					}
					}
					resultBookInfoList.clear();
				} else{
					out.println("");
				}
				
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
