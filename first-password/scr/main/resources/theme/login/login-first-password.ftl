<#-- 
  Custom login template for the First Password Authenticator.
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Set Your New Password</title>
</head>
<body>
    <h1>Set Your New Password</h1>
    <#if message??>
        <div class="message">${message}</div>
    </#if>
    <form action="${url.loginAction}" method="post">
        <div>
            <label for="password">New Password:</label>
            <input type="password" id="password" name="password" required/>
        </div>
        <div>
            <input type="submit" value="Submit"/>
        </div>
    </form>
</body>
</html>
