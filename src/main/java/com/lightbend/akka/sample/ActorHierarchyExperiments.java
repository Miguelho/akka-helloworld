package com.lightbend.akka.sample;

import java.io.IOException;

import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorHierarchyExperiments {
	public static void main(String[] args) throws IOException {
		ActorSystem system = ActorSystem.create("testSystem");

		ActorRef firstRef = system.actorOf(Props.create(PrintMyActorRefActor.class), "first-actor");

		System.out.println("First: " + firstRef);

		firstRef.tell("printit", ActorRef.noSender());

		System.out.println(">>> Press ENTER to exit <<<");
		try {
			System.in.read();
		} finally {
			system.terminate();
		}
	}

}

class PrintMyActorRefActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().matchEquals("printit", 
					p -> {
						ActorRef secondRef = getContext().actorOf(Props.empty(), "second-actor");
						System.out.println("Second: " + secondRef);
					}
				).build();
	}

}
