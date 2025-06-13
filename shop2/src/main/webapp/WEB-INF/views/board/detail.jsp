<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세보기</title>
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<style type="text/css">
.leftcol {
	text-align: left;
	vertical-align: top;
}
.lefttoptable {
	min-height: 250px;
	border-width: 0px;
	text-align: left;
	vertical-align: top;
	padding: 0px;
}
</style>
</head>
<body class="bg-gray-100 font-sans">
	<div class="container mx-auto p-6 max-w-4xl">
		<div class="bg-white shadow-md rounded-lg p-6">
			<table class="w-full table-auto border-collapse">
				<tr class="bg-blue-600 text-white">
					<td colspan="2" class="p-4 text-xl font-semibold">${boardName}</td>
				</tr>
				<tr class="border-b">
					<td class="p-4 w-1/6 font-medium text-gray-700">글쓴이</td>
					<td class="p-4 w-5/6 leftcol text-gray-800">${board.writer}</td>
				</tr>
				<tr class="border-b">
					<td class="p-4 font-medium text-gray-700">제목</td>
					<td class="p-4 leftcol text-gray-800">${board.title}</td>
				</tr>
				<tr class="border-b">
					<td class="p-4 font-medium text-gray-700">내용</td>
					<td class="p-4 leftcol">
						<div class="lefttoptable bg-gray-50 p-4 rounded-md text-gray-800">${board.content}</div>
					</td>
				</tr>
				<tr class="border-b">
					<td class="p-4 font-medium text-gray-700">첨부파일</td>
					<td class="p-4">
						<c:if test="${!empty board.fileurl}">
							<a href="file/${board.fileurl}" class="text-blue-500 hover:underline">${board.fileurl}</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="p-4 text-center space-x-4">
						<a href="reply?num=${board.num}" class="inline-block px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition">답변</a>
						<a href="update?num=${board.num}&boardid=${board.boardid}" class="inline-block px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600 transition">수정</a>
						<a href="delete?num=${board.num}&boardid=${board.boardid}" class="inline-block px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600 transition">삭제</a>
						<a href="list?boardid=${board.num}&boardid" class="inline-block px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600 transition">게시물목록</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>