package test.state_machine.order;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class OrderAction implements Action<OrderStates, OrderEvents> {

  @Override
  public void execute(StateContext<OrderStates, OrderEvents> context) {
    String transactionId = context.getExtendedState().get("transactionId",String.class);
    System.out.println("Order action - transaction id: " + transactionId);
  }

}

