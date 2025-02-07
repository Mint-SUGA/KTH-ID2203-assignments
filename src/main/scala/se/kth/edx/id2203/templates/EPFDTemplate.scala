// ### Exercise 1: Build an Eventually Perfect Failure Detector
//
// An Eventually Perfect Failure Detector (EPFD), in Kompics terms, is a component that **provides** the following port _(already imported in the code)_.
//
//```scala
//  class EventuallyPerfectFailureDetector extends Port {
//    indication[Suspect];
//    indication[Restore];
//  }
//```
//
// Simply put, your component should indicate or ‘deliver’ to the application the following messages:
//
//```scala
//  case class Suspect(src: Address) extends KompicsEvent;
//  case class Restore(src: Address) extends KompicsEvent;
//```
//
// As you have already learnt from the course lectures, an EPFD, defined in a partially synchronous model, should satisfy the following properties:
//
//  1.  **Completeness**: Every process that crashes should be eventually suspected permanently by every correct process
//  2.  **Eventual Strong Accuracy**: No correct process should be eventually suspected by any other correct process
//
// To complete this assignment you will have to fill in the missing functionality denoted by the commented sections below and pass the property checking test at the end of this notebook.  
// The recommended algorithm to use in this assignment is _EPFD with Increasing Timeout and Sequence Numbers_, which you can find at the second page of this [document](https://courses.edx.org/asset-v1:KTHx+ID2203.1x+2016T3+type@asset+block@epfd.pdf) in the respective lecture.


// {"gradingToken":[45,45,45,45,45,66,69,71,73,78,32,80,71,80,32,77,69,83,83,65,71,69,45,45,45,45,45,10,86,101,114,115,105,111,110,58,32,66,67,80,71,32,118,49,46,53,53,10,10,104,81,73,77,65,57,79,116,77,118,84,69,98,74,49,102,65,81,47,47,82,67,116,98,111,50,67,56,109,100,113,57,78,120,99,107,90,54,104,114,83,71,79,51,82,51,82,67,110,107,115,86,87,119,102,97,110,118,80,100,89,79,53,81,10,43,90,110,102,113,104,121,67,47,85,74,89,52,107,111,52,119,47,51,49,108,83,55,117,113,111,115,57,89,77,47,104,70,102,81,90,52,87,122,49,97,113,76,114,75,78,104,88,101,108,119,101,110,80,100,120,83,53,114,89,70,65,117,119,10,55,80,57,55,103,66,89,80,68,103,120,76,122,110,66,74,65,120,66,83,110,86,117,122,110,112,68,114,100,110,70,83,55,54,80,68,72,104,68,102,87,70,76,106,65,105,115,56,110,71,57,88,84,65,71,117,73,54,102,103,79,76,47,107,10,122,97,104,72,100,86,77,108,52,69,86,98,47,102,119,98,117,52,67,97,56,113,84,53,101,79,65,87,89,107,65,75,82,97,118,69,118,67,67,72,77,73,100,82,51,112,50,66,106,105,115,74,111,66,119,97,113,85,121,51,117,99,57,55,10,104,56,65,49,74,76,80,97,111,104,116,72,50,54,78,109,48,114,108,56,90,85,97,56,107,70,69,106,65,70,115,74,113,111,109,110,99,79,78,97,89,115,97,111,48,52,115,113,118,76,89,109,103,87,114,84,84,88,78,82,48,55,82,55,10,100,86,67,50,102,88,53,76,119,76,71,107,114,70,109,87,99,47,79,78,106,76,77,120,115,90,119,49,108,65,97,71,113,112,104,77,70,107,107,73,57,50,89,82,52,115,68,84,116,54,89,99,103,50,73,47,65,105,53,86,43,54,102,103,10,119,90,52,77,71,86,49,76,81,106,51,122,51,71,69,115,66,57,43,83,65,115,110,71,48,78,108,66,99,100,99,77,74,56,90,87,54,105,81,102,85,84,86,72,77,57,75,110,113,111,55,66,87,47,85,112,89,82,100,106,57,101,118,109,10,122,109,87,72,84,54,113,47,57,77,71,66,47,89,113,99,111,54,110,110,76,78,50,67,57,118,56,99,74,112,101,103,72,117,65,67,51,74,71,89,71,88,81,106,73,102,51,115,114,69,56,113,113,110,77,87,68,97,80,107,102,66,111,56,10,48,50,73,74,87,99,87,76,120,57,118,70,111,107,83,103,107,71,66,55,119,101,85,66,75,72,86,119,77,54,103,78,88,86,117,43,55,112,112,111,111,118,110,97,103,71,84,48,110,74,99,114,51,48,43,114,57,114,74,56,76,80,79,53,10,112,65,78,114,43,106,78,49,74,105,75,121,84,113,109,87,77,75,110,108,84,70,72,99,47,71,71,77,101,72,117,109,49,76,73,80,51,51,75,101,113,97,113,83,54,57,79,83,47,80,88,90,76,53,87,111,76,115,67,106,121,89,120,76,10,120,80,83,121,86,43,57,49,114,43,51,66,77,101,50,81,119,78,122,112,53,87,83,98,114,118,49,83,114,118,67,82,108,48,78,82,79,49,68,115,77,84,107,84,55,87,83,56,88,68,102,80,76,75,98,109,82,81,102,68,56,99,55,83,10,119,67,81,66,106,53,88,68,105,57,85,89,82,68,54,120,69,106,88,113,78,74,69,109,51,85,101,119,70,111,43,77,56,73,75,106,113,115,69,102,55,65,115,97,120,49,97,49,52,104,56,67,101,114,53,79,80,90,78,112,117,99,83,97,10,101,51,121,86,80,85,82,86,56,82,65,90,118,50,89,108,117,121,56,108,120,78,66,90,119,55,74,82,113,86,103,51,66,51,70,105,66,114,116,68,71,74,85,88,50,66,107,84,77,115,52,122,48,74,98,99,65,77,55,52,107,48,118,54,10,106,66,51,115,67,102,65,90,88,72,117,72,110,83,53,119,73,74,43,90,109,121,80,75,102,105,73,82,109,83,77,69,117,75,104,101,77,85,71,122,97,77,47,74,80,112,88,49,65,53,67,88,72,114,75,85,57,47,90,119,84,56,98,110,10,55,110,50,88,84,84,105,84,101,103,108,117,98,53,105,114,53,43,97,82,88,54,120,115,73,104,69,122,103,102,57,47,49,111,54,88,49,100,54,97,47,78,107,114,55,115,113,122,77,110,97,76,86,87,56,49,49,79,47,78,55,90,103,50,10,67,49,52,89,65,97,69,85,54,112,111,65,75,48,49,57,54,89,53,52,122,56,80,79,74,43,47,114,117,74,43,82,51,119,97,73,98,121,68,49,57,47,50,87,73,82,51,52,84,107,73,61,10,61,87,51,99,43,10,45,45,45,45,45,69,78,68,32,80,71,80,32,77,69,83,83,65,71,69,45,45,45,45,45,10]}


package se.kth.edx.id2203.templates

import se.kth.edx.id2203.core.Ports._
import se.kth.edx.id2203.templates.EPFD._
import se.kth.edx.id2203.validation._
import se.sics.kompics.network._
import se.sics.kompics.sl.{ Init, _ }
import se.sics.kompics.timer.{ ScheduleTimeout, Timeout, Timer }
import se.sics.kompics.{ KompicsEvent, Start, ComponentDefinition => _, Port => _ }

import scala.collection.mutable._

//Define initialization event
object EPFD {

  //Declare custom message types related to internal component implementation
  case class CheckTimeout(timeout: ScheduleTimeout) extends Timeout(timeout);

  case class HeartbeatReply(seq: Int) extends KompicsEvent;
  case class HeartbeatRequest(seq: Int) extends KompicsEvent;
}

//Define EPFD Implementation
class EPFD(epfdInit: Init[EPFD]) extends ComponentDefinition {

  //EPFD subscriptions
  val timer = requires[Timer];
  val pLink = requires[PerfectLink];
  val epfd = provides[EventuallyPerfectFailureDetector];

  // EPDF component state and initialization
  val self = epfdInit match {
    case Init(s: Address) => s
  }
  val topology = cfg.getValue[List[Address]]("epfd.simulation.topology");
  val delta = cfg.getValue[Long]("epfd.simulation.delay");

  var period = cfg.getValue[Long]("epfd.simulation.delay");
  var alive = Set(cfg.getValue[List[Address]]("epfd.simulation.topology"): _*);
  var suspected = Set[Address]();
  var seqnum = 0;

  def startTimer(delay: Long): Unit = {
    val scheduledTimeout = new ScheduleTimeout(period);
    scheduledTimeout.setTimeoutEvent(CheckTimeout(scheduledTimeout));
    trigger(scheduledTimeout -> timer);
  }

  //EPFD event handlers
  ctrl uponEvent {
    case _: Start => {
      /* WRITE YOUR CODE HERE */
      startTimer(period)
    }
  }

  timer uponEvent {
    case CheckTimeout(_) => {
      if (!alive.intersect(suspected).isEmpty) {
        /* WRITE YOUR CODE HERE */
        period += delta
      }

      seqnum = seqnum + 1;

      for (p <- topology) {
        if (!alive.contains(p) && !suspected.contains(p)) {

         /* WRITE YOUR CODE HERE */
          suspected = suspected + p
          trigger(Suspect(p) -> epfd)

        } else if (alive.contains(p) && suspected.contains(p)) {
          suspected = suspected - p;
          trigger(Restore(p) -> epfd);
        }
        trigger(PL_Send(p, HeartbeatRequest(seqnum)) -> pLink);
      }

      alive = Set[Address]();
      startTimer(period);
    }
  }

  pLink uponEvent {
    case PL_Deliver(src, HeartbeatRequest(seq)) => {
         /* WRITE YOUR CODE HERE */
        trigger(PL_Send(src, HeartbeatReply(seq)) -> pLink)

    }
    case PL_Deliver(src, HeartbeatReply(seq)) => {
         /* WRITE YOUR CODE HERE */
        alive = alive + src
      
    }
  }
};

object EPFDTemplate extends App {
  checkEPFD[EPFD]();
}
