FROM amazoncorretto:11
COPY target/*.jar wishlist.jar
ENTRYPOINT ["java", "-jar", "/wishlist.jar"]
EXPOSE 9095