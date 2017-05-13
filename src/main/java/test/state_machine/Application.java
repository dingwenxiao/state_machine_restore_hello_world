package test.state_machine;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import test.state_machine.call.CallEvents;
import test.state_machine.call.CallStates;
import test.state_machine.order.OrderEvents;
import test.state_machine.order.OrderStateMachineBuilder;
import test.state_machine.order.OrderStates;
import test.state_machine.serialize.InMemoryStateMachinePersist;

@SpringBootApplication
public class Application implements CommandLineRunner {

  private StateMachinePersister<OrderStates, OrderEvents, String> orderStateMachinePersister = new DefaultStateMachinePersister<>(new InMemoryStateMachinePersist<OrderStates,OrderEvents>());
  
  //@Autowired
  //private InMemoryStateMachinePersist<CallStates,CallEvents> callStateMachinePersist;
  
  public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... arg0) throws Exception {
    StateMachine<OrderStates, OrderEvents> orderStateMachine = OrderStateMachineBuilder.buildMachine();
    String transactionId = UUID.randomUUID().toString();
    orderStateMachine.getExtendedState().getVariables().put("transactionId", transactionId);
    
    orderStateMachine.sendEvent(OrderEvents.E1);
    orderStateMachinePersister.persist(orderStateMachine, transactionId);
    System.out.println("persisting statemachine");
    
    
    StateMachine<OrderStates, OrderEvents> newOrderStateMachine = OrderStateMachineBuilder.buildMachine();
    newOrderStateMachine = orderStateMachinePersister.restore(newOrderStateMachine, transactionId);
    System.out.println("restored statemachine");
    String passedTransactionId = newOrderStateMachine.getExtendedState().get("transactionId", String.class);
    newOrderStateMachine.sendEvent(OrderEvents.E2);
    System.out.println("transaction id : " + passedTransactionId);
  }

}
