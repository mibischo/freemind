package freemind.encryption;

public class TripleDesEncrypter extends DesEncrypter {
	
	public TripleDesEncrypter(StringBuffer pPassPhrase) {
		super(pPassPhrase, "PBEWithMD5AndTripleDES");
	}
}
