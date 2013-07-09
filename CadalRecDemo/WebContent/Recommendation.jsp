<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page
	import = "cn.cadal.rec.common.Book"
	import = "java.util.List"
	import = "java.util.ArrayList"
	import = "cn.cadal.rec.itemcf.RecommendItemCF"
%>
<%
	request.setCharacterEncoding("utf-8");
	List<Book> recList = new ArrayList<Book>();
	RecommendItemCF rec = new RecommendItemCF();
	String booknumber = "";
	String bkName = "";
	String bkPress = "";
	String bkAuthor = "";
	if((booknumber = request.getParameter("bookNo") )!= null){
	//得到推荐列表
		String algorithm = request.getParameter("radiobutton");
		List<String> bookInfo = Book.getBookInfo(booknumber);
		if(bookInfo!=null){
	bkName = bookInfo.get(1);
	bkPress = bookInfo.get(2);
	bkAuthor = bookInfo.get(3);
	recList = rec.getRecommendation(booknumber);
		}
		else
	recList = null;
		
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CADAL Recommendation</title>

<style type=text/css media=all>
.lay {solid #FF6699;
	width: 180px;
	float: left
}
</style>
</head>
<script type="text/javascript">
	function getRecommendation() {
		if (form1.bookNo.value == "") {
			alert("请输入书号丫！");
			return;
		}
		form1.submit();
	}
</script>

<body>
	<p align="center"><B>CADAL RecommendationDemo</B></p>
	<hr width="100%" noshade color="#C0C0C0" size="3">
	<form name="form1" action="Recommendation.jsp" method="post">
		请输入书号：<input type="text" name="bookNo"></input> <input type="radio"
			name="radiobutton" value="algorithm1" checked> ItemCF <input
			type="radio" name="radiobutton" value="algorithm2"> 算法2 <input
			type="radio" name="radiobutton" value="algorithm3"> 算法3
		<input type="button" value="推荐" onclick="getRecommendation()" />
	</form>

	<hr width="100%" noshade color="#C0C0C0" size="1">
	<%
		//输出用户提交的书号对应的书本的信息
		if ((booknumber = request.getParameter("bookNo")) != null) {
			out.println("书号：<B>" + booknumber + "</B> || 书名：<B>" + bkName + "</B> || 作者：<B>"
					+ bkAuthor + "</B> || 出版社：<B>" + bkPress + "</B>");
			out.println("||推荐列表如下：");
		} else
			out.println("　");
	%>
	<div align="center" >
		<hr width="100%" noshade color="#C0C0C0" size="1">

		<div id="recommend-result-container">
			<%
				if ((recList != null) && (recList.size() != 0)) {
					for (int i = 0; i < recList.size(); ++i) {
						Book book = (Book) recList.get(i);
						String bookNo = book.getBookNo();
						String bookName = book.getBookName();
						String press = book.getPress();
						String author = book.getAuthor();
						if (author == null || author == "") {
							author = "暂无作者信息";
						}
						if (press == null || press == "") {
							press = "暂无出版社信息";
						}
			%>

				<div style="height: 250px" align="center" class="lay">
					<div id="bookimg">
						<a href="http://www.cadal.zju.edu.cn/book/<%=bookNo%>"> <img
							width="100" height="140" title="<%=bookName%>"
							src="http://www.cadal.zju.edu.cn/cover/<%=bookNo%>">
						</a>
					</div>
					<div id="bookinfo">
						<span id="bookName" align="left"><%=bookName%></span><br> <span
							id="author" align="left"><%=author%></span><br> <span
							id="press" align="left"><%=press%></span><br>
					</div>
				</div>
			<%
				}
					recList.clear();
				} else
					out.println(" 没有相关记录！");
			%>
		</div>
	</div>

</body>
</html>