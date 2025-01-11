# north-phonebook-app
PhoneBook application that enables creation of phonebook contacts and searching those phonebook contacts

Technologies that are used to implement this project are:
* Spring boot framework
* Java 8
* Maven
* H2 in memory database
* JUnit 
* Postman (for testing)

1. To check the database, you need to open up H2 console. Navigate to: http://localhost:8080/h2-console/
* Fill:
	- Driver Class:org.h2.Driver
	- JDBC URL:jdbc:h2:mem:phonebook_db
	- User Name: admin
	- Password: 

2. The application is build as jar file. 
To start the application, create JAR run configuration and pick up the builded jar file from target folder.

3. To test the application, you can use Postman.

There are two request methods, GET and POST

Test scenarios

POST METHOD: Create new phonebook contact 

- In postman choose POST method and use the following url:
http://localhost:8080/phonebook/contacts
with body JSON type: (one by one)

* {
	"name": "Andrea",
	"phoneNumber": "+38971456987"
}
* {
	"name": "Ferdinand",
	"phoneNumber": "+38971456987"
}
* {
	"name": "Brandy",
	"phoneNumber": "+38976896541"
}
* {
	"name": "Gjurgjica",
	"phoneNumber": "+38976668498"
}

When inserting data, if it is successful, you should see message of type: "Successfully created phonebook contact"

There is validation on the name and phoneNumber.
The name should not be null and phoneNumber has specific format

Try to insert some wrong format of the phoneNumber or null or empty value for name:
* {
	"name": null,
	"phoneNumber": "+38976668498"
}
* {
	"name": null,
	"phoneNumber": "++38976668498"
}
* {
	"name": "",
	"phoneNumber": "++38976668498"
}
* {
	"name": "",
	"phoneNumber": "+38976668498"
}
* {
	"name": "",
	"phoneNumber": "++38976"
}
* {
	"name": "Gjurgjica",
	"phoneNumber": "++38976"
}

* Expected result: HttpStatus 400 BAD Request 

Payload:
	{
		"errorCode": "INVALID_PAYLOAD",
		"errorMessage": "field_name"
	}


GET METHOD: Search phonebook contacts 
- In postman choose GET method and use the following url: http://localhost:8080/phonebook/contacts?name=Test&phoneNumber=1234563.

We can make search with the two parameters, with name parameter only or with the phoneNumber parameter only 

Search filter by name and phoneNumber
http://localhost:8080/phonebook/contacts?name=And&phoneNumber=%38976896541
* Expected result: should return only one record 
	- [
	    {
		"name": "Brandy",
		"phoneNumber": "+38976896541"
	    }
	]

Search filter by name 
http://localhost:8080/phonebook/contacts?name=And
* Expected result: list of founded records
	- [
	    {
		"name": "Andrea",
		"phoneNumber": "+38971456987"
	    },
	    {
		"name": "Ferdinand",
		"phoneNumber": "+38971456987"
	    },
	    {
		"name": "Brandy",
		"phoneNumber": "+38976896541"
	    }
	]

Search filter by phoneNumber
http://localhost:8080/phonebook/contacts?phoneNumber=%2B38971456987
* Expected result:
	- [
	    {
		"name": "Andrea",
		"phoneNumber": "+38971456987"
	    },
	    {
		"name": "Ferdinand",
		"phoneNumber": "+38971456987"
	    }
	]

If there is no result found, the following message is returned: No search result for the given search criteria

4. There are also some JUnit tests and integration tets that cover some of the functionalities.
