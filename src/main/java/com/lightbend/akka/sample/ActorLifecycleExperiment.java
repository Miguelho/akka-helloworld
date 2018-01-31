package com.lightbend.akka.sample;


import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.ActorRef;

import java.io.IOException;

import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;

public class ActorLifecycleExperiment {
	public static void main(String[] args) throws IOException{
		ActorSystem system = ActorSystem.create("testSystem");
		
		ActorRef firstRef = system.actorOf(Props.create(StartStopActor1.class), "firstActor");
		
		System.out.println("First: " + firstRef);
		
		firstRef.tell("stop", ActorRef.noSender());
		
		System.out.println(">>> Press ENTER to exit <<<");
		try {
			System.in.read();
		} finally {
			system.terminate();
		}
	}
}

class StartStopActor1 extends AbstractActor {
	
	
	
	@Override
	public void preStart() throws Exception {
		System.out.println("first started");
		
		getContext().actorOf(Props.create(StartStopActor2.class), "second");
	}
	
	@Override
	public void postStop() throws Exception {
		System.out.println("first stopped");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.matchEquals("stop", p -> {
					getContext().stop(getSelf());
				}).build();
		
	}
}

class StartStopActor2 extends AbstractActor {

	@Override
	  public void preStart() {
	    System.out.println("second started");
	  }

	 @Override
	  public void postStop() {
	    System.out.println("second stopped");
	  }
	
	// Actor.emptyBehavior is a useful placeholder when we don't
	// want to handle any messages in the actor.
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder()
				.build();
	}
	
}
