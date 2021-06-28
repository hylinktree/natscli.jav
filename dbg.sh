docker run --rm -it -p 8090:80 baso/npmon bash
docker pull hylinktree/npmon
docker run --rm -it -p 8077:80 -e NPMON_ROLE=master -e NPMON_TARGET=140.92.24.29 hylinktree/npmon bash