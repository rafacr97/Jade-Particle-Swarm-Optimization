/**
 * 
 *  @author Rafael Camacho Roldán <za18012201@zapopan.tecmm.edu.mx>
 */
import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import jade.domain.FIPANames;

import java.util.Date;
import java.util.Vector;
import java.util.Enumeration;

@SuppressWarnings("unchecked")

public class Particle_Agent extends Agent {
		private int nResponders;
	
	protected void setup() { 
  	// Read names of responders as arguments
  	Object[] args = getArguments();
  	if (args != null && args.length > 0) {
  		nResponders = args.length;
  		System.out.println("PARTICLE: Trying to delegate PSO to one out of "+nResponders+" responders.");
  		
  		// Fill the CFP message
  		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
  		for (int i = 0; i < args.length; ++i) {
  			msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
  		}
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			// We want to receive a reply in 10 secs
			//msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			msg.setContent("Perform: Ant colony optimization method");
			
			addBehaviour(new ContractNetInitiator(this, msg) {
				
				protected void handlePropose(ACLMessage propose, Vector v) {
					System.out.println("PARTICLE: Agent "+propose.getSender().getName()+" proposed "+propose.getContent());
				}
				
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("PARTICLE: Agent "+refuse.getSender().getName()+" refused");
				}
				
				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						// FAILURE notification from the JADE runtime: the receiver
						// does not exist
						System.out.println("PARTICLE: Responder does not exist");
					}
					else {
						System.out.println("PARTICLE: Agent "+failure.getSender().getName()+" failed");
					}
					// Immediate failure --> we will not receive a response from this agent
					nResponders--;
				}
				
				protected void handleAllResponses(Vector responses, Vector acceptances) {
					if (responses.size() < nResponders) {
						// Some responder didn't reply within the specified timeout
						System.out.println("PARTICLE: Timeout expired: missing "+(nResponders - responses.size())+" responses");
					}
					// Evaluate proposals.
					String bestProposal = "";
					AID bestProposer = null;
					ACLMessage accept = null;

					Enumeration e = responses.elements();
					while (e.hasMoreElements()) {
						ACLMessage msg = (ACLMessage) e.nextElement();
						if (msg.getPerformative() == ACLMessage.PROPOSE) {
							ACLMessage reply = msg.createReply();
							reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
							acceptances.addElement(reply);

								String proposal = String.valueOf(msg.getContent());
								System.out.println("The proposal is: "+proposal);
							if (proposal !="PSO METHOD") {
								bestProposal = proposal;
								bestProposer = msg.getSender();
								accept = reply;
							}
						}
					}
					// Accept the proposal of the best proposer
					if (accept != null) {
						System.out.println("ANT: Accepting proposal "+bestProposal+" from responder "+bestProposer.getName());
						accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					}						
				}
				
				protected void handleInform(ACLMessage inform) {
					
					System.out.println("PSO: Agent "+inform.getSender().getName()+" successfully performed the requested action");

				}
			} );
  	}
  	else {
  		System.out.println("No responder specified.");
  	}
  } 
}