# C7.C7Paint
Project for course TDA367.


## How to run the application
There are two ways to run the project:

### Run jar file
Use maven to pacakge the project by running the command `mvn package` while in the root folder.
This will package a jar file which can be executed with the command `java -jar "target//C7Paint-1.0-SNAPSHOT.jar"`.

### Run class files in IDE 
Alternativly, the project could be run with its class files. This can be done with an IDE, such as Intellij.
Open the project pom.xml file with the IDE. Navigate to run configurations and then to VM options. Add this line to the VM options, but fill in the "{JAVAFX_HOME_PATH}" with your Javafx home path, `--module-path {JAVAFX_HOME_PATH}/lib --add-modules javafx.controls,javafx.fxml`.

## Authors
| **Username** | **IRL name** |
| ------------- | ------------- |
| Spektria | Hugo Ekstrand |
| Eliasers | Elias Ersson |
| funcorange | Love Gustafsson |
| isakswe | Isak Gustafsson |

**Note:** Love Gustafsson changed surname during the project to Love Svalby.
