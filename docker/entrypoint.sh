#!/bin/sh

set -e

bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://simulator:8100/index.html)" != "200" ]]; do sleep 5; done'

exec "$@"
