### Step 1

wsimport -keep -p GROUP_ID -clientjar JAR_NAME.jar WSDL_NAME.wsdl

Example: wsimport -keep -p com.kadioglumf.tutorials -clientjar wsdlclient.jar a.wsdl

### Step 2

mvn install:install-file –Dfile=JAR_FILE_PATH -DgroupId=GROUP_ID -DartifactId=ARTIFACT_ID -Dversion=JAR_VERSION

Example: mvn install:install-file –Dfile=C:\dev\app.jar -DgroupId=com.kadioglumf.tutorials -DartifactId=example-app -Dversion=1.0


### Step 3

- Add jar file to your dependency

``` xml 
<dependency>
    <groupId>com.kadioglumf.tutorials</groupId>
    <artifactId>example-app</artifactId>
    <version>1.0</version>
</dependency> 
```