<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div class="mb-1">Создание нового пользователя</div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
   <div class="form-group row">
        <label class="col-sm-2 col-form-label">Почта:</label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid', '')}"
                   placeholder="@mail.com"/>
            <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
        </div>
    </div>

${picture?ifExists}
<@l.login "/registration"/>
</@c.page>