java-algorithms
===============

A set of interesting problems in algorithms implemented in Java

How to clone:
-------------
a) Say you have a eclipse workspace directory (in windows) at c:\vmurthy\workspaces
b) Just esnure you have git client properly installed and is available in cygwin prompt(use cygwin's git from its setup)
c) Open cygwin64 and then reach upto by cd /cygdrive/c/vmurthy/workspaces
d) git clone git@github.com:venkateshamurthy/java-algorithms.git java-algorithms 
e) This creates a folder java-algorithms in the main folder /cygdrive/c/vmurthy/workspaces
f) Next do a cd java-algorithms
g) Assuming maven is available in the path: just type "mvn clean verify" and it should build and run tests

How to create eclipse project:
------------------------------
a) Please ensure that lombok.jar (from http://projectlombok.org ) is downloaded 
b) next, reach to your cygwin prompt and reach upto the folder where lombok.jar is downloaded(as of date the version is 1.14.4)
c) next, type in cygwin: java -jar lombok.jar
d) next, this opens a window where eclipse installed in the host is listed(or if not please select the folder where your eclipse is installed)
e) once you click ok; the eclipse installation is lombok enabled.

f) Next, create a simple java project and point it to /cygdrive/c/vmurthy/workspaces/java-algorithms
g) Once the project opens; configure it to a maven project (File->Configure->Convert to Maven..)
h) Next, just do File->Maven->Update Project to update maven deps

Caveats/issues as of 5th july 2014
----------------------------------
a) The Lombok annotations @Data @Builder have not been successful and hence i have dropped them. (Basically mvn clean compile is failing; though eclipse doesnt have a problem) 
