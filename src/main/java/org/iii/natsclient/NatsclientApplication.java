package org.iii.natsclient;

import org.iii.natsclient.service.dummy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.nats.examples.jetstream.NatsJsPub;

@SpringBootApplication
public class NatsclientApplication {

	public static void main(String[] args) {
		NatsJsPub.main(args);
		// new dummy().run(args);
		// SpringApplication.run(NatsclientApplication.class, args);
	}

}
