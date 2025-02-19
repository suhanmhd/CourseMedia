FROM openjdk:21
EXPOSE 9090

ADD target/course-media.jar course-media.jar
ENTRYPOINT ["java","-jar","/course-media.jar"]


