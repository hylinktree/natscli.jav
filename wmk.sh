pushd `dirname $0`
git fetch; git pull
./mvnw -Dmaven.test.skip=true -T 1C compile install
popd
