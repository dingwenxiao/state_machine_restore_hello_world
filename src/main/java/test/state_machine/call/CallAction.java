package test.state_machine.call;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;


public class CallAction implements Action<CallStates,CallEvents>{

  @Override
  public void execute(StateContext<CallStates, CallEvents> context) {
    String transactionId = context.getExtendedState().get("transactionId",String.class);
    System.out.println("Order action - transaction id: " + transactionId);
  }
  
}

