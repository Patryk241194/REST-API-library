# RESTful API Endpoints

## Reader Controller

### Create a new reader
- **Method:** POST  
- **Endpoint:** `/api/readers`  
- **Description:** Create a new reader.  
- **Sample Request:** http://localhost:8080/api/readers
- **Sample Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Smith",
  "registrationDate": "2023-09-29"
}
```

### Get a list of all readers
- **Method:** GET  
- **Endpoint:** `/api/readers`  
- **Description:** Get a list of all readers.
- **Sample Request:** http://localhost:8080/api/readers

### Get a reader by ID
- **Method:** GET  
- **Endpoint:** `/api/readers/{readerId}`  
- **Description:** Get a reader by ID.  
- **Sample Request:** http://localhost:8080/api/readers/1  
- **Sample Response:**
```json
{
    "id": 1,
    "firstName": "John",
    "lastName": "Smith",
    "registrationDate": "2023-09-29",
    "borrowingIds": [23, 72]
}
```

### Get a list of readers with borrowings of a specific status

- **Method:** GET
- **Endpoint:** `/api/readers/with-borrowings?status=BORROWED`
- **Description:** Get a list of readers with borrowings of a specific status.
- **Sample Request:** http://localhost:8080/api/readers/with-borrowings?status=BORROWED
- **Sample Response:**
```json
    {
        "id": 1,
        "firstName": "John",
        "lastName": "Smith",
        "registrationDate": "2023-09-29",
        "borrowingIds": [23, 72]
    }
```

### Get a list of readers with overdue borrowings
- **Method:** GET  
- **Endpoint:** `/api/readers/overdue-readers`  
- **Description:** Get a list of readers with overdue borrowings.
- **Sample Request:** http://localhost:8080/api/readers/overdue-readers

### Update a reader by ID
- **Method:** PATCH  
- **Endpoint:** `api/readers/{readerId}`  
- **Description:** Update a reader by ID.  
- **Sample Request:** http://localhost:8080/api/readers/1  
- **Sample Request Body:**
```json
{
    "firstName": "Updated First Name"
}
```
- **Sample Response:**
```json
    {
        "id": 1,
        "firstName": "Updated First Name",
        "lastName": "Smith",
        "registrationDate": "2023-09-29",
        "borrowingIds": [23, 72]
    }
```

### Delete a reader by ID
- **Method:** DELETE  
- **Endpoint:** `/api/readers/{readerId}`  
- **Description:** Delete a reader by ID.  
- **Sample Request:** http://localhost:8080/api/readers/1