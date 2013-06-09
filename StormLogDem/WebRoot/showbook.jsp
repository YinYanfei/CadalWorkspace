<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0046)http://www.cadal.zju.edu.cn/book/view/06100009 -->
<%@ page import="java.util.List" %>
<html lang="zh-CN" xmlns="http://www.w3.org/1999/xhtml" xmlns:xn="http://www.renren.com/2009/xnml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta name="keywords" content="数字图书馆,民国,在线,古籍">
      <meta http-equiv="Content-Language" content="zh-CN">
	<title>大学数字图书馆国际合作计划</title>
    <style type="text/css">

        #background_center {
/*
        background: url(/new_logo/personal_base.png) no-repeat scroll 0% 0% transparent;
        display:block;
        height:86px;
        padding-left: 0px;
*/
        }
        #base {
	background: url(new_logo/ge_up1.png) no-repeat scroll 0% 0% transparent;
        display:block;
        border: 0px;
        height:87px;
        }

        #account {
         margin:0px;
	 padding-right:10px;
         display:inline;
        }
       #search{
          margin:0px;
          display: inline;
        }
        .banner-info{
	height:45px;
        color:#000000;
        float:right;
        padding-right:10px;
}
.footer{
        width:804px;
        height:82px;
/*      background:url(/img/footer.gif) no-repeat;*/
}
.footer-content{
        padding-top:20px;
        padding-bottom:20px;
        padding-left:50px;
        padding-right:50px;
}
.sidepane{
	min-height:100px;
	height:auto !important;
	height:100px;
}

        #background {
              background-color: #ECEFF3;
              background-repeat: repeat-x;
        }

        .banner-foot {
	background: url(new_logo/ge_down.png) no-repeat scroll 0% 0% transparent;
        height:50px;
        display:block;
        padding-top:8px;
        padding-left:25px;
}
	body {
	margin:0;
        font-size:13px;
        font-family:"宋体";
        color:#4c4847;
	background-color:#ECEFF3;
/*      font-color:#4c4847;*/
        line-height:18px;
        }
        a:link {
        color:#4d74b4;
	text-decoration: none;
}
        a:visited {
        color:#4d74b4;
	text-decoration: none;
}
        a:hover {
        color:#fc6f07;
}
        a:active {
	text-decoration: none;
        color:#4d74b4;
}
 
.back-to-home{
	float:left;
	padding-top:2px;
	padding-left:25px;
}
.mymain1 span.base-title{
        margin-left:8px;
        margin-bottom:14px;
}
.link_class {
        font-size:110%;
        color:#333333;
}
.links {
  float:left;
  padding-top:10px;
  padding-left:30px;
}

div.clear{
	clear:both;
}
div.bookimg{
	float:left;
}
div.bookinfo{
	float:left;
	text-align:left;
}
div.notesinfo {
/*      background:url("/img/h150.png") repeat scroll 0 50% #FFFFFF;*/
	width:170px;
        border-top:1px dotted #4b79a1;
        word-wrap:break-word;
	margin-left:3px;
	margin-right:7px;
}
fieldset{
	margin:0;
	padding:0;
	border:0 none;
}
legend.title{
	font-size:15px;
}
.bookinfo span{
	text-align:left;
}
.baseline{
	font-size:12px;
	color:#C3C3C3;
}
div.notesinfo img {
        padding-top:10px;
        float:left;
	border:1px solid #CFDBE4;
        width:30px;
        height:30px;
}
#catalog-title {
	width:590px;
	height:31px;
	background:url(img/catalog-title.gif) no-repeat;
}
.reply-info p{
        padding-top:10px;
        font-size:12px;
        margin:0;
}
.hr-dash{
        width:780px;
        border-top:1px dashed #ddd;
        height: 1px;
        overflow:hidden;
}

.notesno{
	height:33px;
	padding-bottom:7px;
	padding-left:15px;
}
/.notesno {
/*        background:url("/img/n.png") repeat-x scroll center top transparent;*/
        -moz-user-select:none;
        padding-top:8px;
        font-size:22px;
        color:white;
        margin-right:25px;
        height:30px;
        cursor:move;
}
.no {
	border:1px solid #b0d5e8;
	background-color:#dbeef5;
/*        background:url("/img/no.png") no-repeat scroll left top transparent;*/
}
div.isbn_more{
	width:270px;
	display:none;
	margin-bottom:80px;
}
.notesmain {
/*      border:1px solid #DDDDDD;*/
/*      background-color:#33C1F1;*/
/*      height:700px;*/
        overflow:hidden;
/*      opacity:0.8;*/
	width:180px;
}
ul#previewtree ul {
        padding-left:20px;
}
#previewtree li {
        list-style:none;
}
#previewtree ul.hide {
        display:none;
}
#previewtree span {
/*        color:#999;
        font-family:"Courier New", Courier, monospace;*/
        cursor:default;
}
#previewtree span.expanded, #previewtree span.collapsed {
        cursor:pointer;
}

.background {
           margin: 0 auto;
           width:960px;
/*           background-image:url(/backgroundImage/bookview2.jpg);      */
}
div.book-catalog-tree{
        background-color:#F3FAFF;
        width:586px;
        float:left;
	border:2px solid #ECE9D8;
}
div.book-notes {
        width:180px;
        float:left;
	border:1px;
}
.cover {
     border:1px solid #999999;
}
.total-wide {
	border:1px solid #ccd8e2;
	padding-right:9px;
	padding-left:9px;
	padding-top:5px;
}
.book {
     background-color:#F3FAFF;
     padding-top:5px;
	display:block;
     width:780px;
/*     border-left:9px solid #ccd8e2;
     border-right:9px solid #ccd8e2;*/
}
.modal {
                background-color:burlyWood;
                display:none;
                width:386px;
                text-align:center;
                border:2px solid #333;
				height:200px;
                opacity:0.8;
                -moz-border-radius:6px;
                -webkit-border-radius:6px;
                -moz-box-shadow: 0 0 50px #ccc;
                -webkit-box-shadow: 0 0 50px #ccc;
}

</style>
  </head>
  <body youdao="bind">
<div id="background" align="center" valign="top">
<table valign="top" align="center" width="840" border="0" cellspacing="0" cellpadding="0">
<tbody><tr valign="top" align="center" width="840">
<td valign="top" align="center" height="120">
<!-- <div id="background_move" style="padding-left:6px">
<div id="background_center" align="center" valign="top">
-->
<div id="base" align="center">
	<div class="eng" style="float:right;padding-top:5px;padding-right:20px;">
		<a href="http://www.cadal.zju.edu.cn/personal/setlanguage?language=English"><img src="new_logo/england.png" width="20" height="13" border="0"></a></div>
<div class="banner-info">

</div> <!-- end banner-info -->
</div> <!-- end base -->

<div align="left" class="banner-foot">
 <div class="links" style="padding-top:3px;padding-left:10px;align:center;">
    <a href="http://www.cadal.zju.edu.cn/"><font class="link_class">首页</font></a> | <a href="http://www.cadal.zju.edu.cn/IM/IM.htm"><font class="link_class">图像库</font></a> | <a href="http://www.cadal.zju.edu.cn/VideoShow.action"><font class="link_class">视频库</font></a> | <a href="http://www.cadal.zju.edu.cn/Calligraphy/"><font class="link_class">中国书法</font></a> | <a href="http://www.cadal.zju.edu.cn/bns/"><font class="link_class">文学编年史</font></a> | <a href="http://www.cadal.zju.edu.cn/ChineseMedicine/"><font class="link_class">中医药</font></a>|<a class="link_class" href="http://www.cadal.zju.edu.cn/music/"><font class="link_class">音频</font></a> 
<span style="padding-left:260px">
  <span class="account" align="left">
          
  </span>

</span>
</div>

</div> <!-- end banner-foot -->
</td></tr>

<tr width="820"><td style="padding-left:10px;">   
<table valign="top" border="0" align="center" cellspacing="0" cellpadding="0" width="815" style="padding-left:5px;">

<tbody><tr>
<td align="left" valign="top" width="3" style="background:url(/img/left_td.gif) repeat-y;">
</td>
<td bgcolor="#ffffff"><div class="total-wide">
<table cellspacing="0" cellpadding="0" bgcolor="white" width="815">
<tbody><tr><td align="center">
   
 <div class="book">
<%
	List<List<String>> finalResult = (List<List<String>>)request.getAttribute("finalResult");
	List<String> bookTitle = (List<String>)request.getAttribute("bookTitle");
%>
	<table width="810" style="padding-top:10px;float:left;padding-left:15px;">
    <tbody>
	<tr>
    <td valign="top" width="150">
	<div id="bookimg" width="100">
<%
	for (int i = 0; i < finalResult.size(); ++i) {
		String bookno = finalResult.get(i).get(0);
		String bookname = bookTitle.get(i);
		List<String> listTmp = finalResult.get(i).subList(1, finalResult.get(i).size());
%>
	<a href="http://www.cadal.zju.edu.cn/book/06100009"><img class="cover" src="http://www.cadal.zju.edu.cn/cover/<%= bookno%>" width="100" height="140" title="中国书法全集--顔真卿一"></a>
	<br/>
	<span><%= bookname%></span>	
	</div>
    </td>
    <%
    	for(int j = 0; j < listTmp.size(); ++j) {
    		String chapterTmp = listTmp.get(j); 
    %>
    <td style="text-align:left;" valign="top"> 
	<li><span>&nbsp;</span><%= chapterTmp%></li>
    <br>	   
    </td>
    <%
    	} 
    %>
    </tr></tbody></table>
<%
	}
%>
	<div class="clear"></div>
</div>
<div class="modal" id="newTags"></div>

</td></tr>

</tbody></table></div></td>
<td align="left" valign="top" width="3" style="background:url(/img/right_td.gif) repeat-y;"></td>

</tr></tbody></table>
</td>
</tr></tbody></table>

<div class="footer" align="center"><div class="footer-content" align="center"><span>
<a href="http://www.cadal.cn/">CADAL管理中心</a> | <a href="http://www.zju.edu.cn/">浙江大学</a> | <a href="http://www.dcd.zju.edu.cn/">DCD实验室</a></span>
</div>

<br>
</div>
</div>
</body></html>
