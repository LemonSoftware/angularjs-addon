<#if (property.hidden!"false") == "false">
{
header: '${property.label}',
flex: 1,
renderer: function (value, metaData, record, row, col, store, gridView) {
    return record.data != null && record.data.${property.identifier} != null ? record.data.${property.identifier} : '';
    },
    editor: {
            <#if (property.required!"false") == "true">
            allowBlank: false,
            <#else>
            allowBlank: true,
            </#if>
            xtype: 'combo',
            displayField: 'name',
            valueField: 'id',
            queryMode:'local',
            value: 'Choose a ${property.label}',
            store: Ext.create('${projectId}.store.${property.identifier}Store'),
            listeners:{
                      'select': function(combo, records){
                      this.up('grid').getSelectionModel().getSelection()[0].data.${property.identifier} = records[0].data.name;
                      }
            }
    }
}
</#if>