package test.state_machine;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine(id = "myMachineId")
public class EventConfig {
  private Logger logger = LogManager.getLogger(EventConfig.class.toString());

  @OnTransition(source = "S1", target = "S2")
  public void fromS1ToS2(@EventHeaders Map<String, Object> headers, ExtendedState extendedState) {
    logger.info("test");
    System.out.println("test");
  }
}
