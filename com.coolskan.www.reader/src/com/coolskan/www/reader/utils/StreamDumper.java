package com.coolskan.www.reader.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;


public class StreamDumper {

	public static void dump(String outputFileName, InputStream inputStream) throws Exception {
		FileOutputStream br = null;
		try {
			br = new FileOutputStream(outputFileName);
			byte buf[]=new byte[1024];
			int len;
			while((len=inputStream.read(buf))>0)
				br.write(buf,0,len);
		} finally {
			InterfacesOperations.closeAll(br, inputStream);
		}
	}

	public static String dump(InputStream inputStream) throws Exception {
		if (inputStream != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				inputStream.close();
			}
			return writer.toString();
		}        
		return "";
	}

	public static void dump(OutputStream writer, InputStream iStream) throws Exception {
		try {
			byte buf[]=new byte[1024];
			int len;
			while((len=iStream.read(buf))>0)
				writer.write(buf,0,len);
		} finally {
			InterfacesOperations.closeAll(writer, iStream);
		}
	}

	public static void dump(String outputFileName, String stringToDump) throws Exception {
		FileOutputStream br = null;
		try {
			br = new FileOutputStream(outputFileName);
			br.write(stringToDump.getBytes());
		} finally {
			InterfacesOperations.closeAll(br);
		}
	}


}
