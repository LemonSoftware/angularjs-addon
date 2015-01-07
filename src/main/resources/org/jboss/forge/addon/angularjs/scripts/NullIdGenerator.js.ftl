Ext.define('${projectId}.NullIdGenerator', {
extend: 'Ext.data.identifier.Generator',
alias: 'data.identifier.nullgenerator',
generate: function () {
return null;
}
});