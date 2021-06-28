package org.iii.natsclient;

import java.util.Arrays;

import org.iii.natsclient.service.dummy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.nats.examples.ExampleUtils;
import io.nats.examples.NatsReply;
import io.nats.examples.NatsReq;
import io.nats.examples.autobench.mybench;
import io.nats.examples.jetstream.NatsJsPub;

@SpringBootApplication
public class NatsclientApplication {

	public static void main(String[] args) {
		int len;
		if ((len = args.length) < 1) {
			ExampleUtils.Trace("Usage");
			return;
		}
		String mod = args[0];
		args = Arrays.copyOfRange(args, 1, len);
		switch (mod) {
			case "mybench":
				mybench.main(args);
				break;
			default:
				ExampleUtils.Trace("No target to run");
				break;
		}
		// String[] ar = "-s hpcargo:4222 yysub 10".split(" ");
		// NatsReply.main(ar);
		// NatsJsPub.main(args);
		// new dummy().run(args);
		// SpringApplication.run(NatsclientApplication.class, args);
	}

}
