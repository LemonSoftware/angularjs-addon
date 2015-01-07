<#assign entityName = "${entityName}">

Ext.define('${projectId}.model.${entityName}Model', {
    extend: 'Ext.data.Model',
    requires: [
'${projectId}.NullIdGenerator'
    ],
    identifier: {
        type: 'nullgenerator'
    },
    fields: [


<#list properties as property>
{
    name: '${property.identifier}',
    <#if (property["datetime-type"]!"") == "date">
    type: 'date',
    dateFormat:'y-m-d'
    <#elseif (property["datetime-type"]!"") == "time">
    type: 'time'
    <#elseif (property["datetime-type"]!"") == "both">
    type: 'date',
    dateFormat:'time'
    <#elseif property.type == "number">
    type: 'int'
        <#if property["minimum-value"]??> , minValue:"${property["minimum-value"]}"<#if property["maximum-value"]??>,</#if></#if><#t/>
        <#if property["maximum-value"]??> maxValue:"${property["maximum-value"]}"</#if><#t/>
    <#elseif property.type == "boolean">
    type: 'boolean'<#t/>
   <#elseif (property["many-to-one"]!"") != "">
  reference: '${projectId}.model.${property.simpleType}Model'
    <#else>
    defaultValue:null,
    useNull:true
    </#if>

}<#if property_has_next> , </#if>
</#list>


],
<#if property["n-to-many"] ??>
<#if (property["n-to-many"]!"") != "">
manyToMany: '${projectId}.model.${property.simpleType}Model',
</#if>
</#if>
proxy: {

type: 'rest',
url: '${resourceRootPath}/${resourcePath}/',

writer:{
writeAllFields:true,
dateFormat:'time'
},
pageParam: false,
startParam: false,
limitParam: false
}
});