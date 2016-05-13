<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>

<%@include file="../include/header.jsp"%>

<style>
	#perPageNum {
		position: relative;
		float: right;
	}
</style>

<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class='box'>
				<div class="box-header with-border">
					<h3 class="box-title">Board List</h3>
				</div>
				<div class='box-body'>
					<select id="searchType" name="searchType">
						<option value="tcw"
							<c:out value="${criteria.searchType eq 'tcw'?'selected':''}"/>>
							전체</option>
						<%-- <option value="n"
							<c:out value="${criteria.searchType == null?'selected':''}"/>>
							없음</option> --%>
						<option value="t"
							<c:out value="${criteria.searchType eq 't'?'selected':''}"/>>
							제목</option>
						<option value="c"
							<c:out value="${criteria.searchType eq 'c'?'selected':''}"/>>
							내용</option>
						<option value="w"
							<c:out value="${criteria.searchType eq 'w'?'selected':''}"/>>
							작성자</option>
						<option value="tc"
							<c:out value="${criteria.searchType eq 'tc'?'selected':''}"/>>
							제목 및 내용</option>
						<option value="cw"
							<c:out value="${cri.searchType eq 'cw'?'selected':''}"/>>
							내용 및 작성자</option>						
					</select> 
					<input type="text" name='keyword' id="keywordInput"
						value='${criteria.keyword }'>
					<button id='searchBtn'>검색</button>
					<button id='newBtn'>새글</button>					
					<select id ="perPageNum" name="perPageNum">
						<option value="10"
							<c:out value="${criteria.perPageNum eq '10' ? 'selected':''}"/>>
							10개씩 출력
						</option>
						<option value="20"
							<c:out value="${criteria.perPageNum eq '20' ? 'selected':''}"/>>
							20개씩 출력
						</option>
						<option value="5"
							<c:out value="${criteria.perPageNum eq '5' ? 'selected':''}"/>>
							5개씩 출력
						</option>						
					</select>
				</div>
			</div>

			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">LIST ALL PAGE</h3>
				</div>
				<div class="box-body">

					<table class="table table-bordered">
						<tr>
							<th style="width: 10px">BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th style="width: 40px">VIEWCNT</th>
						</tr>

						<c:forEach items="${list}" var="board">

							<tr>
								<td>${board.bno}</td>
								<td><a
									href='/board/read${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${board.bno}'>
										<c:choose>
											<c:when test="${fn:length(board.title) > 20}">
												${fn:substring(board.title, 0, 20)}... [ ${board.replycnt} ]
											</c:when>
											<c:otherwise>
												${board.title}[ ${board.replycnt} ]
											</c:otherwise>
										</c:choose>
								</a></td>
								<td>${board.writer}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
										value="${board.regdate}" /></td>
								<td><span class="badge bg-red">${board.viewcnt }</span></td>
							</tr>

						</c:forEach>

					</table>

				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">

							<c:if test="${pageMaker.prev}">
								<li><a
									href="list${pageMaker.makeSearch(pageMaker.startPage - 1)}">&laquo;</a></li>
							</c:if>

							<c:forEach begin="${pageMaker.startPage}"
								end="${pageMaker.endPage}" var="idx">
								<li
									<c:out value="${pageMaker.cri.page == idx?'class =active':''}"/>>
									<a href="list${pageMaker.makeSearch(idx)}">${idx}</a>
								</li>
							</c:forEach>

							<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
								<li><a
									href="list${pageMaker.makeSearch(pageMaker.endPage + 1)}">&raquo;</a></li>
							</c:if>

						</ul>
					</div>
				</div>
				<!-- /.box-footer-->
			</div>
		</div>
		<!--/.col (left) -->
	</div>
	<!-- /.row -->
</section>
<!-- /.content -->

<script>
	var result = '${result}';

	if (result == "success") {
		alert("처리가 완료되었습니다.");
	}
</script>

<script>
	$(document).ready(function() {
		
		/* 새글 버튼 클릭 */
		$('#newBtn').on("click", function(evt) {
			self.location = "register"
				+ "?page="
				+ ${criteria.page}
				+ "&perPageNum="
				+ ${criteria.perPageNum}
				+ "&searchType="
				+ $("#searchType option:selected").val()							
				+ "&keyword=" + $('#keywordInput').val();
		});
		
		/* 검색 버튼 클릭 */
		$('#searchBtn').on("click", function(event) {
			
			console.log("#searchBtn click ...");			
			console.log($("#searchType option:selected").val());
			
			self.location = "list"
							+ '${pageMaker.makeQuery(1)}'
							+ "&searchType="
							+ $("#searchType option:selected").val()
							+ "&keyword=" + $('#keywordInput').val();
		});
		
		/* #perPageNum select 변경 */
		$('#perPageNum').on("change", function(event) {
						
			console.log("#perPageNum change ...");
			console.log($("#perPageNum option:selected").val());
			
			self.location = "list"
							+ "?page="
							+ '${board.bno+""}'
							+ "&perPageNum="
							+ $("#perPageNum option:selected").val()
							+ "&searchType="
							+ $("#searchType option:selected").val()							
							+ "&keyword=" + $('#keywordInput').val();
		});
		
	});
</script>


<%@include file="../include/footer.jsp"%>