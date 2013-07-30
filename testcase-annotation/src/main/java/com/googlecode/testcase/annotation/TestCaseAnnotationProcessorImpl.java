package com.googlecode.testcase.annotation;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

class TestCaseAnnotationProcessorImpl implements AnnotationProcessor {

	private static final String XLS_EXTENSION = ".xls";
	private final static Logger LOGGER=Logger.getLogger(TestCaseAnnotationProcessorImpl.class);
	private final AnnotationProcessorEnvironment annotationProcessorEnvironment;

	TestCaseAnnotationProcessorImpl(AnnotationProcessorEnvironment annotationProcessorEnvironment) {
		this.annotationProcessorEnvironment = annotationProcessorEnvironment;
	}

	private static String generateExcelPath(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String date= simpleDateFormat.format(new Date());

 		return "TestCaes_"+date+XLS_EXTENSION;
	}

	public void process() {

/*		{-Apath=c:/test=null,
				-nowarn=null,
				-d=C:\com.googlecode\target\generated-resources\apt,
				-s=C:\com.googlecode\target\generated-sources\apt,
				save-parameter-names=null, -AApath=c:/test=null,
				-classpath=C:\Users\jiafu\.m2\repository\org\codehaus\plexus\plexus-utils\1.4.6\plexus-utils-1.4.6.jar;C:\Users\jiafu\.m2\repository\org\codehaus\plexus\plexus-compiler-api\1.5.2\plexus-compiler-api-1.5.2.jar;C:\Users\jiafu\.m2\repository\asm\asm\3.2\asm-3.2.jar;C:\Users\jiafu\.m2\repository\asm\asm-commons\3.2\asm-commons-3.2.jar;C:\Users\jiafu\.m2\repository\asm\asm-tree\3.2\asm-tree-3.2.jar;C:\Users\jiafu\.m2\repository\org\apache\xbean\xbean-reflect\3.4\xbean-reflect-3.4.jar;C:\Users\jiafu\.m2\repository\log4j\log4j\1.2.12\log4j-1.2.12.jar;C:\Users\jiafu\.m2\repository\commons-logging\commons-logging-api\1.1\commons-logging-api-1.1.jar;C:\Users\jiafu\.m2\repository\com\google\collections\google-collections\1.0\google-collections-1.0.jar;C:\com.googlecode\target\classes;C:\Users\jiafu\.m2\repository\com\googlecode\testcase-annotation\testcase-annotation\1.0.2\testcase-annotation-1.0.2.jar;C:\Users\jiafu\.m2\repository\org\apache\poi\poi-excelant\3.10-beta1\poi-excelant-3.10-beta1.jar;C:\Users\jiafu\.m2\repository\org\apache\poi\poi\3.10-beta1\poi-3.10-beta1.jar;C:\Users\jiafu\.m2\repository\commons-codec\commons-codec\1.5\commons-codec-1.5.jar;C:\Users\jiafu\.m2\repository\org\apache\poi\poi-ooxml\3.10-beta1\poi-ooxml-3.10-beta1.jar;C:\Users\jiafu\.m2\repository\org\apache\poi\poi-ooxml-schemas\3.10-beta1\poi-ooxml-schemas-3.10-beta1.jar;C:\Users\jiafu\.m2\repository\org\apache\xmlbeans\xmlbeans\2.3.0\xmlbeans-2.3.0.jar;C:\Users\jiafu\.m2\repository\stax\stax-api\1.0.1\stax-api-1.0.1.jar;C:\Users\jiafu\.m2\repository\dom4j\dom4j\1.6.1\dom4j-1.6.1.jar;C:\Users\jiafu\.m2\repository\xml-apis\xml-apis\1.0.b2\xml-apis-1.0.b2.jar;C:\Users\jiafu\.m2\repository\org\apache\ant\ant\1.8.2\ant-1.8.2.jar;C:\Users\jiafu\.m2\repository\org\apache\ant\ant-launcher\1.8.2\ant-launcher-1.8.2.jar;C:\Users\jiafu\.m2\repository\log4j\log4j\1.2.14\log4j-1.2.14.jar,
				-nocompile=null,
				-encoding=ISO-8859-1,
				-factory=com.googlecode.testcase.annotation.AnnotationProcessFactoryImpl,
				-sourcepath=C:\com.googlecode\src\main\java}*/

		String fileName=generateExcelPath();
		Map<String, String> options = this.annotationProcessorEnvironment.getOptions();
		Set<String> keySet = options.keySet();
		for(String key:keySet){
			if(key.startsWith("-AfileName="))
				fileName=key.substring("-AfileName=".length());
 		};

 		String folderPath = options.get("-s");
		ToExcelHandle toExcelHandle = new ToExcelHandle(folderPath,fileName);

		for (TypeDeclaration typeDeclaration : annotationProcessorEnvironment.getSpecifiedTypeDeclarations()) {
			System.out.println("find:" + typeDeclaration);

			Collection<? extends MethodDeclaration> methods = typeDeclaration.getMethods();
			for (MethodDeclaration methodDeclaration : methods) {

				System.out.println("___" + methodDeclaration);

				TestCase testCase = methodDeclaration.getAnnotation(TestCase.class);
				if (testCase == null)
					continue;

				TestCaseWrapper testCaseWrapper = new TestCaseWrapper(testCase, methodDeclaration.getSimpleName());
				LOGGER.info(testCaseWrapper);
				toExcelHandle.add(testCaseWrapper);
			}
		}

		toExcelHandle.generate();
	}

}