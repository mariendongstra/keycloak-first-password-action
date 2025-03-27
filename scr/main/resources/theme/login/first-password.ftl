<#import "template.ftl" as layout>
<@layout.registrationLayout>
    <h1>Set Your First Password</h1>
    <form action="${url.loginAction}" method="post">
        <div>
            <label for="password-new">New Password</label>
            <input type="password" id="password-new" name="password-new" />
        </div>
        <div>
            <label for="password-confirm">Confirm Password</label>
            <input type="password" id="password-confirm" name="password-confirm" />
        </div>
        <div>
            <input type="submit" value="Submit" />
        </div>
    </form>
    <#if messages?has_content>
        <div>${messages}</div>
    </#if>
</@layout.registrationLayout>