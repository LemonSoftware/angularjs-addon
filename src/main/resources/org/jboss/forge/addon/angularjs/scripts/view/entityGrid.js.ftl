<#assign entityName = "${entityName}">

Ext.define('${projectId}.view.${entityName}Grid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.${entityName}Grid',
    initComponent: function () {
        Ext.apply(this, {
            store: Ext.getStore('${entityName}Store'),
            loadMask: true,
            frame: true,
            layout: 'auto',
            columns: [
            ${formProperties}
            ],
            tbar: [
                {
                    itemId: 'add${entityName}BtnId',
                    text: 'Add ${entityName}',
                    iconCls: 'add_button'
                },
                {
                    itemId: 'delete${entityName}BtnId',
                    text: 'Delete ${entityName}',
                    iconCls: 'delete_button',
                    disabled: true
                }
            ],
            plugins: [
                {
                    pluginId: 'roweditorid',
                    ptype: 'rowediting',
                    clicksToMoveEditor: 1,
                    clickToEdit: 2,
                    autoCancel: false
                }
            ]
        });
        this.callParent(arguments);
    }
});
