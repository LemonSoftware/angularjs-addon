Ext.define('${projectId}.Viewport', {
    extend: 'Ext.container.Viewport',
    uses: [
<#list entityNames as entityName>

'${projectId}.view.${entityName}Grid' <#if entityName_has_next> , </#if>
</#list>
    ],
    initComponent: function () {
        Ext.apply(this, {
            layout: {
                type: 'border'
            },
            items: [
                {
region: 'center',
xtype: 'tabpanel',
// TabPanel itself has no title
activeTab: 0,
// First tab active by default
items: [
<#list entityNames as entityName>
{
itemId: '${entityName}ManagementGridId',
title: '${entityName} Management',
xtype: '${entityName}Grid'
}
    <#if entityName_has_next> , </#if>
</#list>


]
}
            ]
        });
        this.callParent(arguments);
    }
});