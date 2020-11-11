curl -X POST \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d "{\"name\":\"ahoj\",\"url\":\"https://www.seznam.cz\"}" \
http://localhost:8080/links

curl -X GET \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
http://localhost:8080/links/ahoj
