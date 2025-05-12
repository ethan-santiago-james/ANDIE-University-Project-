# compile the Java files into bin directory
javac -cp src/main/java -d bin src/main/java/cosc202/andie/*.java
# copy icon into bin directory
cp src/main/resources/icon.png bin/
cp src/main/resources/*.properties bin/
# copy any properties files into bin directory
jar --create --file=ANDIE-test.jar --main-class=cosc202.andie.Andie -C bin .
