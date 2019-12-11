drop database ${dbName};

create database ${dbName};

<#list data as item>
   ${item};
</#list>