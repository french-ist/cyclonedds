package HelloWorldData;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class HelloWorldData_Msg extends Structure {
	public int userID;
	/** C type : char* */
	public Pointer message;
	public HelloWorldData_Msg() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("userID", "message");
	}
	/** @param message C type : char* */
	public HelloWorldData_Msg(int userID, Pointer message) {
		super();
		this.userID = userID;
		this.message = message;
	}
	public HelloWorldData_Msg(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends HelloWorldData_Msg implements Structure.ByReference {
		
	};
	public static class ByValue extends HelloWorldData_Msg implements Structure.ByValue {
		
	};
}