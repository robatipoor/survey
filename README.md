## A java & spring survey application 

## Install Application
```shell
cd project-directory
./mvn spring-boot:run
# open browser 
# go to http://127.0.0.1:8080/swagger-ui/index.html
```

## Basic Usage
```shell
./mvn spring-boot:run
## open url http://127.0.0.1:8080/swagger-ui/index.html

```

## Getting Started Development
```shell
./mvn clean package 
## install git code format hook
./mvnw git-code-format:install-hooks
```
## Entity Relationship Diagram

![image info](./docs/entity_relationship_diagram.jpg)

## TODO list
1. for security reason we can use uuid instead of sequence number id
2. add more unit and integrate test 
3. add more exception handler
4. fix bug mapper answer
5. add docker file