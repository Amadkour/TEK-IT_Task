# TEK-IT Task

## steps to run

```
1- run Discovery App (discovry-service)
2- run Gateway App (tek-it-gateway)
3- go to employee-service forlder and run this command in this folder terminal " docker compose up --build"
4- run employee App (employee-service)
```

## add Employee

```
curl --location 'http://localhost:8080/employee/create' \
--header 'Accept-Language: ar' \
--header 'Content-Type: application/json' \
--data '{
    "name":"AHMD",
    "age":28,
    "nationalId":"29609221601852",
    "branchName":"finance"
} '

```

## update Employee

```
curl --location 'http://localhost:8080/employee/update' \
--header 'Accept-Language: ar' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=A2297D98E71801EE6CFE223DA0C7B266' \
--data '{
    "id":1,
    "name":"AH",
    "age":28,
    "nationalId":"29609221601852",
    "branchName":"Media"
} '

```

## create Employee token

```
curl --location 'http://localhost:8080/employee/token' \
--header 'Accept-Language: ar' \
--header 'national_id: 29609221601852'

```

## find Employee

```
curl --location 'http://localhost:8080/employee/1' \
--header 'Accept-Language: ar' \
--header 'national_id: 29609221601852' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOTYwOTIyMTYwMTg1MiIsImlhdCI6MTcyMTUwNjA1NSwiZXhwIjoxNzIxNTA3ODU1fQ.hPxC4OWYmDMgz4UUJ9sAi6iUHBTsQvArVJWX6sajktY' \

```
## delete Employee

```
curl --location --request DELETE 'http://localhost:8080/employee/1' \
--header 'Accept-Language: ar' \
--header 'national_id: 29609221601852' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyOTYwOTIyMTYwMTg1MiIsImlhdCI6MTcyMTUwNjA1NSwiZXhwIjoxNzIxNTA3ODU1fQ.hPxC4OWYmDMgz4UUJ9sAi6iUHBTsQvArVJWX6sajktY' \
```
