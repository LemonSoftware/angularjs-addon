<#if (property.hidden!"false") == "false">
{
header: '${property.label}',
flex: 1,
renderer: function (value, metaData, record, row, col, store, gridView) {
    var items = '';
    if(record.data != null && record.data.${property.identifier} != null){
    if(record.data.${property.identifier} instanceof Array) {
        record.data.${property.identifier}.forEach(function (entry) {
            items += entry.name + ', ';
            });
            items = items.trim().substr(0 , items.trim().length - 1);
        }
        else{
            items = record.data.${property.identifier}.name;
            }
        }
    return items;
    },
editor: {
    <#if (property.required!"false") == "true">
    allowBlank: false,
    <#else>
    allowBlank: true,
    </#if>
    xtype: 'combo',
    displayField: 'name',
    multiSelect: true,
    valueField: 'id',
    value: 'Choose a ${property.label}',
    store: Ext.getStore('${property.simpleType}Store'),
    listeners:{
            'select': function(combo, records){
                var items = [];
                records.forEach(function(entry) {
                    items.push(entry.data);
                    });
            this.up('grid').getSelectionModel().getSelection()[0].data.${property.identifier} = items;
            }
        }
    }
}
</#if>