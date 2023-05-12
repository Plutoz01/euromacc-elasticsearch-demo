# Start application locally

1. Start locally hosted Elasticsearch defined in `docker-compose.yml` with command:
```shell
docker compose up user-elasticsearch
```

2. Start application with command:
```shell
mvnw spring-boot:run
```
*Note*: Application is hosted on port 8900.