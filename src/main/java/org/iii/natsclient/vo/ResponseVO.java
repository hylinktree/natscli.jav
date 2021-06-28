package org.iii.natsclient.vo;

import org.iii.natsclient.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseVO {
	/*
	 * public class LogVO extends HashMap<Integer, String> { // private static final
	 * long serialVersionUID = 1L;
	 * 
	 * 
	 * public LogVO add(String si) { sout += String.format("%04d:%s\n", lines, si);
	 * lines++; return this; }
	 * 
	 * @Override public String toString() { return sout; /*
	 * 
	 * } }
	 */
	public static int _pretty = 0;
	private String status = "ok";
	private String error = "no error";
	public String payload;
	public Object misc;
	public Object report;

	// private LogVO log = new LogVO();
	public ResponseVO() {
	}

	public boolean isOK(){
		return status.equals("ok") && error.equals("no error");
	}

	public ResponseVO(String sta, String err) {
		this.status = sta;
		this.error = err;
	}

	public String getStatus() {
		return this.status;
	}

	public ResponseVO setPayload(String pay) {
		this.payload = pay;
		return this;
	}

	public ResponseVO setReport(Object ob) {
		this.report = ob;
		return this;
	}

	/*
	 * public ResponseVO setMisc(String si) { this.misc = Utils.fromJson(si, );
	 * return this; }
	 */
	public ResponseVO setMisc(String si, Class<?> classOfT) {
		this.misc = Utils.fromJson(si, classOfT);
		return this;
	}

	public ResponseVO setMisc(String si) {
		Object ob = new Gson().fromJson(si, Object.class);
		this.misc = ob;
		return this;
	}

	public ResponseVO setMisc(Object ob) {
		this.misc = ob;
		return this;
	}

	public ResponseVO setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getErr() {
		return this.error;
	}

	public ResponseVO setErr(String err) {
		// Utils.Trace("!!SetErr=", err);
		this.error = err;
		return this;
	}

	public ResponseVO setErr(Exception ex) {
		// Utils.Trace("!!SetErr=", ex.getMessage());
		this.error = ex.getMessage();
		return this;
	}

	@Override
	public String toString() {
		String so;
		if (_pretty == 0)
			so = new Gson().toJson(this);
		else
			so = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		// Utils.Trace(so);
		return so;
	}

}
