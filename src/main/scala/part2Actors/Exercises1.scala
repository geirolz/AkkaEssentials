package part2Actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * AkkaEssentials
  * Created by geirolad on 15/01/2019.
  *
  * @author geirolad
  */
object Exercises1 extends App{

  val system = ActorSystem("myactorsystem")

  class BankAccount() extends Actor{

    var amount = 0
    override def receive: Receive = {
      case op@Deposit(v, req) => amount += v; req ! Success(op)
      case op@Deposit(v, req) if v < 0 => req ! Failure(op)
      case op@Withdraw(v, req) if v < amount => req ! Success(op)
      case op@Withdraw(_, req) => req ! Failure(op)
      case Statement(req) => req ! s"[BankAccount] Amount: $amount"
    }
  }

  class User() extends Actor{
    override def receive: Receive = {
      case Success(op) => println(s"[User] Success ${op.toString}")
      case Failure(op) => println(s"[User] Failure ${op.toString}")
      case msg => println(msg)
    }
  }


  sealed trait Op
  case class Deposit(amount: Int, req: ActorRef) extends Op{
    override def toString: String = s"Deposit of $amount"
  }
  case class Withdraw(amount: Int, req: ActorRef) extends Op{
    override def toString: String = s"Withdraw of $amount"
  }
  case class Statement(req: ActorRef) extends Op

  sealed trait Result{val op: Op}
  case class Success(op: Op) extends Result
  case class Failure(op: Op) extends Result



  private val bank = system.actorOf(Props[BankAccount])
  private val user = system.actorOf(Props[User])



  bank ! Deposit(100, user)
  bank ! Statement(user)
}
