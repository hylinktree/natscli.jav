package org.iii.natsclient.endpoint;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

import org.iii.natsclient.util.Utils;

// import javax.servlet.ServletContext;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

import com.google.common.io.ByteStreams;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class ApiController {

	// @Value("${npmon.iperf}")
	// public String iperfname;

	// @Autowired
	// Tester _tester;

	public static final long _sch_init_delay = 1000L * 45;
	public static final long _sch_fixed_delay = 1000L * 30;

	@RequestMapping(value = "/load", method = RequestMethod.POST)
	public void postload( //
			// HttpServletResponse response, HttpServletRequest request)
			InputStream in) //
	{
		try {
			byte[] bytes = ByteStreams.toByteArray(in);
			Utils.Trace("total ", bytes.length, " uploaded");
		} catch (Exception ex) {
			Utils.printException(ex);
		}
	}

}