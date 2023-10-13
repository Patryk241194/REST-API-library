# RESTful API Endpoints

## Borrowing Controller

### Borrow a book
- **Method:** POST
- **Endpoint:** /api/borrowings/borrow-book
- **Description:** Borrow a book.
- **Sample Request:** http://localhost:8080/api/borrowings
- **Sample Request Body:**
```json
{
  "bookCopyId": 5,
  "readerId": 7,
  "borrowingDate": "2023-09-29",
  "returnDate": "2023-10-15"
}
```

### Return a book
- **Method:** POST
- **Endpoint:** `/api/borrowings/return-book/{borrowingId}`
- **Description:** Return a book.
- **Sample Request:** http://localhost:8080/api/borrowings/return-book/1
- **Sample Response:**
```json
"The book has been returned and is available."
```

### Get a list of all borrowings
- **Method:** GET
- **Endpoint:** `/api/borrowings`
- **Description:** Get a list of all borrowings.
- **Sample Request:** http://localhost:8080/api/borrowings

### Get a borrowing by ID
- **Method:** GET
- **Endpoint:** `/api/borrowings/{borrowingId}`
- **Description:** Get a borrowing by ID.
- **Sample Request:** http://localhost:8080/api/borrowings/1
- **Sample Response:**
```json
{
    "id": 1,
    "bookCopyId": 5,
    "readerId": 7,
    "borrowingDate": "2023-09-29",
    "returnDate": "2023-10-15"
}
```

### Get borrowings by reader
- **Method:** GET
- **Endpoint:** `/api/borrowings/reader/{readerId}`
- **Description:** Get borrowings by reader.
- **Sample Request:** http://localhost:8080/api/borrowings/reader/7
- **Sample Response:**
```json
[
    {
        "id": 1,
        "bookCopyId": 5,
        "readerId": 7,
        "borrowingDate": "2023-09-29",
        "returnDate": "2023-10-15"
    },
    {
        "id": 2,
        "bookCopyId": 8,
        "readerId": 7,
        "borrowingDate": "2023-10-01",
        "returnDate": "2023-10-18"
    }
]
```

### Update a borrowing by ID
- **Method:** PATCH
- **Endpoint:** `api/borrowings/{borrowingId}`
- **Description:** Update a borrowing by ID.
- **Sample Request:** http://localhost:8080/api/borrowings/1
- **Sample Request Body:**
```json
{
    "returnDate": "2023-10-20"
}
```
- **Sample Response:**
```json
{
    "id": 1,
    "bookCopyId": 5,
    "readerId": 7,
    "borrowingDate": "2023-09-29",
    "returnDate": "2023-10-20"
}
```

### Delete a borrowing by ID
- **Method:** DELETE
- **Endpoint:** `/api/borrowings/{borrowingId}`
- **Description:** Delete a borrowing by ID.
- **Sample Request:** http://localhost:8080/api/borrowings/1