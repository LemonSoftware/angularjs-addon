<#assign projectName = "${projectId}">

Ext.application({
    name: '${projectName}',
    appFolder: '${projectId}',
    requires: [
        'Ext.layout.container.*',
        'Ext.resizer.Splitter',
        'Ext.fx.target.Element',
        'Ext.fx.target.Component',
        'Ext.window.Window',
        '${projectName}.*'
    ]
    , autoCreateViewport: '${projectId}.Viewport'
    ,init: function() {
    }
    ,views: [
            <#list entityNames as entityName>
            '${projectId}.view.${entityName}Grid' <#if entityName_has_next> , </#if>
            </#list>
            ]

,controllers: [
              <#list entityNames as entityName>
              '${projectId}.controller.${entityName}Controller' <#if entityName_has_next> , </#if>
               </#list>
               ]

});