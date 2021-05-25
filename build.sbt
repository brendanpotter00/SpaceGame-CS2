name := "SpaceGame"
version := "1.0"
scalaVersion := "2.12.10"
run / fork := true
run / connectInput := true

libraryDependencies ++= Seq(
	"org.scalafx" %% "scalafx" % "8.0.144-R12",
	"com.novocode" % "junit-interface" % "0.11" % Test,
	"org.scalactic" %% "scalactic" % "3.0.5",
	"org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add JavaFX dependencies
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m=>
  "org.openjfx" % s"javafx-$m" % "11" classifier osName
)