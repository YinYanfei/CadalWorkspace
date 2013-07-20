<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="cn.cadal.rec.common.Book"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>CADAL Recommendation Demo!</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">


		<style type=text/css media=all>
.lay {solid #FF6699;
	width: 180px;
	float: left
}
</style>

	</head>

	<body>
		<div>
			<div>
				<table border="0" align=center cellspacing="0"
					cellpadding="0">
					<tr>
						<td>
							<form name="infoNeeded" action="UniqAlgoServ" method="post">
								选择算法：
								<select name="algoType" id="algoType">  
        							<option value="ItemCF">ItemCF</option>  
      								<option value="UserCF">UserCF</option>  
        							<option value="Tags">Tags</option>  
        							<option value="FreqItem">FreqItem</option> 
        							<option value="ColdStart">ColdStart</option>         
      							</select> 
								原始信息：
								<input type="text" name="id"></input>
								<input type="submit" value="提交"></input>
							</form>
						</td>
					</tr>
				</table>
			</div>
		</div>

		<hr width="100%" noshade color="#C0C0C0" size="1">
		<%
			if(request.getAttribute("singleBookInfo")!=null){
				Book book = (Book)request.getAttribute("singleBookInfo");
				out.println("书号：<B>" + book.getBookNo() + "</B> || 书名：<B>" + book.getBookName() + "</B> || 作者：<B>"
						+ book.getAuthor() + "</B> || 出版社：<B>" + book.getPress() + "</B>");
				out.println("</br></br>");
				out.println("推荐列表如下：");
			}
		%>
		<hr width="100%" noshade color="#C0C0C0" size="1">
		<%
			//输出用户提交的书号对应的书本的信息
			List<Book> resultBookInfoList = null;
			String bookInfo = null;
			if ((request.getAttribute("resBookInfo")) != null) {
				resultBookInfoList = (List) request.getAttribute("resBookInfo");
			}
		%>
		<div align="center">
			<div align="center" id="recommend-result-container">
				<%
					if ((resultBookInfoList != null)
							&& (resultBookInfoList.size() != 0)) {
						int count = 0;
						for (Book book : resultBookInfoList) {
							count++;
							String bookNo = book.getBookNo();
							String bookName = book.getBookName();
							String press = book.getPress();
							String author = book.getAuthor();
				%>
				<div style="height: 250px" align="center" class="lay">
					<div id="bookimg">
						<a href="http://www.cadal.zju.edu.cn/book/<%=bookNo%>"> <img
								width="100" height="140" title="<%=bookName%>"
								src="http://www.cadal.zju.edu.cn/cover/<%=bookNo%>"> </a>
					</div>
					<div id="bookinfo">
						<span id="bookName"><%=bookName%></span>
						<br>
						<span id="author"><%=author%></span>
						<br>
						<span id="press"><%=press%></span>
						<br>
					</div>
				</div>
				<%
					if (count == 6) {
						count = 0;
				%>
				<hr width="100%" noshade color="#C0C0C0" size="1">
				<%
					}
					}
					resultBookInfoList.clear();
				} else{
					out.println("");
				}
				%>
			</div>
		</div>
	</body>
</html>
