#!/bin/bash
GITBASE=iscr.io:5002
TARGET=iii/nats-test

docker build --no-cache . -t $GITBASE/$TARGET

