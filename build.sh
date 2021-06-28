#!/bin/bash
GITBASE=iscr.io:5002
docker build --no-cache . -t $GITBASE/iii/npmon
#docker tag $GITBASE/npmon hylinktree/npmon

