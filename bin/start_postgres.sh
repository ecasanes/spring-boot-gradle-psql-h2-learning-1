#!/usr/bin/env bash

set -euo pipefail
which psql > /dev/null || (echo "Please ensure that postgres client is in your PATH" && exit 1)

## install psql
## https://blog.timescale.com/tutorials/how-to-install-psql-on-mac-ubuntu-debian-windows/
## https://stackoverflow.com/questions/44654216/correct-way-to-install-psql-without-full-postgres-on-macos

mkdir -p $HOME/docker/volumes/postgres
rm -rf $HOME/docker/volumes/postgres/data

docker run --rm --name pg-docker -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=dev -d -p 5432:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgresql postgres
sleep 3
export PGPASSWORD=postgres
psql -U postgres -d dev -h localhost -f schema.sql
psql -U postgres -d dev -h localhost -f data.sql