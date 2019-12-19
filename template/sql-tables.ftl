drop database ${dbName};

create database ${dbName};

use ${dbName};

<#list data as item>
   ${item};
</#list>