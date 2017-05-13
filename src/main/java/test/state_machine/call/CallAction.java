package test.state_machine.call;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import test.state_machine.serialize.InMemoryStateMachinePersist;


public class CallAction implements Action<CallStates,CallEvents>{

//  @Autowired
//  private InMemoryStateMachinePersist<CallStates, CallEvents> orderStateMachinePersister;

  @Override
  public void execute(StateContext<CallStates, CallEvents> context) {
    String transactionId = context.getExtendedState().get("transactionId",String.class);
//    StateMachineContext<CallStates,CallEvents> stateMachineContext = 
//        new DefaultStateMachineContext<CallStates, CallEvents>(CallStates.S1, CallEvents.E1, context.getMessageHeaders(), context.getExtendedState());
//    try {
//      orderStateMachinePersister.write(stateMachineContext, transactionId);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
    System.out.println("Order action - transaction id: " + transactionId);
  }
  
}

