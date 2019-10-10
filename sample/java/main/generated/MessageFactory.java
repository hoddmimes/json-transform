
package generated;

import com.hoddmimes.jsontransform.*;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

	

@SuppressWarnings({"WeakerAccess","unused","unchecked"})
public class MessageFactory implements MessageFactoryInterface
{
	public static Pattern JSON_MESSAGE_NAME_PATTERN = Pattern.compile("^\\s*\\{\\s*\"(\\w*)\"\\s*:\\s*\\{");


	private String getJsonMessageId( String pJString )
	{
		Matcher tMatcher = JSON_MESSAGE_NAME_PATTERN.matcher(pJString);
		if (tMatcher.find()) {
		  return tMatcher.group(1);
		}
		throw new RuntimeException("Failed to extract message from JSON message");
	}

	@Override
	public MessageInterface getMessageInstance(String pJsonMessageString) {

	  	String tMessageId = getJsonMessageId( pJsonMessageString );
	
		switch( tMessageId ) 
		{

            case "TestMessage":
            {
            	TestMessage tMessage = new TestMessage();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			
            default:
              return null;
		}	
	}
}

