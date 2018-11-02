# task-management

This is the TODOLIST application which was built on top of Springframework, it is applied several technologies:
- Spring cloud (Spring Cloud Contract, Spring Netflix Cloud,...)
- SpringMvcREST
- Spring Security:
              + Using basic authentication
              + Spring security ACLs for domain access control authorization (schema for this is included in [resource/sql]
- Persistence: 
              + MongoDB for whole business of application & authentication info
              + MongoDB with QueryDSL, paging applied
              + MongoDB 
              + MySQL for the domain authorization which is built base on Spring Security ACLs
              + Spring Mongo data
              + Spring JDBC for working with Spring security ACL
- Swagger API framework
- Spring Circuit Breaker for avoid failure when client consume data from REST API
- Kafka event driven 
- Projection, security on projection (field 'password')
- Logging with slf4j
- UnitTest provided


* Applying best practices:
- REST API best practices
- HATEOAS 
- Single responsible principle, Open-closed principle, Dependencies inversion principle
- Singleton pattern, Builder pattern,...





* Entities:
  + User: the owner of the task, username and password will be used for authentication
  + Task: the task which is created by user
  

To be updated...
