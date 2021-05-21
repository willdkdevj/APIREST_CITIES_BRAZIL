#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "postgres_supernova_user" --dbname "citiesBrazil" <<-EOSQL
    CREATE USER postgres_supernova_user;
    CREATE DATABASE citiesBrazil;
    GRANT ALL PRIVILEGES ON DATABASE citiesBrazil TO postgres_supernova_user;
EOSQL