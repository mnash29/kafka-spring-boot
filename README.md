# Kafka Tutorial

## Running Kafka using KRaft

Configure logs for starting in KRaft mode

```sh
sh /path/to/directory/kafka/bin/kafka-storage.sh random-uuid
sh /path/to/directory/kafka/bin/kafka-storage.sh format -t "<UUID>" -c ./kraft/server.properties
```

Start Kafka server using the KRaft config file
> NOTE: Run below command for each server.properties files if running with multiple brokers.

```sh
sh /path/to/directory/Application/kafka/bin/kafka-server-start.sh ./kraft/server.properties
```

Gracefully, shutdown all running Kafka brokers.

```sh
sh /path/to/directory/Application/kafka/bin/kafka-server-stop.sh
```

## Running Kafka in Docker

Generate a random UUID for Kafka Cluster

```sh
sh /path/to/directory/kafka/bin/kafka-storage.sh random-uuid
```

Run docker compose

```sh
docker-compose -f kafka-docker-compose.yaml up --env-file environment.env
```

Create new topic with Kafka CLI via the docker terminal

```sh
sh /path/to/directory/kafka-topics.sh --create --topic test-topic --bootstrap-server host.docker.internal:9092
```

List running topics using the Kafka CLI on your localhost

> NOTE: You may need to add the line `127.0.0.1 host.docker.internal` to your `/etc/hosts` file to allow DNS to resolve docker internal

```sh
sh /path/to/directory/kafka-topics.sh --list --bootstrap-server host.docker.internal:9092
```

Commands can also be run from host machine using `docker-compose exec` command

```sh
docker-compose -f kafka-docker-compose.yaml exec kafka-1 /opt/bitnami/kafka/bin/kafka-topics.sh --list --bootstrap-server host.docker.internal:9092
```

## Kafka CLI Commands

Create new topic with specified partitions and replication factor

```sh
sh /path/to/directory/kafka-topics.sh --create -topic test-topic2 --partitions 3 --replication-factor 2 --bootstrap-server localhost:9092
```

List and describe topics

```sh
sh /path/to/directory/kafka-topics.sh --list --bootstrap-server localhost:9092
sh /path/to/directory/kafka-topics.sh --describe --bootstrap-server localhost:9092
```

Delete a topic (permanently delete)

```sh
sh /path/to/directory/kafka-topics.sh --delete --topic topic1 --bootstrap-server localhost:9092
```

Send a message using Kafka CLI

```sh
sh /path/to/directory/kafka-console-producer.sh --bootstrap-server localhost:9092,localhost:9094 --topic test-topic2
```

Send key/value pair message using Kafka CLI

```sh
sh /path/to/directory/kafka-console-producer.sh --bootstrap-server localhost:9092,localhost:9094 --topic test-topic2 --property "parse.key=true" --property "key.separator=:"
```