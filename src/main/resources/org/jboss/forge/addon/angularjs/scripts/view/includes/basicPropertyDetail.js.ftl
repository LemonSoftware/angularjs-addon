<#if (property.hidden!"false") == "false">
    <#if (property["datetime-type"]!"") == "date" ||  (property["datetime-type"]!"") == "both" >
        <#assign columnType = "datecolumn">
        <#assign editorType = "datefield">
        <#assign format = "d/m/y">
    <#elseif (property["datetime-type"]!"") == "time" >
        <#assign columnType = "timecolumn">
        <#assign editorType = "timefield">
    <#elseif property.type == "number">
        <#assign columnType = "numbercolumn">
        <#assign editorType = "numberfield">
    <#elseif property.type == "integer">
        <#assign columnType = "numbercolumn">
        <#assign editorType = "numberfield">
        <#assign renderer = "numberRenderer('0')">
    <#elseif property.type == "boolean">
        <#assign columnType = "checkcolumn">
        <#assign editorType = "checkbox">
    </#if>
{
header: '${property.label}',
dataIndex: '${property.identifier}',
flex: 1,
    <#if columnType ??>
    xtype: '${columnType}',
    </#if>
    <#if format ??> format:'${format}', </#if> <#t />
    <#if renderer ??> renderer: Ext.util.Format.${renderer}, </#if> <#t />
editor: {
    <#if editorType ??>
    xtype:'${editorType}',
    </#if>
    <#if property["minimum-value"]??> minValue:"${property["minimum-value"]}"<#if property["maximum-value"]??>,</#if></#if><#t/>
    <#if property["maximum-value"]??> maxValue:"${property["maximum-value"]}"</#if><#t/>
    <#if format ??> format:'${format}', </#if> <#t />
    <#if (property.required!"false") == "true">
    allowBlank: false
    <#else>
    allowBlank: true
    </#if>
}
}</#if>