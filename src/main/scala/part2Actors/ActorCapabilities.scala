package part2Actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * AkkaEssentials
  * Created by geirolad on 15/01/2019.
  *
  * @author geirolad
  */
object ActorCapabilities extends App{

  class SimpleActor extends Actor{
    override def receive: Receive = {
      case "Hi" => log(s"I have received Hi from ${sender().path.name}")
      case msg: String => log(s"I have received $msg")
      case number: Int => log(s"I have received a NUMBER: $number")
      case SpecialMsg(content) => log(s"I have received a SPECIAL MSG: $content ")
      case SayHiTo(actorRef) => actorRef ! "Hi"
      case ForwardTo(msg, actorRef) => actorRef forward msg
    }

    def log(msg: String) = println(s"[${self.path.name}] $msg")
  }
  case class SpecialMsg(content: String)
  case class SayHiTo(target: ActorRef)
  case class ForwardTo(msg: String, target: ActorRef)

  val system = ActorSystem("ActorSystemDemo")
  val bob = system.actorOf(Props[SimpleActor], "bob")
  val alice = system.actorOf(Props[SimpleActor], "alice")
  val david = system.actorOf(Props[SimpleActor], "david")

  alice ! "hello, actor"
  alice ! 115
  alice ! SpecialMsg("Special msg.")
  bob ! SayHiTo(alice)

  //sender = deadletters
  alice ! ForwardTo("Hi", bob)

}
