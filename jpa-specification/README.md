### Create Docker Container

```cli
docker run --name <CONTAINER_NAME> -e POSTGRES_PASSWORD=12345678 -d -p 5434:5432 -v <SHARED_FILE_PATH>:/var/lib/postgresql/data postgres
```

