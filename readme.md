To run the server: 
build the project
mvn install
mvn spring-boot:run

After server started admin and user will be created and initial user has 10000 TL available asset

Postman Collection can be used to send request
Import brokefirm.postman_collection.json file

Use Basic Authentication to send request

admin:
username: admin
password: adminPassword

username: user
password: userPassword