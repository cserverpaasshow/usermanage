FROM tomcat:latest
RUN rm -rf /usr/local/tomcat/webapps/*
copy  target/usermanage.war  /usr/local/tomcat/webapps
