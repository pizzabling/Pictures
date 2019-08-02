<#include "security.ftl">

<div class="card-columns">
    <#list pictures as picture>
        <div class="card my-3">
            <#if picture.filename??>
                <img src="/img/${picture.filename}" class="card-img-top">
            </#if>
            <div class="m-2">
                <span>${picture.text}</span><br/>
                <i>${picture.tags}</i>
            </div>
            <div class="card-footer text-muted">
                <a href="/user-pictures/${picture.author.id}">${picture.authorName}</a>
                <#if picture.author.id == currentUserId>
                   </#if>
            </div>
        </div>
    <#else>
        Нет картинок
    </#list>
</div>
