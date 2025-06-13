<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<style type="text/css">
textarea {
    resize: vertical;
}
</style>
</head>
<body class="bg-gray-100 font-sans">
    <div class="container mx-auto p-6 max-w-3xl">
        <div class="bg-white shadow-md rounded-lg p-6">
            <h2 class="text-2xl font-semibold text-gray-800 mb-6 text-center">게시글 작성</h2>
            <form:form modelAttribute="board" action="write" 
                enctype="multipart/form-data" name="f">
                <input type="hidden" name="boardid" value="${param.boardid}">
                <table class="w-full table-auto border-collapse">
                    <tr class="border-b">
                        <th class="p-4 w-1/5 font-medium text-gray-700 text-left">글쓴이</th>
                        <td class="p-4">
                            <form:input path="writer" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                            <div class="text-red-500 text-sm mt-1"><form:errors path="writer"/></div>
                        </td>
                    </tr>
                    <tr class="border-b">
                        <th class="p-4 font-medium text-gray-700 text-left">비밀번호</th>
                        <td class="p-4">
                            <form:password path="pass" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                            <div class="text-red-500 text-sm mt-1"><form:errors path="pass"/></div>
                        </td>
                    </tr>
                    <tr class="border-b">
                        <th class="p-4 font-medium text-gray-700 text-left">제목</th>
                        <td class="p-4">
                            <form:input path="title" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                            <div class="text-red-500 text-sm mt-1"><form:errors path="title"/></div>
                        </td>
                    </tr>
                    <tr class="border-b">
                        <th class="p-4 font-medium text-gray-700 text-left">내용</th>
                        <td class="p-4">
                            <form:textarea path="content" rows="10" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                            <div class="text-red-500 text-sm mt-1"><form:errors path="content"/></div>
                        </td>
                    </tr>
                    <tr class="border-b">
                        <th class="p-4 font-medium text-gray-700 text-left">첨부파일</th>
                        <td class="p-4">
                            <input type="file" name="file1" class="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="p-4 text-center space-x-4">
                            <a href="javascript:document.f.submit()" class="inline-block px-6 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition">게시물등록</a>
                            <a href="list?boardid=${param.boardid}" class="inline-block px-6 py-2 bg-gray-500 text-white rounded-md hover:bg-gray-600 transition">게시물목록</a>
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
    </div>
</body>
</html>