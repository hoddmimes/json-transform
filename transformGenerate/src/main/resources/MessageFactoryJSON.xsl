<xsl:stylesheet version="3.0"
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
				xmlns:xs="http://www.w3.org/2001/XMLSchema"
				xmlns:map="http://www.w3.org/2005/xpath-functions/map"
				xmlns:functx="http://www.functx.com"
				exclude-result-prefixes="map">

	<xsl:output method="text"/>
	<xsl:param name="outPath"/>
	<xsl:param name="package"/>

	<xsl:include href="functx-1.0.1.xsl"/>

<xsl:template match="/MessagesRoot">

	<xsl:variable name="file" select="concat('file://',$outPath,'MessageFactory.java')"/>
	<xsl:result-document href="{$file}" method="text" omit-xml-declaration="yes" encoding="utf-8">

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

import com.hoddmimes.transform.*;
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
		<xsl:message>Created file <xsl:value-of select="$file"/></xsl:message>
	</xsl:result-document>
</xsl:template>


</xsl:stylesheet>
