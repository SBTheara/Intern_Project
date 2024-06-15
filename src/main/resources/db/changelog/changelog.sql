databaseChangeLog:
   - changeSet:
       id: 1
       author: Liquibase
       changes:
       - createTable:
           tableName: contact
           columns:
           - column:
               name: phone_number
               type: INT
               constraints:
                   primaryKey:  false
                   nullable:  false
                   tableName: test_table