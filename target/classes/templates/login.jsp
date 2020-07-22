<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
    <title>Security with Remember Me in Spring boot</title>
</head>

<body>
    <div th:if="${param.error}">
        <h1 style="color:red">Authentication credentials invalid! Please check again.</h1>
    </div>
    <div th:if="${param.logout}">
        <h1 style="color:green">Successfully logged out.</h1>
    </div>
    <form th:action="@{/login}" method="post">
        <div>Username:
            <input type="text" name="username" /> </div>
        <div>Password:
            <input type="password" name="password" /> </div>
        <div>Remember Me:
            <input type="checkbox" name="remember-me" /> </div>
        <div>
            <input type="submit" value="Sign In" />
        </div>
    </form>
</body>

</html>