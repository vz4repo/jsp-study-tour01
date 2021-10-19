<%@page import="data.dto.NoticeDto"%>
<%@page import="data.dao.NoticeDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<%
	//num읽기 
	String num=request.getParameter("num");
	//페이지 번호 읽기
	String currentPage=request.getParameter("currentPage");
	//db에서 num dto 가져오기
	NoticeDao dao=new NoticeDao();
	NoticeDto dto=dao.getNot(num);
	//프로젝트의 경로
	String root=request.getContextPath();
%>
<script type="text/javascript" src="<%=root%>/se2/js/HuskyEZCreator.js"
	charset="utf-8"></script>

<script type="text/javascript" src="<%=root%>/se2/photo_uploader/plugin/hp_SE2M_AttachQuickPhoto.js"
	charset="utf-8"></script>	
</head>

<body>
<div style="margin-left: 350px; margin-top: 300px;">
<form action="notice/noticeupdateaction.jsp" method="post">
	<input type="hidden" name="num" value="<%=num%>">
	<input type="hidden" name="currentPage" value="<%=currentPage%>">
	<table style="width:750px;">
			<tr>
				
				<td width="500">
					<textarea  style="width:480px; height:30px;"
					name="title" required="required"><%=dto.getTitle() %></textarea>
				</td>
			</tr>
			<tr>
				
				<td width="500">
					 <textarea style="width:100%; height:300px; display:none;" id="content"
					 name="content" required="required"><%=dto.getContent() %></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<button type="submit" class="btn1"
					style="margin-left:190px; width:100px; height:50px;" onclick="submitContents(this)">수정</button>
				</td>
				
				
			</tr>
		
		</table>
</form>
<!-- 스마트게시판에 대한 스크립트 코드 넣기 -->
<script type="text/javascript">
var oEditors = [];

nhn.husky.EZCreator.createInIFrame({

    oAppRef: oEditors,

    elPlaceHolder: "content",

    sSkinURI: "<%=request.getContextPath()%>/se2/SmartEditor2Skin.html",

    fCreator: "createSEditor2"

}); 

//‘저장’ 버튼을 누르는 등 저장을 위한 액션을 했을 때 submitContents가 호출된다고 가정한다.

function submitContents(elClickedObj) {

    // 에디터의 내용이 textarea에 적용된다.

    oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", [ ]);

 

    // 에디터의 내용에 대한 값 검증은 이곳에서

    // document.getElementById("textAreaContent").value를 이용해서 처리한다.
    try {
        elClickedObj.form.submit();
    } catch(e) { 

    }

}

// textArea에 이미지 첨부

function pasteHTML(filepath){
    var sHTML = '<img src="<%=request.getContextPath()%>/save/'+filepath+'">';
    oEditors.getById["content"].exec("PASTE_HTML", [sHTML]); 

}
</script>
</div>
</body>
</html>
