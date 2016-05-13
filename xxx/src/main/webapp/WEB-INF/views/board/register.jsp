<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>

<!-- <style>
.fileDrop {
	width: 80%;
	height: 100px;
	border: 1px dotted gray;
	background-color: lightslategrey;
	margin: auto;
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
					<h3 class="box-title">REGISTER BOARD</h3>
				</div>
				<!-- /.box-header -->
				<form role="form" method="post">
					<div class="box-body">
						<div class="form-group">
							<label for="exampleInputEmail1">Title</label> <input id="title"
								type="text" name='title' class="form-control"
								placeholder="Enter Title" required="required"
								autofocus="autofocus">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">Content</label>
							<textarea id="content" class="form-control" name="content"
								rows="3" placeholder="Enter ..." required="required"></textarea>
						</div>
						<div class="form-group">
							<label for="exampleInputEmail1">Writer</label> <input id="writer"
								type="text" name="writer" class="form-control"
								placeholder="Enter Writer" required="required">
						</div>

						<!-- <div class="form-group">
							<label for="exampleInputEmail1">여기에 파일을 드롭하세요.</label>
							<div class="fileDrop"></div>
						</div> -->
					</div>
					<!-- /.box-body -->
					<div class="box-footer">
						<div>
							<hr>
						</div>
						<ul class="mailbox-attachments clearfix uploadedList">
						</ul>
						<button type="submit" class="btn btn-warning">저장</button>
						<button type="reset" class="btn btn-danger">취소</button>
						<button type="button" class="btn btn-primary">목록</button>
					</div>
				</form>
			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->
	</div>
	<!-- /.row -->
</section>
<!-- /.content -->

<script type="text/javascript" src="/resources/js/upload.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<script id="template" type="text/x-handlebars-template">
<li>
  <span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
  <div class="mailbox-attachment-info">
	<a href="{{getLink}}" download="{{fileName}}" class="mailbox-attachment-name">{{fileName}}</a>
	<a href="{{fullName}}" 
     class="btn btn-default btn-xs pull-right delbtn"><i class="fa fa-fw fa-remove"></i></a>
	</span>
  </div>
</li>                
</script>

<!-- <script>
	var template = Handlebars.compile($("#template").html());
	
	$(".fileDrop").on("dragenter dragover", function(event){
		event.preventDefault();
	});
	
	
	$(".fileDrop").on("drop", function(event){
		event.preventDefault();
		
		var files = event.originalEvent.dataTransfer.files;
		
		var file = files[0];
		
		//파일 업로드 크기 제한
		if(file.size > 10485760) {
			alert("파일 업로드는 10MB까지 가능합니다.");
			return;
		}
	
		var formData = new FormData();
		
		formData.append("file", file);	
		
		
		$.ajax({
			  url: '/uploadAjax',
			  data: formData,
			  dataType:'text',
			  processData: false,
			  contentType: false,
			  type: 'POST',
			  success: function(data){				  
				  var fileInfo = getFileInfo(data);				  
				  var html = template(fileInfo);
				  
				  $(".uploadedList").append(html);
			  }
			});	
	});
</script> -->

<script>
	var template = Handlebars.compile($("#template").html());
	
	$("form").on("dragenter dragover", function(event){
		event.preventDefault();
	});
	
	
	$("form").on("drop", function(event){
		event.preventDefault();
		
		var files = event.originalEvent.dataTransfer.files;
		
		var file = files[0];
		
		//파일 업로드 크기 제한
		if(file.size > 10485760) {
			alert("파일 업로드는 10MB까지 가능합니다.");
			return;
		}
	
		var formData = new FormData();
		
		formData.append("file", file);	
		
		
		$.ajax({
			  url: '/uploadAjax',
			  data: formData,
			  dataType:'text',
			  processData: false,
			  contentType: false,
			  type: 'POST',
			  success: function(data){				  
				  var fileInfo = getFileInfo(data);				  
				  var html = template(fileInfo);
				  
				  $(".uploadedList").append(html);
			  }
			});	
	});
</script>

<script>
	$(document).ready(function() {
		var formObj = $("form[role='form']");
		
		/* 저장 버튼 클릭 */
		$(".btn-warning").on("click", function() {
			if($("#title").val().length != 0 &&
			   $("#content").val().length != 0 &&
			   $("#writer").val().length != 0) {
				
				var str ="";
				$(".uploadedList .delbtn").each(function(index){
					 str += "<input type='hidden' name='files["+index+"]' value='"+$(this).attr("href") +"'> ";
				});
				
				formObj.append(str);
				
				formObj.submit();
			}
		});
		
		/* 취소 버튼 클릭 */
		$(".btn-danger").on("click", function() {
			$("#title").focus();
			formObj.reset;
		});
		
		/* 목록 버튼 클릭 */
		$(".btn-primary").on("click", function() {
			self.location = "/board/list?page=${criteria.page}&perPageNum=${criteria.perPageNum}"
				+"&searchType=${criteria.searchType}&keyword=${criteria.keyword}";
		});

	});
</script>

<script>
	$(".uploadedList").on("click", "a:last", function(event){	
		 event.preventDefault();
	
		 console.log("delete file...............");
		 console.log($(this).attr("href"));
	
		 var that = $(this);
	
		 $.ajax({
			 url:"/deleteFile",
			 type:"post",
			 data: {fileName:$(this).attr("href")},
			 dataType:"text",
			 success:function(result){
			 if(result == "deleted"){
				 console.log("deleted......................");
				 console.log(that.closest("li").html());
				 that.closest("li").remove();
			 }
			 }
		 }); 
		});

</script>

<%@include file="../include/footer.jsp"%>