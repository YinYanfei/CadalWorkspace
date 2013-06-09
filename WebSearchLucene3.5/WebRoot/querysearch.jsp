<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="cn.cadal.quicksearch.search.TopicSearchResult" %>
<%@ page import="cn.cadal.quicksearch.search.SearchResult" %>
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN" xmlns="http://www.w3.org/1999/xhtml">
  <head>

  
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="keywords" content="数字图书馆,民国,在线,古籍"/>
      <META http-equiv="Content-Language" content="zh-CN">
      <meta name="verify-v1" content="2ZhZmdKcisUXIK8TvlKGd6EpnMWoGSyGo5w65/msJ6I=" />
	<title>高等学校中英文图书数字化国际合作计划</title>

	<SCRIPT language="javascript" src="http://www.cadal.zju.edu.cn/js/transform.js" type="text/javascript"></SCRIPT>
	<SCRIPT language="javascript" src="http://www.cadal.zju.edu.cn/js/cadal.js" type="text/javascript"></SCRIPT>
	<script src="http://www.cadal.zju.edu.cn/jquery-1.4.2.js" type="text/javascript"></script>

	<LINK media=all href="http://www.cadal.zju.edu.cn/css/cadal_layout.css" type=text/css rel=stylesheet>
	  <STYLE type=text/css media=all>
	    @import url(http://www.cadal.zju.edu.cn/css/cadal_layout.css); 
	  </STYLE>
	  <style type=text/css media=all>
	  	#portal_promo{
	  		width:980px;
	  		padding:0;
	  		clear:both;
			list-style:none outside none;
			-moz-box-shadow:0 1px 3px rgba(0, 0, 0, 0.3);
			background:none repeat scroll 0 0 #FFFFFF;
			border-color:#E5E5E5 #DBDBDB #D2D2D2;
			border-style:solid;
			border-width:1px;
			-moz-border-radius:4px 4px 4px 4px;
	  	}
		#portal_promo:nth-child(n){
			border:medium none;
		}
	  	#portal_promo li{
	  		width:245px;
	  		float:left;
	  	}
		#portal_promo li a{
			color:#252525;
			display:block;
			min-height:144px;
			padding:5px 15px 256px 20px;
			position:relative;
			text-decoration:none;
			border-left:1px solid #E5E5E5;
		}
		#portal_promo li a:active {
			url("http://www.cadal.zju.edu.cn/images/icadal-hover.png") repeat-x scroll 0 0 transparent
		}
		#portal_promo li a.roundedleft{
			border-left:medium none;
			-moz-border-radius:4px 0 0 4px;
		}
		#portal_promo li div.roundedright{
			-moz-border-radius:0 4px 4px 0;
		}
	  	#portal_promo li h3 {
	  		color:#000;
	  		font-size:18px;
	  		font-weight:600;
	  		line-height:21px;
	  	}
		#portal_promo li h3 span.grey{
			color:#888;
			font-weight:600;
			font-size:17px;
		}
	  	#portal_promo li p {
	  		color:#666;
	  		font-size:14px;
	  		line-height:18px;
	  		margin:0;
			margin-bottom:18px;
	  	}
	  	#portal_promo li img{
	  		bottom:-1px;
	  		left:0;
	  		position:absolute;
	  		display:block;
	  		border:0 none;
	  	}
		.link_class {
     		       font-size:14px;
   		       color:#333333;
		}
		.link_font {
     		       font-size:14px;
   		       color:#333333;
		}
		#left_links {
 		       float:left;
		       padding-top:10px;
           padding-left:35px;
		}
    #right_links {
 		       float:right;
		       padding-top:10px;
           
		}
		form {
 		       margin:0;        
		}
		.searchform {
					 height: 24px; 
					 padding-left: 0px; 
					 float: left; 
					 margin-top: 10px; 
					 width: 320px; 
					 margin-left: 35px;
					 background: url("http://www.cadal.zju.edu.cn/img/a3.png") no-repeat scroll 10px 50% transparent;
		}
		.searchtext {
					 float: left; 
					 border: 0pt none; 
					 width: 284px; 
					 height: 17px; 
					 margin-top: 3px; 
					 margin-left: 11px;
		}
		.clear {
				clear:both;
				font-size:0;
				height:0;
				line-height:0;
				overflow:hidden;
		}
		dl {
			margin:0;
			padding:0;
		}
                #right_message {
 		       float:right;
		       padding-top:5px;
                       padding-left:10px;
		}
   	       .banner-foot {
    		      background: url(http://www.cadal.zju.edu.cn/images/main4.png) no-repeat scroll 0% 0% transparent;
          	      height:48px;
     		      display:block;
  		      padding-top:2px;
    		      padding-right:35px;
		}
                .banner-head {
    		      background: url(http://www.cadal.zju.edu.cn/images/main3.png) no-repeat scroll 0% 0% transparent;
          	      height:87px;
     		      display:block;
  		      padding-top:0px;
    		      padding-right:35px;
		}
                
table {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

form {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
#multi-facted {
	width: 997px;
	border: 1px #c2e2f8 solid;
	margin-bottom:10px;
}
.classification {
    border-bottom: 1px dashed #D1D1D1;
    margin: 0 10px;
    padding: 7px 0 2px;
    line-height: 18px;
}
.classification span{
		color: #777;
		float:left;
		height:18px;
		text-align:right;
		width:100px;
}
div#bookimg{
		margin-left:10px;
}
#bookimg span#title{
		font-size:15px;
}
#bookinfo{
		margin-left:10px;
}
em, i {
	font-style:normal;
}
em.all{
	float:left;
	width:40px;
	background-color:#fee8da;
}
i {
	float:left;
	white-space:nowrap;
	font-style:normal;
	height:24px;
	margin-left:10px;
}
div#search-result{
	border:1px solid #C2E2F8;
	width:997px;
}
div#query-search-result{
	border:1px solid #C2E2F8;
	width:997px;
	display:none;
	
}
#resulthead{
	width:997px;
	background-image:url("http://www.cadal.zju.edu.cn/img/a1.png");
	background-repeat:repeat-x;
}

#resultHeadIn{
	font-size:12px;
	height:30px;
	color:#aa0000;
	text-align:left;
	padding-top:5px;
}

		a:link {text-decoration: none;}
		a:visited {text-decoration: none;}
		a:active {text-decoration: none;}
		a:hover {text-decoration: none;}
		span#left_links a:hover {color: red;}
		span#right_links a:hover {color: red;}
	  	</style>
	</head>
	<body align="center">
		
		<div width="100%" align="center" padding-left="1px">
<table width="997" border="0" nowrap="nowrap" cellpadding="0" cellspacing="0" align="center">
 <tr nowrap="nowrap" cellpadding="0" cellspacing="0" height="87">


    <td border="0" width="1000" nowrap="nowrap" cellpadding="0" cellspacing="0">
        <div align=left class="banner-head">
           <span id="right_message">
           <a href="IndexEng.action"><img src="http://www.cadal.zju.edu.cn/images/england.png" border="0" width="20" height="13" /></a>&nbsp;
           </span>
     </div>
    </td>
  </tr>
  <tr nowrap="nowrap" cellpadding="0" cellspacing="0">
  <td border="1" width="1000" nowrap="nowrap" cellpadding="0" cellspacing="0">
     <div align=left class="banner-foot">
     <div class="links">
     <span id="left_links">
     <a class="link_class" href="http://www.cadal.zju.edu.cn/"><font class="link_font">首页</font></a> | <a class="link_class" href="http://www.cadal.zju.edu.cn/IM/IM.htm"><font class="link_font">图像库</font></a> | <a class="link_class" href="http://www.cadal.zju.edu.cn/VideoShow.action"><font class="link_font">视频库</font></a> | <a class="link_class" href="http://www.cadal.zju.edu.cn/CalligraphyWeb/listBooks.action"><font class="link_font">中国书法</font></a> | <a class="link_class" href=""><font class="link_font">文学编年史</font></a> | <a class="link_class" href=""><font class="link_font">中医学</font></a> | <a class="link_class" href="http://www.cadal.zju.edu.cn/help.jsp"><font class="link_font">使用帮助</font></a> | <a class="link_class" href="mailto:cadal@zju.edu.cn"><font class="link_font">邮件反馈</font></a>
     </span>
     <span id="right_links">
        <a class="link_class" href="http://www.cadal.zju.edu.cn/personal/myPage"><font class="link_font">个人首页</font></a>
     </span>
     <form method="post" action="/QuickSearch" nowrap="nowrap">
<span class="searchform">
<input type="image" src="http://www.cadal.zju.edu.cn/img/a4.png" name="submit" class="indexSubmit">
</span>
</form>
     </div>
  </td>
  </tr>
</table>
</div>

		
		<div align="center" style="" width="100%">
		<div id="search-result">
			<% 
			  List queryResult = (List)request.getAttribute("queryResult");										
			  String timeUse = (String)request.getAttribute("timeUse");							

			%>

			<%
				String count = String.valueOf(queryResult.size());
				//long time = Long.parseLong(timeUse);
			%>

			<div id="resulthead"><div id="resultListHeadIn">共检索到<%= count%>条记录,耗时${timeUse}毫秒</div></div>
			<div id="search-result-container">
			<table valign="top" border="0" align=center cellspacing="0" cellpadding="0">
			<!--%record iterator%--->
			
			<%
				int num = 0;
				if(queryResult.size()%2 != 0) {
					num = queryResult.size() - 1;
				}else{
					num = queryResult.size();
				}
				
				for(int i = 0; i < num; i += 2){
					SearchResult result = (SearchResult)queryResult.get(i);
					String bookNo = result.getBookNo();
					String title  = result.getTitle();
					String subject = result.getSubject();
					if(subject == null){
						subject = "暂无关键词信息";
					}
					String creator = result.getCreator();
					if(creator == null){
						creator = "暂无作者信息";
					}

					String pulisher = result.getPress();
					if(pulisher == null){
						pulisher = "暂无出版社信息";
					}
					
			%>
			
			<tr>
				
				<td>
					<div class="resultRecordSetDiv">
						<table valign="top" border="0" align=center cellspacing="0" cellpadding="0">
							<tr>
								<td width="100">
									<div id="bookimg">
										<a href="http://www.cadal.zju.edu.cn/book/<%= bookNo%>">
											<img width="100" height="140" title="<%= title%>" src="http://www.cadal.zju.edu.cn/cover/<%= bookNo%>" class="cover">
										</a>
									</div>
								</td>
								<td>
									<div id="bookinfo">
										<span id="title" align="left"><%= title%></span><br>
										<span id="subject" align="left"><%= subject%></span><br>
										<span id="creator" align="left"><%= creator%></span><br>
										<span id="publisher" align="left"><%= pulisher%></span><br>
									</div>
								</td>
					
						</tr>
					</table>
				</div>
				</td>

				<%
					SearchResult result2 = (SearchResult)queryResult.get(i + 1);
					String bookNo2 = result.getBookNo();
					String title2  = result.getTitle();
					String subject2 = result.getSubject();
					if(subject2 == null){
						subject2 = "暂无关键词信息";
					}
					String creator2 = result.getCreator();
					if(creator2 == null){
						creator2 = "暂无作者信息";
					}

					String pulisher2= result.getPress();
					if(pulisher2 == null){
						pulisher2 = "暂无出版社信息";
					}
				
				%>
				<td>
					<div class="resultRecordSetDiv">
						<table valign="top" border="0" align=center cellspacing="0" cellpadding="0">
							<tr>
								
								<td>
									<div id="bookimg">
										<a href="http://www.cadal.zju.edu.cn/book/<%= bookNo2%>">
											<img width="100" height="140" title="<%= title2%>" src="http://www.cadal.zju.edu.cn/cover/<%= bookNo2%>" class="cover">
										</a>
									</div>
								</td>
								
								<td>
									<div id="bookinfo">
										<span id="title" align="left"><%= title2%></span><br>
										<span id="subject" align="left"><%= subject2%></span><br>
										<span id="creator" align="left"><%= creator2%></span><br>
										<span id="publisher" align="left"><%= pulisher2%></span><br>
										</div>
										</td>
					
				</tr>
				</table>
				</div>
				</td>

				
			</tr>
			<%
				}
			%>

			</table>
			</div>
			
			
			
			
			
			
			
			
			
			
			
			
			  <table align="center" border="0" cellpadding="0" cellspacing="0" height="200" width="100%">
			      <tbody><tr>
				<td>&nbsp;</td>
				<td valign="top" width="600">
				  <div id="indexAboveFoot">
								<p>
					      项目其他网址：<a href="http://www.cadal.cn">CADAL管理中心</a>&nbsp;|&nbsp;<a href="http://www.calis.edu.cn/">CALIS</a><br><br>
                                              IPv6网站访问客户端工具：<a href="http://www.cadal.zju.edu.cn/Install/6fei_go6_Install.exe">下载</a><br><br>
				  		  <a href="http://www.zju.edu.cn">浙江大学</a>&nbsp;|&nbsp;<a href="http://www.dcd.zju.edu.cn">DCD实验室</a>&nbsp;|&nbsp;<a href="http://www.dl-erc.org">数字图书馆工程研究中心</a>&nbsp;|&nbsp;<a href="#">CADAL开发团队</a>&nbsp;|&nbsp;<a href="help.jsp">帮助</a>
				  		</p>	
						</div>						

					      </td>
					      <td>&nbsp;</td>
					    </tr>
					  </tbody></table>
					
					
					<script language="javascript">
						setSearchFormCheckBox ();
						document.onkeydown = function(e){
							if(window.event.keyCode==13){
								indexCheckInput();
							}
						}
					</script>
				
      
				      </body>
				     
				    </html>
