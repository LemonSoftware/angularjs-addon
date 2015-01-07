<#assign entityName = "${entityName}">

Ext.define('${projectId}.controller.${entityName}Controller', {
    extend: 'Ext.app.Controller',

    models: [
        '${entityName}Model'
    ],

    stores: [
        '${entityName}Store'
    ],

    views: [
        '${projectId}.view.${entityName}Grid'
    ],

    refs: [
        {ref: 'add${entityName}Btn', selector: 'button[itemId = add${entityName}BtnId]'},
        {ref: 'delete${entityName}Btn', selector: 'button[itemId = delete${entityName}BtnId]'},
        {ref: 'mainGridPanel', selector: '${entityName}Grid[itemId = ${entityName}ManagementGridId]'}
    ],

    init: function () {
        this.control({
            '${entityName}Grid[itemId = ${entityName}ManagementGridId]': {
                'edit': this.onRowEditFinished,
                'canceledit': this.onRowEditCanceled,
                'selectionchange': this.onSelectRow
            },
            'button[itemId=add${entityName}BtnId]': {
                'click': this.${entityName}Add
            },
            'button[itemId=delete${entityName}BtnId]': {
                'click': this.${entityName}Delete
            }
        })
    },

    onSelectRow: function (view, records) {
        this.getDelete${entityName}Btn().setDisabled(!records.length);
    },

    onRowEditFinished: function (editor, context, eOpts) {
        var me = this;
        var store = Ext.getStore('${entityName}Store');
        context.record.save({
        success: function (batch, eOpts) {
                var location = eOpts.getResponse().getResponseHeader('Location');
                var serverId;
                if(location != null) {
                        var fragments = location.split('/');
                        serverId = fragments[fragments.length - 1];
                    }else{
                        serverId = eOpts.getRecords()[0].data.id;
                    }
                eOpts.getRecords()[0].data.id = serverId;
                var data = {
                        <#list properties as property>
                        ${property.identifier}:eOpts.getRecords()[0].data.${property.identifier}<#if property_has_next>,</#if>
                        </#list>
                    };
                me.updateRecord(data);
            },
        failure: function (batch, eOpts) {
                Ext.MessageBox.alert('Error', 'Error while communicating with the server. Changes not saved on the server.', eOpts.getResponse());
            }
        });
    },

    onRowEditCanceled: function (editor, context, eOpts) {
        var sm = this.getMainGridPanel().getSelectionModel();
        var store = this.getStore('${entityName}Store');
        var recordData = sm.getSelection()[0].data;
        if (!recordData.id) {
            store.remove(sm.getSelection());
            if (store.getCount() > 0) {
                sm.select(0);
            }
        }
    },

    ${entityName}Add: function () {
        var rowEditor = this.getMainGridPanel().getPlugin('roweditorid');
        rowEditor.cancelEdit();
        var newRecord = Ext.create('${projectId}.model.${entityName}Model');
        this.id = this.id + 1;
        var store = Ext.getStore('${entityName}Store');
        store.insert(0, newRecord);
        this.getMainGridPanel().getView().refresh();
        rowEditor.startEdit(0, 0);
    },

    ${entityName}Delete: function () {
        var sm = this.getMainGridPanel().getSelectionModel();
        var rowEditor = this.getMainGridPanel().getPlugin('roweditorid');
        var store = Ext.getStore('${entityName}Store');
        rowEditor.cancelEdit();
        var selectedRecord = sm.getSelection();
        Ext.MessageBox.confirm('Confirm', 'Are you sure you want to delete ${entityName}?', function (btn) {
            if (btn == 'yes') {
                store.erase({records: selectedRecord});
                store.remove(selectedRecord);
                if (store.getCount() > 0) {
                sm.select(0);
                }
            }
        });
    },


    updateRecord: function (data) {
        var newRecord = Ext.create('${projectId}.model.${entityName}Model', {
            <#list properties as property>
            ${property.identifier}: data.${property.identifier} <#if property_has_next>,</#if>
            </#list>
        });
        this.refreshSelectedRecord(newRecord);
    },
    refreshSelectedRecord: function (record) {
        var store = Ext.getStore('${entityName}Store');
        var selectedIndex = store.indexOf(this.getMainGridPanel().getSelectionModel().getSelection()[0]);
        Ext.getStore('${entityName}Store').remove(this.getMainGridPanel().getSelectionModel().getSelection()[0]);
        Ext.getStore('${entityName}Store').insert(selectedIndex, record);
    }
});