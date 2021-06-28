package org.iii.natsclient.service;

import io.nats.client.AuthHandler;
import io.nats.client.Connection;
import io.nats.client.Consumer;
import io.nats.client.ErrorListener;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.nats.client.impl.NatsMessage;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class dummy {
    static final String usageString = "\nUsage: java -cp <classpath> NatsPub [-s server] [-r headerKey:headerValue]* <subject> <message>\n"
            + "\nUse tls:// or opentls:// to require tls, via the Default SSLContext\n"
            + "\nSet the environment variable NATS_NKEY to use challenge response authentication by setting a file containing your private key.\n"
            + "\nSet the environment variable NATS_CREDS to use JWT/NKey authentication by setting a file containing your user creds.\n"
            + "\nUse the URL for user/pass/token authentication.\n";

    public static Options createOptions(String server) throws Exception {
        Options.Builder builder = new Options.Builder().server(server).connectionTimeout(Duration.ofSeconds(5))
                .pingInterval(Duration.ofSeconds(10)).reconnectWait(Duration.ofSeconds(1)).maxReconnects(-1)
                .traceConnection();

        builder = builder.connectionListener((conn, type) -> System.out.println("Status change " + type));

        builder = builder.errorListener(new ErrorListener() {
            @Override
            public void slowConsumerDetected(Connection conn, Consumer consumer) {
                System.out.println("NATS connection slow consumer detected");
            }

            @Override
            public void exceptionOccurred(Connection conn, Exception exp) {
                System.out.println("NATS connection exception occurred");
                exp.printStackTrace();
            }

            @Override
            public void errorOccurred(Connection conn, String error) {
                System.out.println("NATS connection error occurred " + error);
            }
        });

        if (System.getenv("NATS_NKEY") != null && System.getenv("NATS_NKEY") != "") {
            AuthHandler handler = new ExampleAuthHandler(System.getenv("NATS_NKEY"));
            builder.authHandler(handler);
        } else if (System.getenv("NATS_CREDS") != null && System.getenv("NATS_CREDS") != "") {
            builder.authHandler(Nats.credentials(System.getenv("NATS_CREDS")));
        }

        return builder.build();
    }

    public void run(String[] args) {
        String srv = "hpcargo:4222";
        ExampleArgs exArgs = ExampleUtils.expectSubjectAndMessage(args, usageString);

        try {
            System.out.println();
            System.out.printf("Timing connect time to %s.\n", exArgs.server);
            System.out.println();

            Options options = createOptions(exArgs.server);

            long start = System.nanoTime();
            Connection nc = Nats.connect(options);
            long end = System.nanoTime();
            double seconds = ((double) (end - start)) / 1_000_000_000.0;
            System.out.printf("Connect time to %s was %.3f seconds\n", exArgs.server, seconds);

            nc.close();

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}