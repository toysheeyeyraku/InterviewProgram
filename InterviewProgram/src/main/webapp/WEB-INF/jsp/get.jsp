<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
      <%@ page import="java.util.ArrayList" %>
    <head>
        <title>Spring Security Example </title>
    </head>
    <body>
        
       <%
       ArrayList<String> p=(ArrayList<String>) request.getAttribute("data");
       String shab = "<form th:action=\"@{/get}\" method=\"post\" enctype=\"application/json\">\r\n"
				+ "             <input type=\"hidden\" name=\"type\" value=\"%s\"/> \r\n" + "           \r\n"
				+ "            <input type=\"hidden\"\r\n" + "    		name=\"${_csrf.parameterName}\"\r\n"
				+ "    		value=\"${_csrf.token}\"/>\r\n"
				+ "            <div><input type=\"submit\" value=\"%s\"/></div>\r\n" + "        </form>";
		StringBuilder ans = new StringBuilder();
		if (p != null) {
			for (String s : p) {
				ans.append(String.format(shab, s, s));
			}
		}
		out.println(ans.toString());
       %>
        ${question}
       
        
        <form th:action="@{/geti}" method="post" enctype="application/json">
            <div><label> Comment <input type="text" name="comment"/> </label></div>
            
            <input type="hidden"
    		name="${_csrf.parameterName}"
    		value="${_csrf.token}"/>
            <div><input type="submit" value="Add Comment"/></div>
        </form>
        
        <form th:action="@{/end}" method="post" enctype="application/json">
           <input type="hidden" name="end"/> 
            
            <input type="hidden"
    		name="${_csrf.parameterName}"
    		value="${_csrf.token}"/>
            <div><input type="submit" value="End Interview"/></div>
        </form>
        
      ${warn} 
    </body>
</html>