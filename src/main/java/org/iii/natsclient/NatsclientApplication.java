package org.iii.natsclient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.iii.natsclient.service.dummy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.nats.examples.ExampleUtils;
import io.nats.examples.NatsReply;
import io.nats.examples.NatsReq;
import io.nats.examples.autobench.NatsAutoBench;
import io.nats.examples.autobench.mybench;
import io.nats.examples.jetstream.NatsJsPub;

final class Dispatcher {

	private static final Map<String, Class<?>> ENTRY_POINTS = new HashMap<String, Class<?>>();
	static {
		ENTRY_POINTS.put("NatsAutoBench", NatsAutoBench.class);
		// ENTRY_POINTS.put("bar", Bar.class);
		// ENTRY_POINTS.put("baz", Baz.class);
	}

	public static void main(final String[] args) // throws Exception{
	{

		if (args.length < 1) {
			// throw exception, not enough args
		}
		final Class<?> entryPoint = ENTRY_POINTS.get(args[0]);
		if (entryPoint == null) {
			return;
			// throw exception, entry point doesn't exist
		}
		final String[] argsCopy = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[0];
		try {
			entryPoint.getMethod("main", String[].class).invoke(null, (Object) argsCopy);
		} catch (Exception ex) {

		}

	}
}

@SpringBootApplication
public class NatsclientApplication {

	public static void main(String[] args) {
		Dispatcher.main(args);
		// int len;
		// if ((len = args.length) < 1) {
		// ExampleUtils.Trace("Usage");
		// return;
		// }
		// String mod = args[0];
		// args = Arrays.copyOfRange(args, 1, len);
		// switch (mod) {
		// case "NatsAutoBench":
		// NatsAutoBench.main(args);
		// break;
		// default:
		// ExampleUtils.Trace("No target to run");
		// break;
		// }

		// String[] ar = "-s hpcargo:4222 yysub 10".split(" ");
		// NatsReply.main(ar);
		// NatsJsPub.main(args);
		// new dummy().run(args);
		// SpringApplication.run(NatsclientApplication.class, args);
	}

}
