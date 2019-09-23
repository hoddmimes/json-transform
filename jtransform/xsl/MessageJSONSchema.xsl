<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0"
                xmlns:java="http://xml.apache.org/xslt/java"
                xmlns:extensions="com.hoddmimes.jtransform.Transform$Extensions"
                xmlns:redirect="com.hoddmimes.jtransform.Transform$Redirect"
                extension-element-prefixes="redirect extensions"
                xmlns:xalan="http://xml.apache.org/xslt"
                exclude-result-prefixes="xalan java">

    <xsl:output method="text"/>
    <xsl:param name="outPath"/>
    <xsl:param name="schemaDir"/>



    <xsl:variable name="TypeTableDefinitions">
        <DataTypes>
            <Type name="boolean" jtype="boolean" />
            <Type name="byte" jtype="integer" />
            <Type name="short" jtype="integer" />
            <Type name="int" jtype="integer" />
            <Type name="char" jtype="integer" />
            <Type name="long" jtype="integer" />
            <Type name="double" jtype="number" />
            <Type name="String" jtype="string" />
            <Type name="byte[]" jtype="string" />
        </DataTypes>
    </xsl:variable>

    <xsl:variable name="typeTable" select="xalan:nodeset($TypeTableDefinitions)/DataTypes"/>


    <xsl:template match="/MessagesRoot">
        <xsl:for-each select="Messages">
            <xsl:for-each select="Message">
                <redirect:write select="concat($outPath,@name,'.json')">
 {
   "$schema": "http://json-schema.org/draft-06/schema#",
   "title": "<xsl:value-of select='@name'/>",
   "type": "object",
     "properties" : { <xsl:if test="@rootMessage='true'">
     "<xsl:value-of select="@name"/>" : {
       "type" : "object",
       "properties" : { </xsl:if>
   <xsl:apply-templates mode="generateSchemaAttributes" select="."/>
     }<xsl:apply-templates mode="generateRequiredAttributes" select="."/><xsl:if test="@rootMessage='true'">
    }
   },
   "required" : ["<xsl:value-of select='@name'/>"]</xsl:if>
}
                </redirect:write>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>


<xsl:template mode="generateRequiredAttributes" match="Message">
    <xsl:if test="Attribute[@mandatory='true']">,
     "required": [<xsl:for-each select="Attribute[@mandatory='true']">"<xsl:value-of select="@name"/>"<xsl:if test="position() != last()">, </xsl:if></xsl:for-each>]
    </xsl:if>
</xsl:template>


<xsl:template mode="generateSchemaAttributes" match="Message">

    <xsl:for-each select="Attribute">
        <xsl:if test="@array">
            <xsl:apply-templates mode="generateSchemaArrayAttributes" select=".">
                <xsl:with-param name="lastPos" select="position()=last()"/>
            </xsl:apply-templates>
        </xsl:if>
        <xsl:if test="not(@array)">
            <xsl:apply-templates mode="generateSchemaSingelAttributes" select=".">
                <xsl:with-param name="lastPos" select="position()=last()"/>
            </xsl:apply-templates>
        </xsl:if>
    </xsl:for-each>
</xsl:template>

<xsl:template mode="generateSchemaArrayAttributes" match="Attribute">
    <xsl:param name="lastPos"/>
    <!--xsl:message>[ARRAY] Position: <xsl:value-of select='$lastPos'/> Attribute: <xsl:value-of select="@name"/></xsl:message -->
    <xsl:if test="@constantGroup">
        <xsl:variable name="cgName" select="@constantGroup"/>
          "<xsl:value-of select="@name"/>" : {
            "type" : "array",
            "items" : { "type" : "string", "enum" : [<xsl:for-each select="/*/*/ConstantGroups/Group[@name=$cgName]/Constant">"<xsl:value-of select='@id'/>"<xsl:if test="not(position()=last())">, </xsl:if></xsl:for-each>] }
          }<xsl:if test="not($lastPos)">,</xsl:if>
    </xsl:if>
    <xsl:if test="not(@constantGroup)">
        <xsl:variable name="dataType" select="@type"/>
        <xsl:variable name="jType" select="$typeTable/Type[@name=$dataType]/@jtype"/>
        <xsl:if test="$typeTable/Type[@name=$dataType]">
          "<xsl:value-of select="@name"/>" : {
            "items" : { <xsl:if test="@pattern">"pattern": "<xsl:value-of select="@pattern"/>", </xsl:if><xsl:if test="@maxLength">"maxLength": <xsl:value-of select="@maxLength"/>, </xsl:if><xsl:if test="@minLength">"minLength": <xsl:value-of select="@minLength"/>, </xsl:if><xsl:if test="@min">"minimum": <xsl:value-of select="@min"/>, </xsl:if><xsl:if test="@max">"maximum": <xsl:value-of select="@max"/>, </xsl:if><xsl:if test="@multiplieOf">"multiplieOf": <xsl:value-of select="@multiplieOf"/>, </xsl:if>"type" : "<xsl:value-of select="$jType"/>" },
            "type" : "array"
          }<xsl:if test="not($lastPos)">,</xsl:if>
        </xsl:if>
        <xsl:if test="not($typeTable/Type[@name=$dataType])">
          "<xsl:value-of select="@name"/>" : {
            "type" : "array",
            "items" : { "$ref": "file:<xsl:value-of select="$schemaDir"/><xsl:value-of select="$dataType"/>.json" }
          }<xsl:if test="not($lastPos)">,</xsl:if>
        </xsl:if>
    </xsl:if>
</xsl:template>




<xsl:template mode="generateSchemaSingelAttributes" match="Attribute">
    <xsl:param name="lastPos"/>
    <!--xsl:message>[SINGLE] Position: <xsl:value-of select='$lastPos'/> Attribute: <xsl:value-of select="@name"/></xsl:message -->
    <xsl:if test="@constantGroup">
        <xsl:variable name="cgName" select="@constantGroup"/>
          "<xsl:value-of select="@name"/>" : { "type" : "string", "enum" : [<xsl:for-each select="/*/*/ConstantGroups/Group[@name=$cgName]/Constant">"<xsl:value-of select='@id'/>"<xsl:if test="not(position()=last())">, </xsl:if></xsl:for-each>] }<xsl:if test="not($lastPos)">, </xsl:if></xsl:if>
    <xsl:if test="not(@constantGroup)">
        <xsl:variable name="dataType" select="@type"/>
        <xsl:variable name="jType" select="$typeTable/Type[@name=$dataType]/@jtype"/>
        <xsl:if test="$typeTable/Type[@name=$dataType]">
          <xsl:apply-templates mode="generateSchemaSingelStdAttributes" select=".">
              <xsl:with-param name="jType" select="$jType"/>
              <xsl:with-param name="lastPos" select="$lastPos"/>
          </xsl:apply-templates>
        </xsl:if>
        <xsl:if test="not($typeTable/Type[@name=$dataType])">
          "<xsl:value-of select='@name'/>" : { "$ref" : "file:<xsl:value-of select="$schemaDir"/><xsl:value-of select="$dataType"/>.json" }<xsl:if test="not($lastPos)">,</xsl:if></xsl:if>
    </xsl:if>
</xsl:template>


<xsl:template mode="generateSchemaSingelStdAttributes" match="Attribute">
    <xsl:param name="jType"/>
    <xsl:param name="lastPos"/>
          "<xsl:value-of select='@name'/>" : { <xsl:if test="@pattern">"pattern": "<xsl:value-of select="@pattern"/>", </xsl:if><xsl:if test="@maxLength">"maxLength": <xsl:value-of select="@maxLength"/>, </xsl:if><xsl:if test="@minLength">"minLength": <xsl:value-of select="@minLength"/>, </xsl:if><xsl:if test="@min">"minimum": <xsl:value-of select="@min"/>, </xsl:if><xsl:if test="@max">"maximum": <xsl:value-of select="@max"/>, </xsl:if><xsl:if test="@multiplieOf">"multiplieOf": <xsl:value-of select="@multiplieOf"/>, </xsl:if>"type" : "<xsl:value-of select="$jType"/>" }<xsl:if test="not($lastPos)">,</xsl:if>
</xsl:template>
</xsl:stylesheet>