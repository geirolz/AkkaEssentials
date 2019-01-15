package part2Actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * AkkaEssentials
  * Created by geirolad on 15/01/2019.
  *
  * @author geirolad
  */
object ActorsIntro extends App{

  val actorSystem = ActorSystem("firstActorSystem")
  private val david: ActorRef = actorSystem.actorOf(Person.props("David"))
  david ! "hi"
}

class Person(name: String) extends Actor{
  override def receive: Receive = {
    case "hi" => println(s"hi, i'm $name")
    case _ =>
  }
}

object Person {
  def props(name: String): Props = Props(new Person(name))
}


