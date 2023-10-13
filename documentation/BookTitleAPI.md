# RESTful API Endpoints

## BookTitle Controller

### Create a new book title
- **Method:** POST
- **Endpoint:** `/api/booktitles`
- **Description:** Create a new book title.
- **Sample Request:** http://localhost:8080/api/booktitles
- **Sample Request Body:**
```json
{
    "title": "Star Wars",
    "author": "George Lucas"
}
```

### Get a list of all book titles
- **Method:** GET
- **Endpoint:** `/api/booktitles`
- **Description:** Get a list of all book titles.
- **Sample Request:** http://localhost:8080/api/booktitles

### Get a book title by ID
- **Method:** GET
- **Endpoint:** `/api/booktitles/{bookTitleId}`
- **Description:** Get a book title by ID.
- **Sample Request:** http://localhost:8080/api/booktitles/1
- **Sample Response:**
```json
{
    "id": 1,
    "title": "Star Wars",
    "author": "George Lucas",
    "copiesIds": [1, 2, 3]
}
```

### Get the count of available copies for a book title
- **Method:** GET
- **Endpoint:** `/api/booktitles/{bookTitleId}/available-copies-count`
- **Description:** Get the count of available copies for a book title.
- **Sample Request:** http://localhost:8080/api/booktitles/1/available-copies-count
- **Sample Response:**
```json
2
```

### Update a book title by ID
- **Method:** PATCH
- **Endpoint:** `/api/booktitles/{bookTitleId}`
- **Description:** Update a book title by ID.
- **Sample Request:** http://localhost:8080/api/booktitles/1
- **Sample Request Body:**
```json
{
    "title": "Updated Title"
}
```

- **Sample Response:**
```json
{
    "id": 1,
    "title": "Updated Title",
    "author": "George Lucas",
    "copiesIds": [1, 2, 3]
}
```

### Delete a book title by ID
- **Method:** DELETE
- **Endpoint:** `/api/booktitles/{bookTitleId}`
- **Description:** Delete a book title by ID.
- **Sample Request:** http://localhost:8080/api/booktitles/1