package test.state_machine.order;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import test.state_machine.serialize.InMemoryStateMachinePersist;

public class OrderAction implements Action<OrderStates, OrderEvents> {

  @Autowired
  private InMemoryStateMachinePersist<OrderStates, OrderEvents> orderStateMachinePersister;

  @Override
  public void execute(StateContext<OrderStates, OrderEvents> context) {
    String transactionId = context.getExtendedState().get("transactionId",String.class);
//    StateMachineContext<OrderStates,OrderEvents> stateMachineContext = 
//        new DefaultStateMachineContext<OrderStates, OrderEvents>(OrderStates.S1, OrderEvents.E1, context.getMessageHeaders(), context.getExtendedState());
//    try {
//      orderStateMachinePersister.write(stateMachineContext, transactionId);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
    System.out.println("Order action - transaction id: " + transactionId);
  }

}

