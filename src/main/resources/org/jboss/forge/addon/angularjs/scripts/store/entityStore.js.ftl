<#assign entityName = "${entityName}"
entityResource = "${entityName}Resource"
entityIdJsVar="${entityName}Id">

Ext.define('${projectId}.store.${entityName}Store', {
    extend: 'Ext.data.Store',
    autoLoad: true,
    autoDestroy: false,
    remoteSort: false,
    remoteFilter: false,

model: '${projectId}.model.${entityName}Model',
    proxy: {
        type: 'rest',
url: '${resourceRootPath}/${resourcePath}/',

        writer:{ writeAllFields:true },
        pageParam: false,
        startParam: false,
        limitParam: false
    }
});

<#list properties as property>

<#if property["lookup"]??>
Ext.define('${projectId}.store.${property.identifier}Store', {
extend: 'Ext.data.Store',
fields: ['id', 'name'], data: [
    <#list property["lookup"]?split(",") as option>
{"id": "${option_index + 1}", "name": "${option}"}
        <#if option_has_next>,</#if>
    </#list>
    ]
});
</#if>
</#list>


