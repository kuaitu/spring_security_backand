package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.*;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
/**
 * <p>
  * ${table.comment!} 前端控制器
  * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if table.comment??>
@Api(tags = { "${table.comment!}" })
</#if>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
  <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${entity}, ${entity}, ${table.serviceName}> {
  public ${table.controllerName}() {
        super(${entity}.class);
  }
  <#else>
public class ${table.controllerName} {
  </#if>
}
</#if>
