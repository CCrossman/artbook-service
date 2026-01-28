# API Endpoints

## POST /api/credentials/signin

### Request
- email (string, required)
- password (string, required)

### Response
- token (string, optional)
- message (string, error message)

## POST /api/credentials/reset

### Request
- email (string, required)

### Response
- message (string, confirmation message)

## GET /api/images

### Request
- titleSearch (string)
- tags (string, comma-separated)
- startDate (string)
- endDate (string)
- page (number)
- pageSize (number)
- sortBy (string)
- sortOrder (string)

### Response
- items (array)
- total (number)

## POST /api/images

### Request
- title  (string)
- description (string)
- image (file)
- tags (array of key-value pairs)

### Response
- imageId (number)

## GET /api/images/{imageId}

### Path Parameters
- imageId (number)

### Response
- imageId (number)
- imageUrl (string)
- title (string)
- description (string)
- likes (number)
- liked (boolean)
- tags (array of key-value pairs)

## PUT /api/images/{imageId}/like

### Path Parameters
- imageId (number)

### Response
- N/A
