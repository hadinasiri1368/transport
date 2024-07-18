# Specify the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY transport-0.0.1-SNAPSHOT.jar /app/transport-0.0.1-SNAPSHOT.jar

# Expose the port that your application runs on (default is 8080)
EXPOSE 8082

# Specify the command to run your application
CMD ["java", "-jar", "transport-0.0.1-SNAPSHOT.jar"]

#sudo docker build -t docker-image-transport .

#sudo docker network create \
#  --subnet=192.168.100.0/24 \
#  --gateway=192.168.100.1 \
#  my-network

#sudo docker run -d \
#  --name=docker-image-transport \
#  --network=my-network \
#  --ip=192.168.100.13 \
#  docker-image-transport

#sudo docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' docker-image-transport
