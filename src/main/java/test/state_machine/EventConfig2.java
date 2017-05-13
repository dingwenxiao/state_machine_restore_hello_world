package test.state_machine;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.WithStateMachine;

import test.state_machine.order.OrderStates;

@WithStateMachine
public class EventConfig2 {
  private Logger logger = LogManager.getLogger(EventConfig2.class.toString());

  @StatesOnTransition(source = OrderStates.S1, target = OrderStates.S2)
  public void fromS1ToS2(@EventHeaders Map<String, Object> headers, ExtendedState extendedState) {
    logger.info("test");
    System.out.println("asdfs");
  }
}
