package playground

import akka.actor.ActorSystem

/**
  * AkkaEssentials
  * Created by geirolad on 15/01/2019.
  *
  * @author geirolad
  */
object playgroud extends App{

  val actorSystem = ActorSystem("HelloAkka")
  println(actorSystem.name)
}
