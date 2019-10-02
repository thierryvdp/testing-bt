package insee.api.launcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.apache.log4j.Logger;

public class FileUtil {

	private final static Logger logger = Logger.getLogger(FileUtil.class);

	public static void closeAll(Closeable... closables) {
		for (Closeable closeable : closables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (IOException e) {
					logger.error("", e);
				} finally {
					// continue;
				}
			}
		}
	}

	public static void dump(String outputFileName, InputStream inputStream) throws Exception {
		FileOutputStream br = null;
		try {
			br = new FileOutputStream(outputFileName);
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				br.write(buf, 0, len);
			}
		} finally {
			closeAll(br, inputStream);
		}
	}

	public static void dump2XL(String outputFileName, List<List<String>> linesFields, char separator) {
		FileOutputStream br = null;
		BufferedWriter out = null;

		try {
			File file = new File(outputFileName);
			if (file.exists()) {
				file.delete();
			}

			out = new BufferedWriter(new FileWriter(file));

			for (List<String> line : linesFields) {
				for (String field : line) {
					out.write("\"" + field + "\"" + separator);
					out.flush();
				}
				out.write("\n");
				out.flush();
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			closeAll(br);
		}
	}

	public static String dump(InputStream inputStream) throws Exception {
		return dump(inputStream, null);
	}

	public static String load(String _fullInputPath, String _encoding) {
		try {
			FileInputStream inputStream = new FileInputStream(_fullInputPath);
			return dump(inputStream, _encoding);
		} catch (Exception e) {
			logger.error("fullInputPath:" + _fullInputPath, e);
		}
		return null;
	}

	public static String dump(InputStream inputStream, String encoding) throws Exception {
		if (inputStream != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			Reader reader = null;
			try {
				if (encoding == null) {
					reader = new BufferedReader(new InputStreamReader(inputStream));
				}
				else {
					reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
				}
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
			byte buf[] = new byte[1024];
			int len;
			while ((len = iStream.read(buf)) > 0) {
				writer.write(buf, 0, len);
			}
		} finally {
			closeAll(writer, iStream);
		}
	}

	public static void dump(String outputFileName, String stringToDump) throws Exception {
		FileOutputStream br = null;
		try {
			br = new FileOutputStream(outputFileName);
			br.write(stringToDump.getBytes());
		} finally {
			closeAll(br);
		}
	}

	public static boolean isFileEmpty(String fullPath) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fullPath));
			return br.readLine() == null;
		} catch (Exception e) {
			// alors c'est vide aussi !
			logger.error("", e);
		}
		return true;
	}

	public static boolean isFileExists(String fullPath) {
		File f = new File(fullPath);
		boolean exist = f.exists();
		exist = exist && !f.isDirectory();
		return exist;
	}

	public static boolean deleteFile(String fullPath) {
		try {
			File file = new File(fullPath);
			return file.delete();
		} catch (Exception e) {
			logger.error("", e);
		}
		return false;
	}

	public static boolean isFileInUse(String fullPath) {
		boolean isFree = false;
		if (fullPath != null && FileUtil.isFileExists(fullPath)) {
			try {
				FileInputStream fileInputStream = null;
				fileInputStream = new FileInputStream(new File(fullPath));
				isFree = true;
				fileInputStream.close();
			} catch (Exception e) {
				isFree = false;
			}
		}
		return !isFree;
	}

}
