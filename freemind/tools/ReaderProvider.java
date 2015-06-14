package freemind.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import freemind.main.FreeMindMain;
import freemind.main.HtmlTools;
import freemind.main.Resources;
import freemind.main.Tools;

public class ReaderProvider {
	

	/**
	 * Creates a reader that pipes the input file through a XSLT-Script that
	 * updates the version to the current.
	 * 
	 * @throws IOException
	 */
	public static Reader getUpdateReader(Reader pReader, String xsltScript,
			FreeMindMain frame) throws IOException {
		StringWriter writer = null;
		InputStream inputStream = null;
		final java.util.logging.Logger logger = frame.getLogger(Tools.class
				.getName());
		logger.info("Updating the reader " + pReader
				+ " to the current version.");
		boolean successful = false;
		String errorMessage = null;
		try {
			// try to convert map with xslt:
			URL updaterUrl = null;
			updaterUrl = frame.getResource(xsltScript);
			if (updaterUrl == null) {
				throw new IllegalArgumentException(xsltScript + " not found.");
			}
			inputStream = updaterUrl.openStream();
			final Source xsltSource = new StreamSource(inputStream);
			// get output:
			writer = new StringWriter();
			final Result result = new StreamResult(writer);

			String fileContents = getFile(pReader);
			if (fileContents.length() > 10) {
				logger.info("File start before UTF8 replacement: '"
						+ fileContents.substring(0, 9) + "'");
			}
			fileContents = HtmlTools.removeInvalidXmlCharacters(fileContents);
			if (fileContents.length() > 10) {
				logger.info("File start after UTF8 replacement: '"
						+ fileContents.substring(0, 9) + "'");
			}
			final StreamSource sr = new StreamSource(new StringReader(
					fileContents));
			// Dimitry: to avoid a memory leak and properly release resources
			// after the XSLT transformation
			// everything should run in own thread. Only after the thread dies
			// the resources are released.
			class TransformerRunnable implements Runnable {
				private boolean successful = false;
				private String errorMessage;

				public void run() {
					// create an instance of TransformerFactory
					TransformerFactory transFact = TransformerFactory
							.newInstance();
					logger.info("TransformerFactory class: "
							+ transFact.getClass());
					Transformer trans;
					try {
						trans = transFact.newTransformer(xsltSource);
						trans.transform(sr, result);
						successful = true;
					} catch (Exception ex) {
						freemind.main.Resources.getInstance().logException(ex);
						errorMessage = ex.toString();
					}
				}

				public boolean isSuccessful() {
					return successful;
				}

				public String getErrorMessage() {
					return errorMessage;
				}
			}
			final TransformerRunnable transformer = new TransformerRunnable();
			Thread transformerThread = new Thread(transformer, "XSLT");
			transformerThread.start();
			transformerThread.join();
			logger.info("Updating the reader " + pReader
					+ " to the current version. Done."); // +
															// writer.getBuffer().toString());
			successful = transformer.isSuccessful();
			errorMessage = transformer.getErrorMessage();
		} catch (Exception ex) {
			Resources.getInstance().logException(ex, xsltScript);
			errorMessage = ex.getLocalizedMessage();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
		if (successful) {
			String content = writer.getBuffer().toString();
			// logger.info("Content before transformation: " + content);
			String replacedContent = HtmlTools.removeInvalidXmlCharacters(content);
			// logger.info("Content after transformation: " + replacedContent);
			return new StringReader(replacedContent);
		} else {
			return new StringReader("<map><node TEXT='"
					+ HtmlTools.toXMLEscapedText(errorMessage) + "'/></map>");
		}
	}

	/**
	 * In case of trouble, the method returns null.
	 * 
	 * @param pInputFile
	 *            the file to read.
	 * @return the complete content of the file. or null if an exception has
	 *         occured.
	 */
	public static String getFile(File pInputFile) {
		try {
			return getFile(getReaderFromFile(pInputFile));
		} catch (FileNotFoundException e) {
			freemind.main.Resources.getInstance().logException(e);
			return null;
		}
	}

	public static Reader getReaderFromFile(File pInputFile)
			throws FileNotFoundException {
		return new FileReader(pInputFile);
	}

	public static String getFile(Reader pReader) {
		StringBuffer lines = new StringBuffer();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(pReader);
			final String endLine = System.getProperty("line.separator");
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lines.append(line).append(endLine);
			}
			bufferedReader.close();
		} catch (Exception e) {
			freemind.main.Resources.getInstance().logException(e);
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception ex) {
					freemind.main.Resources.getInstance().logException(ex);
				}
			}
			return null;
		}
		return lines.toString();
	}

	

}
