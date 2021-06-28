package org.iii.natsclient;

import org.iii.natsclient.service.dummy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.nats.examples.NatsReply;
import io.nats.examples.NatsReq;
import io.nats.examples.jetstream.NatsJsPub;

@SpringBootApplication
public class NatsclientApplication {

	public static void main(String[] args) {
		String[] ar = "-s hpcargo:4222 yysub 10".split(" ");
		NatsReply.main(ar);
		// NatsJsPub.main(args);
		// new dummy().run(args);
		// SpringApplication.run(NatsclientApplication.class, args);
	}

}
