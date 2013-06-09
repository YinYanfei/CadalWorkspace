PACKAGE_PATH=cn/cadal/catalogsearch
REPOSITORY_PATH=/home/cadal/CodeRepository
CADAL_LIBS_PATH=${REPOSITORY_PATH}/cadal.libs
LUCENE_CORE_JAR=${CADAL_LIBS_PATH}/lucene-core-3.0.0.jar
ALL_JAVA_LIBS=/home/cadal/CodeRepository/cadal.libs/org.apache.commons.httpclient.jar:/home/cadal/CodeRepository/cadal.libs/com.noelios.restlet.jar:/home/cadal/CodeRepository/cadal.libs/lucene-analyzers-3.0.0.jar:/home/cadal/CodeRepository/cadal.libs/mysql-connector-java-5.1.6-bin.jar:/home/cadal/CodeRepository/cadal.libs/com.noelios.restlet.ext.simple_3.1.jar:/home/cadal/CodeRepository/cadal.libs/org.simpleframework.jar:/home/cadal/CodeRepository/cadal.libs/org.apache.commons.logging.jar:/home/cadal/CodeRepository/cadal.libs/lucene-core-3.0.0.jar:/home/cadal/CodeRepository/cadal.libs/lucene-highlighter-3.0.0.jar:/home/cadal/CodeRepository/cadal.libs/com.noelios.restlet.ext.httpclient_3.1.jar:/home/cadal/CodeRepository/cadal.libs/org.restlet.jar:/home/cadal/CodeRepository/cadal.libs/org.apache.commons.codec.jar:/home/cadal/CodeRepository/cadal.libs/org.json.jar:/home/cadal/CodeRepository/cadal.libs/org.restlet.ext.json_2.0.jar

.PHONY: all
all: RestletApplication RestletResource RelativeWords

.PHONY: SAXHandler
SAXHandler: src/${PACKAGE_PATH}/indexBuilder/IndexBuilder.java
	javac -d bin -cp .:${LUCENE_CORE_JAR} src/${PACKAGE_PATH}/indexBuilder/SAXHandler.java

.PHONY: RestletResource
RestletResource: src/${PACKAGE_PATH}/restlet/RestletResource.java
	javac -d bin -cp .:bin:${ALL_JAVA_LIBS} src/${PACKAGE_PATH}/restlet/RestletResource.java

.PHONY: RestletApplication
RestletApplication: src/${PACKAGE_PATH}/restlet/RestletApplication.java
	javac -d bin -cp .:bin:${ALL_JAVA_LIBS} src/${PACKAGE_PATH}/restlet/RestletApplication.java

.PHONY: RelativeWords
RelativeWords: src/${PACKAGE_PATH}/restlet/RelativeWords.java
	javac -d bin -cp .:bin:${ALL_JAVA_LIBS} src/${PACKAGE_PATH}/restlet/RelativeWords.java
