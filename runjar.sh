if [[ $# < 2 ]]; then
    echo usage:
    echo java -cp target/natscli-0.0.1-SNAPSHOT.jar -Dloader.main=io.nats.examples.jetstream.NatsJsPubAsync org.springframework.boot.loader.PropertiesLauncher -help
    exit
fi
jar=$1
shift
entry=$1
shift
java -cp $jar -Dloader.main=$entry org.springframework.boot.loader.PropertiesLauncher $*
