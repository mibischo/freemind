package freemind.tools;

public interface IPlaceholderExpander {

	String expand(String message, String s1, String s2, String s3);

	String expand(String message, String s1, String s2);

	String expand(String message, String s1);

}
