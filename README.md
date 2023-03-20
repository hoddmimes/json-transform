# json-transform


This is a convenience tool for generating Java POJO Object, JSON Schemas, POJO Mongo Support and Message factory for the POJO objects generated,  based on XML definitions.

The generated Java POJO objects have the ability to encode itself to JSON objects, the objects could also instantiate themselves with JSON objects or JSON strings;

From the XML definitions the POJOs and corresponding JSON (basic) schemas could be generated. Furthermore there is support for the generated POJO messages to encode / decode them self to Mongo documents.
For more information about the Mongo support see section *Mongo Support* below.

It might and most likely is easier(?) to use Jackson for mapping between POJOs and JSON.
The advantages with json-transform are:
* In case a lot of messages are to be defined. This can be accomplished efficiently with a minimum of writing.
* The utility encoding/decoding is about 8-10 times faster than Jackson.



## Transformation / Generation
There is a Java program **JsonTransform.java** that takes one argument "-xml *transformation-definitions*.xml".  The program will read the definitions file and based on the definitions generate JAVA POJO classes, JSON Schemas and a MessageFactory.
The later can take a JSON string and create a corresponding POJO .

## XML Defintions
Samples are found in the directory ./samples/xml/
The definition file *(sample TestMessagesFileSet.xml)* defines what XML definition files that are going to be processed and where to generate the output.
It is possible to define references to other structure definitions files to be included i.e. linked references. This allows definitions to be defined in 
many files and packages.

## To Test
To test you can run a transformation of the sample definitions by execution the command
$ java -cp json-transform.1._x_.jar JsonTransform -xml ./xml/TestMessagesFileSet.xml

The file runGenerate.sh do this for you i.e. generate the test files.

_**There is also an online page where you can test transformation interactively. On the page there is 
also some addition help of how to define message objects .**_

_**[Online Json Transformation Testing](https://hoddmimes.com/transform/index.html)**_



## Sample TestMessageFileSet.xml

 <small>
    <?xml version="1.0" encoding="UTF-8"?>
    <MessageFiles>
       <!-- Generate a single message factory for ALL messages defined in the "MessageFile" files found -->
       <!-- If you do not like to generate a MessageFactory just comment out next line -->
       <MessageFactory outPath="./java/main/generated/" package="generated"/>

       <!-- Generates JSON schemas for the structures found in the "MessageFile" files found -->
       <!-- If you do not like to generate JSON schemas to be generated, comment out next line -->
       <SchemaFactory outPath="./jsonSchemas/" schemaDir="./samples/jsonSchemas/"/>

       <!-- Generates Mongo auxilliry CRUD and find methods for the structures defined in the "MessageFile" files found -->
       <!-- If you do not like to Mongo auxilliry support to be generated, comment out next line -->
       <MongoAux outPath="./java/main/generated/" package="generated"/>

       <!-- Gnerates POJO objects with JSON support based upon the defintion found in the file -->
       <!-- You may have multiple "MessageFile" entries -->
       <MessageFile  file="./xml/TestMessage.xml" outPath="./java/main/generated/" package="generated"/>
    </MessageFiles>

</small>

## Sample TestMessage.xml
<small>
<?xml version="1.0" encoding="UTF-8"?>
<Messages mongoSupport="true">

	<ConstantGroups prefix="Core">
		<Group name="CG1">
			<Constant id="X1"/>
			<Constant id="X2"/>
			<Constant id="X3"/>
			<Constant id="X4"/>
			<Constant id="X5"/>
		</Group>
		<Group name="CG2">
			<Constant id="Y1" value="500"/>
			<Constant id="Y2" value="501"/>
			<Constant id="Y3" value="502"/>
		</Group>
	</ConstantGroups>

	<Message name="TestSubMessage">
		<Attribute name="intValue" type="int" mandatory="true" min="42" max="84"/>
		<Attribute name="strValue" type="String"  pattern="foo[X-Z]+bar"/>
		<Attribute name="stringArray" type="String" list="array"/>
		<Attribute name="intArray" type="int" list="array"/>
	</Message>

	<Message name="TestMessage" rootMessage="true" db="true" dbCollection="Test">
		<Attribute name="stringArray" type="String" list="array"/>
		<Attribute name="constValue" constantGroup="CG1"/>
		<Attribute name="constArray" constantGroup="CG2" list="linked"/>
		<Attribute name="boolValue" type="boolean"/>
		<Attribute name="charValue" type="char"/>
		<Attribute name="byteValue" type="byte"/>
		<Attribute name="doubleValue" type="double"/>
		<Attribute name="shortValue" type="short" mandatory="true" min="0" max="100" multiplieOf="3" />
		<Attribute name="intValue" type="int" mandatory="true" dbKey="unique"/>
		<Attribute name="intArray" type="int" list="array"/>
		<Attribute name="longValue" type="long" />
		<Attribute name="strValue" type="String" minLength="3" maxLength="12" mandatory="true"  pattern="foo-\\w*-\\d*" dbKey="unique"/>
		<Attribute name="bytesValue" type="byte[]" dbTransient="true" />
		<Attribute name="bytesArrayValue" type="byte[]" list="linked"/>
		<Attribute name="msgValue" type="TestSubMessage"/>
		<Attribute name="msgArray" type="TestSubMessage" list="array"/>
		<Attribute name="timeString" type="String" mandatory="true"  pattern="\\d+-\\d+-\\d+ \\d+:\\d+:\\d+\\.\\d+"/>
	</Message>
</Messages>
</small>

**the generated POJO/JSON object based upon the definition above can be viewed here: [TestMessage.java](https://github.com/hoddmimes/json-transform/blob/master/samples/src/main/java/generated/TestMessage.java)**

### Message Extensions
The natural way of extending message is to subclass a generated POJO message object However there is also a _dirty way_ of adding implementation to a generated POJO message by adding the code 
directly in the XML definition file using the element tag  **&lt;code&gt; ... &lt;/code&gt;** e.g.

<pre>
&lt;code&gt;
public int incrementFoo( int pIncrementValue )
{ 
   this.mFoo += pIncrementValue;
   return this.mFoo; 
}
&lt;/code&gt;
</pre>



### Supported Attribute Types


 >- "boolean" mapped to JSON "Boolean"
 >- "byte" mapped to JSON "Integer"
 >- "char" mapped to JSON "Integer"
 >- "short" mapped to JSON "Integer"
 >- "int" mapped to JSON "Integer"
 >- "long" mapped to JSON "Integer"
 >- "double" mapped to JSON "Numeric"
 >- "String" mapped to JSON "String"
 >- "Map" mapped to JsonArray of Json objects ([{'name':value}...])
 >- "LocalDate" mapped to "String" ("yyyy-MM-dd")
 >- "LocalDateTime" mapped to String ("yyyy-MM-dd HH:mm:mm.SSS)
 >- "byte[]" mapped to JSON "String" (Base64 string)

### Supported Attribute Lists Types


>    < Attribute name="intArray" type="int" list="array"/>
</small>

 **_"list"_** may be of the type
 * "array" - translates to java _ArrayList_
 * "linked" - translates to java _LinkedList_
 * "stack" - translates to java _Stack_
 




 ### Suported JSON Schema Attributes
 - "mandatory" requires that the attribute is present / set
 - for strings, "maxLength", "minLength", "pattern"
 - for integer "min", "max", "multipleOf"




## Mongo Support
Optionally message objects can also be generated to support Mongo encode/decoding by implementing the interface
[MessageMongoInterface.java]([https://github.com/hoddmimes/json-transform/blob/master/sample/java/main/com/hoddmimes/json-transform/MessageMongoInterface.java](https://github.com/hoddmimes/json-transform/blob/master/sample/java/main/com/hoddmimes/jsontransform/MessageMongoInterface.java))

Three optional directives rules which and how message objects  implements Mongo support.
 * On a top level <Messages *mongoSupport="true"*> the attribute *mongoSupport* rules whatever the message definitions in the XML files
 should be subject for generate Mongo support or not.
 * On a individual message level <Message name="Froboz" rootMessage="true" *db="true" dbCollection="Test"*> the attribute *db* defines whatever the message is
 subject for implementing Mongo support or not. The attribute *dbCollection* is optional and is used to define an alternative name to be used for the Mongo collection into which the message
 is stored. If not present the collection name used will be equal with the message name.
 * On a message attribute level the xml attribute <Attribute name="intValue" type="int" mandatory="true" *dbKey="unique"*/> *dbKey* defines whatever the attribute
 should be a Mongo key in the document or not. If the *dbKey* value is *"unique"* the Mongo key would need to be unique. Furhermore an XML attribute could also be tagged with *dbTransient" <Attribute name="bytesValue" type="byte[]" *dbTransient="true"*/>.
 This will result in that the attribute will not be written/retrieved to/from Mongo.

In addition, a class MongoAux.java is generated. This class implements convince methods for creating a database, dropping a database and CRUD methods for handling the message POJO interaction with Mongo.
The files XML topfile, in the example  [TestMessageFileSet.xml]([https://github.com/hoddmimes/json-transform/blob/master/samples/xml/TestMessagesFileSet.xml](https://github.com/hoddmimes/json-transform/blob/master/sample/xml/TestMessagesFileSet.xml)) defines whatever the class MongoAux.java should be generated or not.


## Sample TestSchema.java
The file [TestSchema.java]([https://github.com/hoddmimes/json-transform/blob/master/samples/java/test/test/TestSchema.java](https://github.com/hoddmimes/json-transform/blob/master/sample/java/test/test/TestSchema.java)) exemplify
how to verify a Json message against one of the schemas generated.

## Sample TestEncodeDecode.java
the file [TestEncodeDecode.java]([https://github.com/hoddmimes/json-transform/blob/master/samples/java/test/test/TestEncodeDecode.java](https://github.com/hoddmimes/json-transform/blob/master/sample/java/test/test/TestEncodeDecode.java))
exemplfies how encode/encode a Java POJO class.

## Sample TestMongoSupport.java
the file [TestEncodeDecode.java]([https://github.com/hoddmimes/json-transform/blob/master/samples/java/test/test/TestMongoSupport.java](https://github.com/hoddmimes/json-transform/blob/master/sample/java/test/test/TestMongoSupport.java))
exemplfies how encode/encode a Java POJO class to Mongo DB documents. It also exemplifies how to use the generated Mongo auxiliary methods in the class [MongoAux]([[https://github.com/hoddmimes/json-transform/blob/master/sample/java/main/generated/MongoAux.java](https://github.com/hoddmimes/json-transform/blob/master/sample/java/main/generated/MongoAux.java)) to create,delete,find and update POJO objects in a Mongo DB.

The test assumes that there is a local Mongo DB instance without any authentication enabled.


## JAR Files Built
Three JAR files are built and placed on the project top level

- pojojson-generate-1.4.jar  this JAR contains everyhing needed for generating POJO/JSON objects from a XML definition, A generation of objects are typically done by invoking the following command "java -cp pojojson-generate-1.4.jar JsonTransform -xml *transformation-definions*.xml"
- pojojson-1.4.jar contains the support classes for encode/decode generated POJO/JSON objects. This JAR is need when there is a need for encode/decode generated objects.
- pojojson-tests.jar contains all classes for running the test on the generated TestMessage object in this project.