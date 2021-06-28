package org.iii.natsclient.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
// import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
// import org.apache.poi.ss.usermodel.Row;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static final String prog = "npmon";
	public static int useSysLog = 1;
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	private static final SimpleDateFormat _simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HH:mm:ss", Locale.ENGLISH);
	private static final SimpleDateFormat _simpleBackupDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss",
			Locale.ENGLISH);
	private static final SimpleDateFormat _simplePythonDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
			Locale.ENGLISH);
	private static final DateTimeFormatter _isoDateTimefmtter = ISODateTimeFormat.dateTime();
	private static final TimeZone _timezone = TimeZone.getDefault();
	private static final ZoneOffset _zoneoffset = ZoneOffset.ofTotalSeconds(_timezone.getRawOffset() / 1000);
	private static final DateTimeFormatter _jodaDateTimeFormatter;// = DateTimeFormat.forPattern("yyyyMMdd-HHmmss");
	private static final DateTimeFormatter _jodaDateTimeFormatter2;// = DateTimeFormat.forPattern("yyyyMMdd-HHmm");
	private static final DateTimeFormatter _jodaDateTimeFormatterPythonA;// = DateTimeFormat.forPattern("yyyy-MM-dd");
	private static final DateTimeFormatter _jodaDateTimeFormatterPythonB;// = DateTimeFormat .forPattern("yyyy-MM-dd
																			// HH:mm:ss");
	// private static final DateTimeFormatter _jodaDateTimeFormatter4;

	static {
		_jodaDateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd-HHmmss");
		_jodaDateTimeFormatter2 = DateTimeFormat.forPattern("yyyyMMdd-HHmm");
		// _jodaDateTimeFormatter4 = DateTimeFormat.forPattern("yyyyMMdd-HHmm");
		_jodaDateTimeFormatterPythonA = DateTimeFormat.forPattern("yyyy-MM-dd");
		_jodaDateTimeFormatterPythonB = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	}
	public static final long _UTCSundayOffsetInMilli = //
			1553990400000L; // SUN 2019-03-31 00:00:00 UTC

	/*
	 * public static long getTimeZoneOffset() { return _timezone.getRawOffset(); }
	 */

	public static long TimeZoneOffsetMillis;
	public static long TimeZoneOffsetSeconds;
	public static int TimeZoneOffsetHours;

	static {
		TimeZoneOffsetMillis = _timezone.getRawOffset();
		TimeZoneOffsetSeconds = TimeZoneOffsetMillis / 1000L;
		TimeZoneOffsetHours = (int) (TimeZoneOffsetSeconds / 3600);
	}

	public static AtomicLong ac = new AtomicLong();

	public static int parseSizeString(String si) {
		Pattern pe = Pattern.compile("([\\d]+)([mMbBkKGg]{0,1})");
		Matcher me = pe.matcher(si);
		if (!me.find())
			return -1;
		if (me.groupCount() != 2)
			return -1;
		int n = Integer.parseInt(me.group(1));
		String p = me.group(2);
		if (p.toUpperCase().equals("K"))
			n *= 1024;
		if (p.toUpperCase().equals("M"))
			n *= 1024 * 1024;
		if (p.toUpperCase().equals("G"))
			n *= 1024 * 1024 * 1024;
		return n;

	}


	public static String loadResourceText(Object cls, String fid) {
		try (InputStream in = cls.getClass().getClassLoader().getResourceAsStream(fid)) //
		{
			// BufferedReader reader = new BufferedReader(new InputStreamReader(in,
			// "UTF-8"))) {
			// String so = new String(in.readAllBytes(), StandardCharsets.UTF_8);
			// return so;

			byte[] bytes = ByteStreams.toByteArray(in);
			return new String(bytes, StandardCharsets.UTF_8);

			// byte[] bs = IOUtils.toByteArray(in);
		} catch (Exception ex) {
			return "";
		}
	}

	public static byte[] loadResourceBinary(Object cls, String fid) {
		try (
				// FileInputStream fis = new FileInputStream(new File(path));
				InputStream in = cls.getClass().getClassLoader().getResourceAsStream(fid)) {
			byte[] bytes = ByteStreams.toByteArray(in);
			return bytes;
		} catch (Exception ex) {
			return null;
		}
	}

	public static void printException(Exception ex) {
		Utils.ErrTrace("EXCEPTION!!", ex.toString());
	}

	public static String fromFile(String path) { // , Charset encoding)
		try {
			Charset encoding = Charset.defaultCharset();
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, encoding);
		} catch (Exception ex) {
			ErrTrace("!!!fromFile Error!!");
			Trace(ex.getMessage());
			return "";
		}
	}

	public static List<String> getFile(String path) { // , Charset encoding)
		List<String> lst = new ArrayList<String>();
		try ( //
				FileInputStream fis = new FileInputStream(new File(path));
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));) {

			String line;
			for (; (line = reader.readLine()) != null;) {
				lst.add(line);
			}
			Collections.sort(lst);
			return lst;
		} catch (Exception ex) {
			Utils.ErrTrace("!!Fails to read file", path, ex.getMessage());
			return null;
		}
	}

	public static String fromFile(String path, Charset encoding) {
		try {
			String content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
			return content;
			// Charset encoding = Charset.defaultCharset();
			// byte[] encoded = Files.readAllBytes(Paths.get(path), StandardCharsets.UTF_8);
			// return new String(encoded, encoding);
		} catch (Exception ex) {
			Utils.printException(ex);
			return null;
		}
	}

	public static String getResourceFileName(Object ob, Path p) // throws URISyntaxException
	{
		URL u;
		try {
			u = ob.getClass().getClassLoader().getResource(p.toString());
			if (u == null) {
				Utils.Trace("NoResourceFileName");
				return "";
			}
			File f = new File(u.toURI());
			return f.toPath().toString();
		} catch (Exception ex) {
			Utils.ErrTrace("Error", ex.getMessage());
			return "";
		}
	}

	public static String inFile(Object cls, Path p) {
		String full;
		// full = p.toString();
		full = "js/yz.html";
		// InputStream inx = cls.getClass().getClassLoader().getResourceAsStream(full);
		// Uti
		try (InputStream in = cls.getClass().getClassLoader().getResourceAsStream(full);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));) {
			String so, line;
			so = "";
			for (; (line = reader.readLine()) != null;) {
				so += line;
			}
			return so;
		} catch (Exception ex) {
			return "";
		}
	}

	public static String loadResourceFile(Object cls, String full) {
		// String full;
		// full = p.toString();
		// full = "js/yz.html";
		// InputStream inx = cls.getClass().getClassLoader().getResourceAsStream(full);
		// Uti
		Utils.Trace("Loader.full=", full);
		try (InputStream in = cls.getClass().getClassLoader().getResourceAsStream(full);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));) {
			String so, line;
			so = "";
			for (; (line = reader.readLine()) != null;) {
				so += line;
			}
			return so;
		} catch (Exception ex) {
			return "";
		}
	}
	// FileInputStream fis = new FileInputStream(new File(path));
	// BufferedReader reader = new BufferedReader(new InputStreamReader(fis,
	// "UTF-8"));) {

	// String line;
	// for (; (line = reader.readLine()) != null;) {
	// lst.add(line);
	// }
	// Collections.sort(lst);
	// return lst;

	// }

	public static String getResourceDirectoryName(Object ob) {
		URL u;
		try {
			u = ob.getClass().getClassLoader().getResource("");
			File f = new File(u.toURI());
			return f.toPath().toString();
		} catch (Exception ex) {
			return "";
		}
	}

	public static String inFile(URL url) throws URISyntaxException {
		try {
			File f = new File(url.toURI());
			Utils.Trace("to load file ", f.toPath(), " ---> ", f.toPath().toString());
			return Utils.fromFile(f.toPath().toString());
			// return "";
			// String content = new String(Files.readAllBytes(Paths.get(path)),
			// StandardCharsets.UTF_8);
			// return content;
			// Charset encoding = Charset.defaultCharset();
			// byte[] encoded = Files.readAllBytes(Paths.get(path), StandardCharsets.UTF_8);
			// return new String(encoded, encoding);
		} catch (Exception ex) {
			Utils.printException(ex);
			return null;
		}
	}

	public static void CreateDir(String dir) {
		try {
			// Utils.Printf("create.dir %s", dir);
			File f = new File(dir);
			if (f.exists())
				return;
			f.mkdir();
		} catch (Exception ex) {
			Utils.printException(ex);
			System.exit(0);
		}
	}

	public static void toFile(String path, String so, String encode) {
		try {
			String fname = path; // Paths.get(path).
			Writer fstream = new OutputStreamWriter(new FileOutputStream(fname), encode);
			BufferedWriter writer = new BufferedWriter(fstream);
			writer.write(so);
			writer.close();
		} catch (Exception ex) {
			return;
		}
	}

	public static boolean RenewDir(String dir) {
		File f = new File(dir);
		// boolean bb = FileSystemUtils.deleteRecursively(f);
		// if (!bb) {
		// Utils.ErrPrintf("Fails to clear dir %s", dir);
		// return false;
		// }

		try {
			FileUtils.deleteDirectory(f);
			return f.mkdir();
		} catch (Exception ex) {
			Utils.printException(ex);
			return false;
		}
	}

	public static void appendFileLine(String path, String so) {

		File file = new File(path);
		try (FileWriter fr = new FileWriter(file, true); BufferedWriter br = new BufferedWriter(fr);) {
			br.write(so + "\n");

			// br.close();
			// fr.close();
		} catch (Exception ex) {
			Utils.ErrTrace(ex.getMessage());
			return;
		}
	}

	public static void moveFile(String fsrc, String fdst) {
		try {
			(new File(fsrc)).renameTo(new File(fdst));
		} catch (Exception ex) {
			return;
		}
	}

	public static void toFile(String path, String so) {
		try {
			String fname = path; // Paths.get(path).
			BufferedWriter writer = new BufferedWriter(new FileWriter(fname, false));
			writer.write(so);
			writer.close();
		} catch (Exception ex) {
			return;
		}
	}

	public static long round2Hour(long n) {
		return ((n / 1000) / 3600) * 3600 * 1000;
	}

	// Create a java.time.LocalDateTime from an epoch in ms, UTC
	public static java.time.LocalDateTime toLocalDateTime(long ns) {
		return java.time.LocalDateTime.ofEpochSecond(ns / 1000, 0, _zoneoffset);
	}

	public static long toMillisFromPython(String si) {
		try {
			return _jodaDateTimeFormatterPythonA.parseDateTime(si).getMillis();
		} catch (Exception e) {
			try {
				return _jodaDateTimeFormatterPythonB.parseDateTime(si).getMillis();
			} catch (Exception y) {
				return 0L;
			}
		}
	}

	public static long toMilliSeconds(String si) {
		try {
			return _jodaDateTimeFormatter.parseDateTime(si).getMillis();
		} catch (Exception e) {
			try {
				return _jodaDateTimeFormatter2.parseDateTime(si).getMillis();
			} catch (Exception y) {
				return 0L;
			}
		}
	}

	public static String toDate(long ms) {
		return _simpleDateFormat.format(ms);
	}

	public static String toPythonDate(long ms) {
		return _simplePythonDateFormat.format(ms);
	}

	public static String toBackupDate(long ms) {
		if (ms == 0)
			ms = System.currentTimeMillis();
		return _simpleBackupDateFormat.format(ms);
	}

	public static String toPythonDate(java.util.Date dt) {
		return _simplePythonDateFormat.format(dt.getTime());
	}

	public static void RemoveFiles(String... fnames) {
		for (String f : fnames) {
			try {
				Files.delete(new File(f).toPath());
			} catch (Exception e) {
			}
		}
	}

	public static boolean Exists(String fname) {
		return new File(fname).exists();
	}

	public static boolean CopyFile(String fsrc, String fdst) {
		try {
			RemoveFiles(fdst);
			Files.copy(new File(fsrc).toPath(), new File(fdst).toPath());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String Trace(Object... params) {
		String so = "";
		for (Object e : params) {
			so += e.toString();
		}
		if (useSysLog == 1) {
			logger.info(so);
			return so;
		}
		so = _isoDateTimefmtter.print(LocalDateTime.now()) + "[" + prog + "] - " + so;
		System.out.println(so);
		return so;
	}

	public static String ErrTrace(Object... params) {
		String so = "";
		for (Object e : params) {
			so += e.toString();
		}
		if (useSysLog == 1) {
			logger.warn(so);
			return so;
		}
		so = _isoDateTimefmtter.print(LocalDateTime.now()) + "[" + prog + "] - " + so;
		System.err.println(so);
		return so;
	}

	public static String Printf(String fmtter, Object... params) {
		String so = _isoDateTimefmtter.print(LocalDateTime.now()) + "[" + prog + "] - " + String.format(fmtter, params);
		if (useSysLog == 1) {
			logger.info(so);
			return so;
		}
		System.out.println(so);
		return so;
	}
	/*
	 * public static String NTrace(Object... params) { String so = ""; for (Object e
	 * : params) { so += e.toString(); } if (useSysLog == 1) { logger.info(so);
	 * return so; } //so = df.format(Calendar.getInstance().getTime()) + " - " + so;
	 * System.out.println(so); return so; }
	 */

	public static void Sleep(long ms) {
		try {
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}

	public static String toJson(Object ob) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(ob);
	}

	public static String toJson(Object ob, boolean pretty) {
		if (!pretty)
			return new Gson().toJson(ob);
		return new GsonBuilder().setPrettyPrinting().create().toJson(ob);
	}

	// <T> T fromJson(String json, Class<T> classOfT)
	public static <T> T fromJson(String si, Class<T> classOfT) {
		return new Gson().fromJson(si, classOfT);
	}

	public static String toMinutesString(long ms) {
		int i = (int) (((ms / 1000) + 59) / 60);
		int j = i % 60;
		i = i / 60;
		return String.format("%d:%02d", i, j);
	}

	public static int getLocalDaysOfWeek(long epoch) {
		int n = getLocalHoursOfWeek(epoch);
		return n / 24;
	}

	public static int getLocalHoursOfWeek(long epoch) { // Millis in UTC
		int m = (int) ((epoch - _UTCSundayOffsetInMilli) / 1000 / 60 / 60); // change to UTC Sunday hour
		return ((m + TimeZoneOffsetHours) % (24 * 7));
	}

	// public static boolean getBoolean(Row row, int idx) {
	// boolean bo;
	// try {
	// bo = row.getCell(idx).getBooleanCellValue();
	// return bo;
	// } catch (Exception ex) {

	// }
	// try {
	// String so = row.getCell(idx).getStringCellValue();
	// if (so.equals("0"))
	// return false;
	// return true;
	// } catch (Exception ex) {
	// return false;
	// }

	// }

	// public static String getDate(Row row, int idx) {
	// String so;
	// try {
	// so = Utils.toPythonDate(row.getCell(idx).getDateCellValue());
	// return so;
	// } catch (Exception ex) {
	// }
	// try {
	// so = row.getCell(idx).getStringCellValue();
	// return so;
	// } catch (Exception ex) {
	// return "";
	// }
	// }

	public static int Exec(String cmd) {
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);

			try (//
					InputStream is = p.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is, "BIG5"));) {
				String line = null;
				while ((line = br.readLine()) != null) {
					Utils.Trace(">>", line);
				}
				Utils.Trace("try wait");
				int rq = p.waitFor(); // Let the process finish.
				Utils.Trace("exec.ret=", rq);
				return rq;
			}
		} catch (Exception e) {
			Utils.printException(e);
			return -1;
		}
	}

	public static String Run(String cmd, String encode) {
		Charset charset;
		if (encode == null)
			charset = StandardCharsets.UTF_8;
		else
			charset = Charset.forName("BIG5");
		String so = "";
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);

			try (//
					InputStream in = p.getInputStream();
			// BufferedReader br = new BufferedReader(new InputStreamReader(in, charset))
			//
			) {
				byte[] bytes = ByteStreams.toByteArray(in);
				so = new String(bytes, charset);

				// byte[] bs = in.readAllBytes();
				// String line = null;
				// while ((line = br.readLine()) != null) {
				// Utils.Trace(">>", line);
				// }
				// Utils.Trace("try wait");
				int rq = p.waitFor(); // Let the process finish.
				// Utils.Trace("exec.ret=", rq);
				return so;
			}
		} catch (Exception e) {
			Utils.printException(e);
			return "";
		}
	}

}
