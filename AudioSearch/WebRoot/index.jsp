<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.cadal.audio.search.Search"%>
<%@page import="cn.cadal.audio.entity.SearchResult"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>
		<%
			String name = request.getParameter("searchWord");
			Search search = new Search();
			Boolean resultBoolean = search.Searcher(name);
			List<SearchResult> searchResultList;
			if (resultBoolean) {
				searchResultList = search.getSearchResultList();
			} else {
				searchResultList = null;
			}
		%>
		<table>
			<tr>
				<%
					if (searchResultList != null) {
						for (int i = 0; i < searchResultList.size(); i++) {
				%>
				<td><%=searchResultList.get(i).getTitle()%>
				</td>
				<td><%=searchResultList.get(i).getAutioNO()%>
				</td>

				<td><%=searchResultList.get(i).getPublisher()%>
				</td>
				<td><%=searchResultList.get(i).getCreator()%>
				</td>
				<%
					}
					} else {
				%>
				<td>
					没有信息
				</td>
				<%
					}
				%>
			</tr>

		</table>
	</body>
</html>
