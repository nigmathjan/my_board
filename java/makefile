all:

	rm -rf MyApp/WEB-INF/classes/*
	
	find . -iname *.java > src_files
	
	javac @src_files -d ./MyApp/WEB-INF/classes/ -classpath /usr/share/tomcat7/lib/servlet-api.jar:lib/mysql-connector-java-5.1.32-bin.jar:lib/commons-fileupload-1.3.1.jar:lib/commons-io-2.4.jar
