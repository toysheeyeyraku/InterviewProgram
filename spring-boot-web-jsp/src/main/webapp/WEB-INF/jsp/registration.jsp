<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
    <head>
        <title>Spring Security Example </title>
    </head>
    <body>
        
        <form th:action="@{/registrarion}" method="post" enctype="application/json">
            <div><label> User Name : <input type="text" name="username"/> </label></div>
            <div><label> Password: <input type="text" name="password"/> </label></div>
            <input type="hidden"
    		name="${_csrf.parameterName}"
    		value="${_csrf.token}"/>
            <div><input type="submit" value="Add User"/></div>
        </form>
    </body>
</html>