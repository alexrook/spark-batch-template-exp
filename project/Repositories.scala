import sbt._

object Repositories {

  //Internal repository (proxy) example
  object Nexus {
    val Url  = "http://xxx:8081/repository/<name>/"
    val Name = "Nexus"
    val It   = Name at Url
  }
}
