name := "Lab1"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "1.9.1" % "test" withSources() withJavadoc()
)

parallelExecution in Test := false
