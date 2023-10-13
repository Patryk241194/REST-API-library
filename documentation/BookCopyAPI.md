# RESTful API Endpoints

## Book Copy Controller

### Create a new book copy
- **Method:** POST
- **Endpoint:** `/api/bookcopies`
- **Description:** Create a new book copy.
- **Sample Request:** http://localhost:8080/api/bookcopies
- **Sample Request Body:**
```json
{
  "status": "AVAILABLE",
  "publicationYear": 2020
}
```

### Get a list of all book copies
- **Method:** GET
- **Endpoint:** `/api/bookcopies`
- **Description:** Get a list of all book copies.
- **Sample Request:** http://localhost:8080/api/bookcopies

### Get a book copy by ID
- **Method:** GET
- **Endpoint:** `/api/bookcopies/{bookCopyId}`
- **Description:** Get a book copy by ID.
- **Sample Request:** http://localhost:8080/api/bookcopies/1
- **Sample Response:**
```json
{
    "id": 1,
    "titleId": 3,
    "status": "AVAILABLE",
    "publicationYear": 2005,
    "borrowingId": 6
}
```

### Update a book copy by ID
- **Method:** PATCH
- **Endpoint:** `/api/bookcopies/{bookCopyId}`
- **Description:** Update a book copy by ID.
- **Sample Request:** http://localhost:8080/api/bookcopies/1
- **Sample Request Body:**
```json
{
    "status": "DAMAGED",
    "publicationYear": 2015
}
```
- **Sample Response:**
```json
{
"id": 1,
"titleId": 3,
"status": "DAMAGED",
"publicationYear": 2015,
"borrowingId": 6
}
```

### Change the status of a book copy
- **Method:** PATCH
- **Endpoint:** `/api/bookcopies/{bookCopyId}/change-status`
- **Description:** Change the status of a book copy.
- **Sample Request:** http://localhost:8080/api/bookcopies/1/change-status?status=LOST

### Delete a book copy by ID
- **Method:** DELETE
- **Endpoint:** `/api/bookcopies/{bookCopyId}`
- **Description:** Delete a book copy by ID.
- **Sample Request:** http://localhost:8080/api/bookcopies/1

