<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script type="text/javascript" src="/resources/js/upload.js"></script>

<%@include file="../include/header.jsp"%>

<!-- <style type="text/css">
	.popup {position: absolute;}
	.back {background-color: gray; opacity: 0.5; width: 100%; height: 300%; overflow: hidden; z-index: 1101;}
	.front {z-index: 1110; opacity: 1; border: 1px; margin:  auto;}
	.show {
		position: relative;
		max-width: 1200px;
		max-height: 800px;
		overflow: auto;
	}
</style> -->


<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">READ BOARD</h3>
				</div>
				<!-- /.box-header -->
				<form role="form" action="modify" method="post">
					<input type='hidden' name='bno' value="${board.bno}"> <input
						type='hidden' name='page' value="${criteria.page}"> <input
						type='hidden' name='perPageNum' value="${criteria.perPageNum}">
					<input type='hidden' name='searchType'
						value="${criteria.searchType}"> <input type='hidden'
						name='keyword' value="${criteria.keyword}">
				</form>
				<div class="box-body">
					<div class="form-group">
						<label for="exampleInputEmail1">Title</label> <input type="text"
							name='title' class="form-control" value="${board.title}"
							readonly="readonly">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Content</label>
						<textarea class="form-control" name="content" rows="3"
							readonly="readonly">${board.content}</textarea>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Writer</label> <input type="text"
							name="writer" class="form-control" value="${board.writer}"
							readonly="readonly">
					</div>
				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<button type="submit" class="btn btn-warning" id="modify">수정</button>
					<button type="submit" class="btn btn-danger" id="remove">삭제</button>
					<button type="submit" class="btn btn-primary" id="list">목록</button>
				</div>
			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->
	</div>
	<!-- /.row -->

	<ul class="mailbox-attachments clearfix uploadedList"></ul>

	<!-- <div class="popup back" style="disply:none"></div>
	<div id="popup_front" class="popup front" style="disply:nonoe"><img id="popup_img"></div> -->





	<!-- 댓글 등록 영역  -->
	<div class="row">
		<div class="col-md-12">
		
			<!-- 댓글 쓰기 영역 -->
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">댓글 쓰기</h3>
				</div>
				<div class="box-body">
					<label for="exampleInputEmail1">작성자</label> 
					<input class="form-control" type="text" placeholder="USER ID"
						id="newReplyWriter"> 
					<label for="exampleInputEmail1">댓글 내용</label> 
					<input class="form-control" type="text"
						placeholder="REPLY TEXT" id="newReplyText">
				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<button type="button" class="btn btn-primary" id="replyAddBtn">댓글 등록</button>
				</div>
			</div>

			<!-- 댓글 목록 영역(The time line) -->
			<ul class="timeline">
				<!-- timeline time label -->
				<li class="time-label" id="repliesDiv">
					<span class="bg-green viewBtn">
						댓글 숨기기 [ ${board.replycnt} ]
					</span>
				</li>
			</ul>
			<!-- 페이징 영역 -->
			<div class='text-center'>
				<ul id="pagination" class="pagination pagination-sm no-margin">
				</ul>
			</div>
		</div>
		<!-- /.col -->
		
	</div>
	<!-- /.row -->
	
	<!-- 댓글 수정/삭제/취소 영역(Modal) -->
	<div id="modifyModal" class="modal modal-primary fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body" data-rno>
					<p>
						<input type="text" id="replytext" class="form-control">
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" id="replyModBtn">Modify</button>
					<button type="button" class="btn btn-danger" id="replyDelBtn">DELETE</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- /.content -->

<script>
	$(document).ready(function(){
		
		var formObj = $("form[role='form']");
		
		/* 목록 버튼 클릭 */
		$("#list").on("click", function(){
			/* self.location = "/board/listPage"; */
			formObj.attr("method", "get");
			formObj.attr("action", "/board/list");
			formObj.submit();	// hidden의 정보가 같이 넘어간다.
		});
		
		/* 수정 버튼 클릭 */
		$("#modify").on("click", function(){
			formObj.attr("action", "/board/modify");
			formObj.attr("method", "get");
			formObj.submit();
		});
		
		/* 삭제 버튼 클릭 */
		$("#remove").on("click", function(){
			formObj.attr("action", "/board/remove");
			formObj.submit();
		});
		
	});
</script>

<%-- Handlebars Template 영역 --%>
<script id="template" type="text/x-handlebars-template">
{{#each .}}
<li class="replyLi" data-rno={{rno}}>
<i class="fa fa-comments bg-blue"></i>
 <div class="timeline-item" >
  <span class="time">
    <i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
  </span>
  <h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
  <div class="timeline-body">{{replytext}} </div>
    <div class="timeline-footer">
     <a class="btn btn-primary btn-xs" 
	    data-toggle="modal" data-target="#modifyModal">Modify</a>
    </div>
  </div>			
</li>
{{/each}}
</script>


<script>
	/* Handlebars 기능 추가 영역(helper) */
	Handlebars.registerHelper("prettifyDate", function(timeValue) {
		var dateObj = new Date(timeValue);
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth() + 1;
		var date = dateObj.getDate();
		return year + "/" + month + "/" + date;
	});

	/* Template 찍어내기 */
	var printData = function(replyArr, target, templateObject) {
		var template = Handlebars.compile(templateObject.html());
		var html = template(replyArr);
		$(".replyLi").remove();
		target.after(html);
	}

	var bno = ${board.bno};	
	var replyPage = 1;
	var replycnt = 0;
	
	function getPage(pageInfo) {		
		$.getJSON(pageInfo, function(data) {
			replycnt = data.pageMaker.totalCount;
			printData(data.list, $("#repliesDiv"), $('#template'));
			printPaging(data.pageMaker, $(".pagination"));
			$("#modifyModal").modal('hide');
			$(".viewBtn").html("데이터 숨기기" + "[" + replycnt + "]");
		});
	}

	var printPaging = function(pageMaker, target) {
		var str = "";

		if (pageMaker.prev) {
			str += "<li><a href='" + (pageMaker.startPage - 1) + "'> << </a></li>";
		}

		for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
			var strClass = pageMaker.cri.page == i ? 'class=active' : '';
			str += "<li "+strClass+"><a href='"+i+"'>" + i + "</a></li>";
		}

		if (pageMaker.next) {
			str += "<li><a href='" + (pageMaker.endPage + 1) + "'> >> </a></li>";
		}

		target.html(str);
	};	
	
	/* 페이징 처리 */
	$(".pagination").on("click", "li a", function(event){		
		event.preventDefault();
		replyPage = $(this).attr("href");
		getPage("/replies/"+bno+"/"+replyPage);	
	});
	
	/* 댓글 등록 */
	$("#replyAddBtn").on("click",function(){	
		var replyerObj = $("#newReplyWriter");
		var replytextObj = $("#newReplyText");
		var replyer = replyerObj.val();
		var replytext = replytextObj.val();		
		  
		$.ajax({
			type:'post',
			url:'/replies/',
			headers: { 
			      "Content-Type": "application/json",
			      "X-HTTP-Method-Override": "POST" },
			dataType:'text',
			data: JSON.stringify({bno:bno, replyer:replyer, replytext:replytext}),
			success:function(result){
				console.log("result: " + result);
				if(result == 'success'){
					alert("등록 되었습니다.");
					replyPage = 1;
					getPage("/replies/"+bno+"/"+replyPage );
					$("#pagination").show();
					replyerObj.val("");
					replytextObj.val("");
				}
			}
		});
	});

	$(".timeline").on("click", ".replyLi", function(event){		
		var reply = $(this);
		
		$("#replytext").val(reply.find('.timeline-body').text());
		$(".modal-title").html(reply.attr("data-rno"));
	});		

	$("#replyModBtn").on("click",function(){		  
		var rno = $(".modal-title").html();
		var replytext = $("#replytext").val();
		 
		$.ajax({
			type:'put',
			url:'/replies/'+rno,
			headers: { 
			      "Content-Type": "application/json",
			      "X-HTTP-Method-Override": "PUT" },
			data:JSON.stringify({replytext:replytext}), 
			dataType:'text', 
			success:function(result){
				console.log("result: " + result);
				if(result == 'success'){
					alert("수정 되었습니다.");
					getPage("/replies/"+bno+"/"+replyPage );
				}
			}
		});
	});

	$("#replyDelBtn").on("click",function(){		  
		 var rno = $(".modal-title").html();
		 
		 $.ajax({
			type:'delete',
			url:'/replies/'+rno,
			headers: { 
			      "Content-Type": "application/json",
			      "X-HTTP-Method-Override": "DELETE" },
			dataType:'text', 
			success:function(result){
				console.log("result: " + result);
				if(result == 'success'){
					alert("삭제 되었습니다.");
					getPage("/replies/"+bno+"/"+replyPage );
				}
			}
		});
	});
	
	//댓글 목록이 그냥 처음부터 보이게 하기
	getPage("/replies/" + bno + "/1");
	
	// 댓글 목록 토글(보이기 <-> 숨기기)
 	var isShow = true;	
	$("#repliesDiv").on("click", function() {		
 		if(isShow == true) {
 			$(".replyLi").remove();
 			$("#pagination").hide(); 			
 			$(".viewBtn").text("댓글 보이기" + "[" + replycnt + "]");
 			isShow = false;
 		} else {
 			$(".viewBtn").text("댓글 숨기기" + "[" + replycnt + "]");
			getPage("/replies/" + bno + "/" + replyPage);
 			$("#pagination").show();
 			isShow = true;
 		}
	});

</script>


<script id="templateAttach" type="text/x-handlebars-template">
<li data-src='{{fullName}}'>
	<span calss="mailbox-attachment-icon has-img"> <img scr="{{imgsrc}}" alt="Attachment">
		<div class="mailbox-attachment-info">
			<a href="{{getLink}}" download="{{fileName}}" class="mailbox-attachment-name">{{fileName}}</a>
		</div>
	</span>
</li>
</script>


<script>
	var bno = ${board.bno};
	var template = Handlebars.compile($('#templateAttach').html());

	$.getJSON("/board/getAttach/" + bno, function(list){
		
		$(list).each(function() {
			
			var fileInfo = getFileInfo(this);
			
			var html = template(fileInfo);
			
			$(".uploadedList").append(html);
		});
	});
</script>

<!-- <script>
	$(".uploadList").on("click", ".mailbox-attachment-info a", function(event){
		
		var fileLink = $(this).attr("href");
		
		if (checkImageType(fileLink)) {
			
			event.preventDefault();
			
			var imaTag = $("#popup_img");
			imgTag.attr("src", fileLink);
			
			console.log(imgTag.attr("src"));
			
			$("popup").show('slow');
			imgTag.addClass("show");
		}
	});
	
	$("#popup_img").on("click", function() {
		$(".popup").hide("slow");
	});
</script> -->



<%@include file="../include/footer.jsp"%>