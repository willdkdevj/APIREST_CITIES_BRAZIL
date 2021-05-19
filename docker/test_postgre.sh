#!/bin/bash
docker run --name test-citiesBrazilDb -d -p 5432:5432 -e POSTGRES_USER=postgres_supernova_user -e POSTGRES_PASSWORD=supernova_pass -e POSTGRES_DB=citiesBrazil -v $PWD/scriptSQL:/tmp postgres

docker exec test-citiesBrazilDb bash -c "cd tmp; psql -U postgres_supernova_user citiesBrazil -f pais.sql; "

# docker exec -it test-citiesBrazilDb psql -h localhost -U postgres_supernova_user citiesBrazil -f tmp/pais.sql

# docker exec -it test-citiesBrazilDb psql -h localhost -U postgres_supernova_user citiesBrazil -f tmp/estado.sql

# docker exec -it test-citiesBrazilDb psql -h localhost -U postgres_supernova_user citiesBrazil -f tmp/cidade.sql
