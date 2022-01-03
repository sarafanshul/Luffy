# Luffy
AMQP client for Spring app

### Endpoints
- Messaging `/messge`
  - `@POST /send` for adding message to MQ, *requires body : `MessageData`*
  - `@GET /count` for getting count of messages in queue, *requires param : `queue`*
- User `/user`
  - `@GET /get` fetches `User` with *param : `id`*
  - `@GET /tomodachi` fetches `List<User>` *param : `userId`*
  - `@PUT /nakama` adds `User` to DB , *Returns `Boolean True` if successful*
  - `@POST /nakama` Connects two users *Requires body : `ConnectionData`, Returns `Boolean True` if successful*
  - `@POST /nakama_bye` Disconnects two users *Requires body : `ConnectionData`, Returns `Boolean True` if successful* 

### Docker Res
- DIY ( *endpoints are mapped so as to consume the mongo and rabbitmq from localhost* )
  - Build Container `docker build -t luffy-test .`
  - Run Container `docker run -p 8080:8080 luffy-test`
  - [Internal port mapping](https://stackoverflow.com/questions/24319662/from-inside-of-a-docker-container-how-do-i-connect-to-the-localhost-of-the-mach)
  - [Base port mapping](https://stackoverflow.com/questions/67120152/spring-boot-apps-port-mapping-in-docker-container)
- Using Compose `docker-compose up`

### Author 
[Anshul](https://github.com/sarafanshul)