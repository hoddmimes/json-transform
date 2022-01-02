<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
				version="1.0"
				xmlns:java="http://xml.apache.org/xslt/java"
				xmlns:extensions ="com.hoddmimes.jtransform.Transform$Extensions"
				xmlns:redirect   ="com.hoddmimes.jtransform.Transform$Redirect"
				extension-element-prefixes="redirect extensions"
				xmlns:xalan="http://xml.apache.org/xslt"
				exclude-result-prefixes="xalan java">

	<xsl:output method="text"/>
	<xsl:param name="outPath"/>
	<xsl:param name="package"/>



<xsl:template match="/MessagesRoot">

<redirect:write select="concat($outPath,'MessageFactory.java')">
/*
* Copyright (c)  Hoddmimes Solution AB 2021.
*
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package <xsl:value-of select="$package"/>;

import com.hoddmimes.jsontransform.*;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NameNotFoundException;

	<xsl:for-each select="Messages">
import <xsl:value-of select="@package"/>.*;
	</xsl:for-each>

@SuppressWarnings({"WeakerAccess","unused","unchecked"})
public class MessageFactory implements MessageFactoryInterface
{
	public static Pattern JSON_MESSAGE_NAME_PATTERN = Pattern.compile("^\\s*\\{\\s*\"(\\w*)\"\\s*:\\s*\\{");


	public String getJsonMessageId( String pJString ) throws NameNotFoundException
	{
		Matcher tMatcher = JSON_MESSAGE_NAME_PATTERN.matcher(pJString);
		if (tMatcher.find()) {
		  return tMatcher.group(1);
		}
		throw new NameNotFoundException("Failed to extract message id from JSON message");
	}

	@Override
	public MessageInterface getMessageInstance(String pJsonMessageString) {
		String tMessageId = null;

		try { tMessageId = getJsonMessageId( pJsonMessageString ); }
		catch( NameNotFoundException e ) { return null; }
	
		switch( tMessageId ) 
		{
<xsl:for-each select="Messages">
<xsl:for-each select="Message">
			<xsl:if test="@rootMessage='true'">
            case "<xsl:value-of select="@name"/>":
            {
            	<xsl:value-of select="@name"/> tMessage = new <xsl:value-of select="@name"/>();
            	tMessage.decode( new JsonDecoder(pJsonMessageString));
            	return tMessage;
            }
			</xsl:if>
</xsl:for-each>
</xsl:for-each>
            default:
              return null;
		}	
	}
}

</redirect:write>
</xsl:template>


</xsl:stylesheet>
