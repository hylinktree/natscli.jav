#!/bin/bash
GITBASE=iscr.io:5002
TARGET=iii/nats-test
pushd `dirname $0`
git fetch; git pull
mvn -Dmaven.test.skip=true -T 1C compile install
docker build --no-cache . -t $GITBASE/$TARGET
docker push $GITBASE/$TARGET
popd
