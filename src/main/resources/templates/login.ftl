<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    ${picture?ifExists}
<@l.login "/login"/>
<a href="/registration">Создание нового пользователя</a>

</@c.page>

