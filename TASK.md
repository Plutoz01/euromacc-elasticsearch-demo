# Task description

Készítsünk egy Java 17-es Spring Boot (maven) alkalmazást, amely Elasticsearchben lévő adatokat képes kezelni.

Szolgáltasson az alkalmazás két darab REST API-t:

## Új felhasználó rögzítése Elasticsearchben REST API:

API elérése: `/api/elasticsearch/create`

Input adat modell: `CreateUserRequest (firstName: string, lastName: string, email: string)`

Output adat modell: ResponseEntity-be csomagolt `UserResponse (id: uuid, firstName: string, lastName: string, email: string)`

Ha az input adatok közül a következő adatok nincsenek kitöltve, akkor hibával térjen vissza az API: firstName, lastName

Az id mezőt generálja le a REST API.

## Felhasználó lekérése Elasticsearchből REST API:

API elérése: `/api/elasticsearch/search`

Input adat modell: UserSearchRequest (firstName: string, lastName: string)

Output adat modell: ResponseEntity-be csomagolt `UserSearchResponse (total: long [hány darab egyezést talált], userList: List<UserResponse> [felhasználók objektumai, amelyek megfeleltek a keresésnek])`

`UserResponse (id: uuid, firstName: string, lastName: string, email: string)`

Mind a két bemenő adatot kötelező kitölteni. Ha valamelyik nincs kitöltve, akkor hibával térjen vissza az API.

Mindkettő (firstName és lastName-re is) LIKE-os keresés fusson le az Elasticsearchben.

## További feladatok:

Ttelepíteni kell minimum egy Elasticsearch alkalmazást, amely docker-ben fut. A telepítést leíró `docker-compose.yml` fájlt szintén csatoljuk az alkalmazáshoz (hogy tudjuk tesztelni az appot).

Készüljenek unit tesztek is az alkalmazáshoz (minimum kettő).

A teljes spring boot alkalmazás kódját ( docker-compose-al együtt ) tömörítve (zip, jar stb.) szeretnénk megkapni.

Tesztelni az alkalmazás API-jait például Postman-ből lehet http hívással.