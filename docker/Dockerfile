FROM postgres:latest

LABEL maintainer="William Dias"

# RUN apt-get update
# RUN apt install postgresql-client-common

EXPOSE 5432

COPY $PWD/scriptSQL/*.sql /docker-entrypoint-initdb.d/