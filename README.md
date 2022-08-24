$ mvn clean install
[INFO] Scanning for projects...
[INFO]
[INFO] ---------------< uk.gov.dwp.uc.pairtest:cinema-tickets >----------------
[INFO] Building cinema-tickets 1.0.0
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ cinema-tickets ---
[INFO] Deleting C:\Code\Dwp\TESTING\cinema-tickets\target
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ cinema-tickets ---
[WARNING] Using platform encoding (Cp1252 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory C:\Code\Dwp\TESTING\cinema-tickets\src\main\resources
[INFO]
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ cinema-tickets ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding Cp1252, i.e. build is platform dependent!
[INFO] Compiling 9 source files to C:\Code\Dwp\TESTING\cinema-tickets\target\classes
[INFO]
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ cinema-tickets ---
[WARNING] Using platform encoding (Cp1252 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 1 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ cinema-tickets ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding Cp1252, i.e. build is platform dependent!
[INFO] Compiling 1 source file to C:\Code\Dwp\TESTING\cinema-tickets\target\test-classes
[WARNING] /C:/Code/Dwp/TESTING/cinema-tickets/src/test/java/uk/gov/dwp/uc/pairtest/TicketServiceImplTest.java: C:\Code\Dwp\TESTING\cinema-tickets\src\test\java\uk\gov\dwp\uc\pairtest\TicketServiceImplTest.java uses or overrides a deprecated API.
[WARNING] /C:/Code/Dwp/TESTING/cinema-tickets/src/test/java/uk/gov/dwp/uc/pairtest/TicketServiceImplTest.java: Recompile with -Xlint:deprecation for details.
[INFO]
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ cinema-tickets ---
[INFO] Surefire report directory: C:\Code\Dwp\TESTING\cinema-tickets\target\surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running uk.gov.dwp.uc.pairtest.TicketServiceImplTest
Tests run: 15, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.55 sec

Results :

Tests run: 15, Failures: 0, Errors: 0, Skipped: 0

[INFO]
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ cinema-tickets ---
[INFO] Building jar: C:\Code\Dwp\TESTING\cinema-tickets\target\cinema-tickets-1.0.0.jar
[INFO]
[INFO] --- maven-install-plugin:2.4:install (default-install) @ cinema-tickets ---
[INFO] Installing C:\Code\Dwp\TESTING\cinema-tickets\target\cinema-tickets-1.0.0.jar to D:\Softwares\repository\uk\gov\dwp\uc\pairtest\cinema-tickets\1.0.0\cinema-tickets-1.0.0.jar
[INFO] Installing C:\Code\Dwp\TESTING\cinema-tickets\pom.xml to D:\Softwares\repository\uk\gov\dwp\uc\pairtest\cinema-tickets\1.0.0\cinema-tickets-1.0.0.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.624 s
[INFO] Finished at: 2022-08-24T21:21:30+01:00
[INFO] ------------------------------------------------------------------------

