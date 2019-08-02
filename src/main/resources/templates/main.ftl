<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
                       placeholder="Найти по тэгу">
                <button type="submit" class="btn btn-primary ml-2">Найти</button>
            </form>
        </div>
    </div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Добавить картинку
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control" name="picturename" placeholder="Введите имя картинки">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="creator" placeholder="Введите имя автора">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="text" placeholder="Комментарий">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="tags" placeholder="Хэштег">
                </div>
                <div class="form-group">
                    <label for="exampleFormControlFile1">Example file input</label>
                    <input type="file" name="file" class="form-control-file" id="exampleFormControlFile1">
                </div>
                <input type="hidden" class="form-control" name="_csrf" value="${_csrf.token}">
                <div class="form-group">
                    <button type="submit" class="btn btn-primary ml-2">Добавить</button>
                </div>
            </form>
        </div>

    </div>

    <div class="card-columns">
        <#list pictures as picture>
            <div class="card my-3" >
                <#if picture.filename??>
                    <img src="/img/${picture.filename}" class="card-img-top">
                <#--<audio src="/img/${picture.filename}"></audio>-->
                </#if>
                <div class="card-body">
                    <h5 class="card-title">${picture.picturename} ${picture.text}</h5>
                    <p class="card-text">${picture.creator}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <a href="/user-pictures/${picture.author.id}">${picture.authorName}</a>
                    </li>
                    <li class="list-group-item">${picture.tags}</li>
                </ul>
            </div>
        <#else>
            Нет никаких постов :(
        </#list>
    </div>
    </body>
    </html>
</@c.page>