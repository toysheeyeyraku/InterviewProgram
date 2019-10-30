<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
    <head>
        <title>Spring Security Example </title>
    </head>
    <body>
        
       ${data}
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