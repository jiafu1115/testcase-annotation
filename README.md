We can use @testcase to write test case for java methods and generate them to one excel and html.

**[#Why\_write\_this\_tool](#Why_write_this_tool.md)**

**[#Requirements](#Requirements.md)**

**[#How\_to\_use\_it](#How_to_use_it.md)**

**[#More\_use\_examples](#More_use_examples.md)**

**[#FAQ](#FAQ.md)**

# Why write this tool #

In traditional test process, we are familiar with writing test cases in excel at first and then to implement the test cases' automation one by one. At last,we add the java doc to every test method.

The problem is that when we change the test cases in excel we should found where is the related test scripts and located the code and change the test case java doc description to keep same.

On the other hand, for coder, they may like to write the test code as first and write test case in excel at later time.Maybe somebody doesn't like any doc. So the tool can help them to generate the test cases in excel by automation.

Also the tool can provide the html style test case so that it can be integrated with Jenkins. Having it, you can check the daily run test cases in Jenkins and from the cases description you also can locate the position easier.

# Requirements #

1.maven project

# How to use it #

## step 1: configure pom.xml ##

add followed two configure parts:dependency and plugin into pom.xml:
note: the dependency's last version can check the maven central:
http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.googlecode.testcase-annotation%22

**ALERT** pls use the latest version.

```
<dependency>
    <groupid>com.googlecode.testcase-annotation</groupid>
    <artifactid>testcase-annotation</artifactid>
    <version>1.2.0</version>
</dependency>
```

**For TestNG**


```
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<version>2.15</version>
				<configuration>

					<systemPropertyVariables>

						<TestCaseFile>TestCase.xls</TestCaseFile>
						<!-- optional,default is TestCase_{Date}.xlsx,suggest to config it 	to TestCase.xls -->

						<!-- <TestCaseFolder>C:/testcase</TestCaseFolder> -->
						<!-- optional,default is target/classes, -->

						<!-- <forceCreateFile>false</forceCreateFile> -->
						<!-- optional,default:false,if ture, will create testcase file even no any cases found. -->
 
					</systemPropertyVariables>

					<properties>
						<property>
							<name>usedefaultlisteners</name>
							<value>true</value>
							disabling default listeners is optional
						</property>
						<property>
							<name>listener</name>
							<value>com.googlecode.testcase.annotation.listeners.TestNGTestCaseListener</value>
						</property>
					</properties>

				</configuration>

```


**For Junit**

config as aboved with followed listener:
***com.googlecode.testcase.annotation.listeners.JunitTestCaseListener***

## step 2: use `@TestCase` in your code ##

```
public class NewTest {


	@TestCase(
			module="module one",  //optional
			id = "Case1001", //optional
		 	title = "case title 1",
		 	preConditions={"condition"}, //optional
			steps = {
				"case step one",
				"case step two"
				},
			results = {
				"case result one",
				"case result two"
				  }
 		 )
	@Test
	public void YourTestMethod() {

 	}
	
}
```

## step 3: generate excel and html(with excel content) ##

execute: **mvn test**  

 
output excel's dir: it is decided to outputDirectory configure by default: 

  *  the output dir is _target\classes

  * 

![http://ww2.sinaimg.cn/mw690/7d9d794bjw1e7bt966isnj20xs05dq4e.jpg](http://ww2.sinaimg.cn/mw690/7d9d794bjw1e7bt966isnj20xs05dq4e.jpg)

![http://ww2.sinaimg.cn/mw690/7d9d794bjw1e7bt4zp685j20q006dgns.jpg](http://ww2.sinaimg.cn/mw690/7d9d794bjw1e7bt4zp685j20q006dgns.jpg)

# More use examples #

## Use 1: How to get all test cases with run any test?

add configure for **maven-compiler-plugin**

            <plugin>
                           <groupId>org.apache.maven.plugins</groupId>
                           <artifactId>maven-compiler-plugin</artifactId>
                           <version>3.1</version>
                         <configuration>
                           <compilerArgs>
                           	<!--   <arg>-ATestCaseFolder=${project.build.directory}</arg>          -->
                                <!--   <arg>-AforceCreateFile=true</arg>          -->                     		
                          	<arg>-ATestCaseFile=TestCase.xls</arg>   
                           </compilerArgs>
                                  <annotationProcessors>
                                <annotationProcessor>com.googlecode.testcase.annotation.handle.toexcel.TestCaseAnnotationProcessorImpl</annotationProcessor>
                                 </annotationProcessors>
                                 <source>1.6</source>
                                 <target>1.6</target>
                                 <encoding>UTF-8</encoding>
                           </configuration>  
                    </plugin>

then run  **mvn compile**

## Use 2: How to generate office 03xls excel file instead of 07xlsx's? ##

add property: **TestCaseExcelType**
```
<configuration>
		<systemPropertyVariables>
				<TestCaseExcelType>xls</TestCaseExcelType>
		</systemPropertyVariables>
</configuration>
```

## Use 3: How to change the excel's name instead of `TestCase_Date.xlsx?` ##

the excel file's name is `TestCase_Date.xlsx` format. if you want to change it, you can:

add file name with xls extention : **TestCaseFile**
```

<configuration>
		<systemPropertyVariables>
				<TestCaseFile>customized.xls</TestCaseFile>
		</systemPropertyVariables>
</configuration>

```

## Use 4: How to change the excel's output dir instead of using outputdirectory ? ##

add configure: **TestCaseFolder**:
```
<configuration>
		<systemPropertyVariables>
				<TestCaseFolder>C:\aptDir</TestCaseFolder>
		</systemPropertyVariables>
</configuration>
```


## Use 4: How to integrated with Jenkins? ##

1. set the output file to fixed name: can refer to <use 2> add set the option forceCreateFile to create file no matter is there any cases created so that the html publish plugin not to report build fail when no cases existed.
 

whole configure:
```
<configuration>
		<systemPropertyVariables>
				<TestCaseFile>TestCase.xls</TestCaseFile>
				<forceCreateFile>true</forceCreateFile>
		</systemPropertyVariables>
</configuration>
```

2. configure the jenkins' job with **html publish plugin**

![http://ww2.sinaimg.cn/mw690/7d9d794bjw1e7buynmuzzj20qd05v0t8.jpg](http://ww2.sinaimg.cn/mw690/7d9d794bjw1e7buynmuzzj20qd05v0t8.jpg)

the result is similar with:

![http://ww2.sinaimg.cn/mw690/7d9d794bjw1e7cmlehdtxj20sb0c6n0h.jpg](http://ww2.sinaimg.cn/mw690/7d9d794bjw1e7cmlehdtxj20sb0c6n0h.jpg)

# FAQ #

## Problem 1: `java.lang.NoClassDefFoundError: org/apache/poi/ss/formula/udf/IndexedUDFFinder` ##

the root cause is that:

the tool use 3.10's poi dependency version, but if your pom.xml had existed lower verison for it which not supported IndexedUDFFinder, it will popup the error.

the solution is to remove the dependency for the lower's version's. for jar dependency can use exclude configure such as followed configure:
```
<dependency>
  <groupId>com.googlecode.server-test-toolkit</groupId>
  <artifactId>server-test-toolkit</artifactId>
  <version>1.0</version>
  <type>jar</type>
  <scope>compile</scope>
  <exclusions>
    <exclusion>
        <artifactId>poi</artifactId>
        <groupId>org.apache.poi</groupId>
    </exclusion>
  </exclusions>
</dependency>
```


## Problem 2: how to format the annotation to good style ##

change the eclipse's formmater:

->windows->preferences->java->code style->formmater
->Line Wrapping

![http://ww2.sinaimg.cn/mw690/7d9d794bjw1e7bjiwshgnj20np0gu0vr.jpg](http://ww2.sinaimg.cn/mw690/7d9d794bjw1e7bjiwshgnj20np0gu0vr.jpg)

## Problem 3: jenkins: html publish plugin report build fail when no any cases existed ##

set the option to force creating file so that it can avoid copy fail.

```
<configuration>
		<systemPropertyVariables>
				<forceCreateFile>true</forceCreateFile>
		</systemPropertyVariables>
</configuration>
```

## Problem 4: error: annotations are not supported in -source 1.3 ##

the jdk1.3 not support annotations. so you can change maven compile plugin's compile level to 1.6.
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>2.0.2</version>
    <configuration>
	<source>1.6</source>
	<target>1.6</target>
	<encoding>UTF-8</encoding>
    </configuration>
</plugin>
```

## Problem 5: can't show Chinese rightly in excel ##

should config encoding method.

```

<configuration>
<encoding>UTF-8</encoding>
</configuration>

```
