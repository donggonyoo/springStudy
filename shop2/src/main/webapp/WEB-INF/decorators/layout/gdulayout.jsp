<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<sitemesh:write property="title" />
<sitemesh:write property="head" />
<sitemesh:write property="body" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title><sitemesh:title default="관리자 대시보드" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<%-- summernote 관련 설정
    jquery, bootstrap 기능 사용 --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote.min.js"></script>

<style>
body {
	overflow-x: hidden;
}

.sidebar {
	width: 250px;
	transition: all 0.3s ease;
	position: fixed;
	top: 56px;
	bottom: 0;
	left: 0;
	background-color: #f8f9fa;
	overflow-y: auto;
	z-index: 1000;
}

.sidebar.collapsed {
	width: 80px;
}

.main-content {
	margin-left: 250px;
	transition: all 0.3s ease;
	padding: 1.5rem;
	margin-top: 56px;
}

.main-content.collapsed {
	margin-left: 80px;
}

.sidebar .list-group-item {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.footer {
	text-align: center;
	padding: 1rem;
	border-top: 1px solid #dee2e6;
	margin-top: 2rem;
}
</style>

<sitemesh:head />
</head>
<body>
	<!-- 상단 네비게이션 -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container-fluid">
			<button class="btn btn-outline-light me-2" id="toggleSidebar">☰</button>
			<a class="navbar-brand" href="/user/mypage?userid=${loginUser.userid}">MyAdmin</a>
			<div class="collapse navbar-collapse">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link" href="#">${loginUser.userid}님
							하이</a></li>
					<li class="nav-item"><a class="nav-link" href="/user/mypage?userid=${loginUser.userid}">홈</a></li>
					<li class="nav-item"><a class="nav-link" href="#">설정</a></li>
					<li class="nav-item"><a class="nav-link" href="/user/logout">로그아웃</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- 왼쪽 사이드바 -->
	<div id="sidebar" class="sidebar border-end">
		<div class="list-group list-group-flush mt-3">
			<a href="/admin/dashboard"
				class="list-group-item list-group-item-action">📊 대시보드</a> <a
				href="/admin/users" class="list-group-item list-group-item-action">👥
				사용자 관리</a> <a href="/board/list?boardid=1"
				class="list-group-item list-group-item-action">📌 공지사항</a> <a
				href="/board/list?boardid=2"
				class="list-group-item list-group-item-action">💬 자유게시판</a> <a
				href="/board/list?boardid=3"
				class="list-group-item list-group-item-action">❓ Q&A</a> <a href="#"
				class="list-group-item list-group-item-action">⚙️ 설정</a>

		</div>
	</div>

	<!-- 메인 콘텐츠 -->
	<div id="mainContent" class="main-content">
		<sitemesh:body />
	</div>

</span>
	<!-- 푸터 -->
	<footer class="footer text-muted"> &copy; 2025 MyAdmin. All
		    <span id="si">
     <select name="si" onchange="getText('si')">
        <option value="">시도를 선택하세요</option>
     </select>
    </span>
    <span id="gu">
   <select name="gu" onchange="getText('gu')">
      <option value="">구군을 선택하세요</option>
   </select>
    </span>
    <span id="dong">
      <select name="dong">
          <option value="">동리를 선택하세요</option>
      </select>
    </span> 
  </footer>

	<!-- JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
    const toggleBtn = document.getElementById("toggleSidebar");
    const sidebar = document.getElementById("sidebar");
    const mainContent = document.getElementById("mainContent");

    toggleBtn.addEventListener("click", () => {
      sidebar.classList.toggle("collapsed");
      mainContent.classList.toggle("collapsed");
    });
  </script>
</body>
</html>
