package freemind.tools;

public interface ICompression {
	
	public String compress(String message);
	
	public String decompress(String compressedMessage);

}
