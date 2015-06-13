package freemind.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Compression implements ICompression {

	public String compress(String message) {
		byte[] input = uTF8StringToByteArray(message);
		// Create the compressor with highest level of compression
		Deflater compressor = new Deflater();
		compressor.setLevel(Deflater.BEST_COMPRESSION);

		// Give the compressor the data to compress
		compressor.setInput(input);
		compressor.finish();

		// Create an expandable byte array to hold the compressed data.
		// You cannot use an array that's the same size as the orginal because
		// there is no guarantee that the compressed data will be smaller than
		// the uncompressed data.
		ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

		// Compress the data
		byte[] buf = new byte[1024];
		while (!compressor.finished()) {
			int count = compressor.deflate(buf);
			bos.write(buf, 0, count);
		}
		try {
			bos.close();
		} catch (IOException e) {
		}

		// Get the compressed data
		byte[] compressedData = bos.toByteArray();
		return Base64Coding.encode64(compressedData);
	}

	public String decompress(String compressedMessage) {
		byte[] compressedData = Base64Coding.decode64(compressedMessage);
		// Create the decompressor and give it the data to compress
		Inflater decompressor = new Inflater();
		decompressor.setInput(compressedData);

		// Create an expandable byte array to hold the decompressed data
		ByteArrayOutputStream bos = new ByteArrayOutputStream(
				compressedData.length);

		// Decompress the data
		byte[] buf = new byte[1024];
		boolean errorOccured = false;
		while (!decompressor.finished() && !errorOccured) {
			try {
				int count = decompressor.inflate(buf);
				bos.write(buf, 0, count);
			} catch (DataFormatException e) {
				errorOccured = true;
			}
		}
		try {
			bos.close();
		} catch (IOException e) {
		}

		// Get the decompressed data
		byte[] decompressedData = bos.toByteArray();
		return byteArrayToUTF8String(decompressedData);
	}

	/**
     */
	private String byteArrayToUTF8String(byte[] compressedData) {
		// Decode using utf-8
		try {
			return new String(compressedData, "UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF8 packing not allowed");
		}
	}

	/**
     */
	private byte[] uTF8StringToByteArray(String uncompressedData) {
		// Code using utf-8
		try {
			return uncompressedData.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF8 packing not allowed");
		}
	}
}
