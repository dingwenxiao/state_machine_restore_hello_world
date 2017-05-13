package test.state_machine.serialize;

import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.AbstractStateMachinePersister;

public class HazelcastStateMachineContextPersister<S,E> extends AbstractStateMachinePersister<S, E, String>{

  /**
   * Instantiates a new hazelcast state machine persister.
   *
   * @param stateMachinePersist the state machine persist
   */
  public HazelcastStateMachineContextPersister(StateMachinePersist<S, E, String> stateMachinePersist) {
      super(stateMachinePersist);
  }
}
