#!/bin/bash
set -e

echo "Building wiremock image..."
(cd ./mocks && source ./build-image.sh)

echo "Building service image..."
source ./service/build-image.sh

echo "Starting services..."
docker-compose up
