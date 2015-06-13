package freemind.encryption;

public class SingleDesEncrypter extends DesEncrypter {
	
	public SingleDesEncrypter(StringBuffer pPassPhrase) {
		super(pPassPhrase, "PBEWithMD5AndDES");
	}

}
