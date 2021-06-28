pushd `dirname $0`
git fetch; git pull
./mvnw -Dmaven.test.skip=true -T 1C compile install
#./mvnw -Dmaven.test.skip=true -T 1C -Xlint:deprecation compile install
popd
