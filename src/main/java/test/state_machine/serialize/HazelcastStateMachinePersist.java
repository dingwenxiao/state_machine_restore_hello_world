package test.state_machine.serialize;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachineContextRepository;
import org.springframework.statemachine.StateMachinePersist;

public class HazelcastStateMachinePersist<S, T> implements StateMachinePersist<S, T, String> {

  
  private static final String PERSIST_MAP_NAME = "PERSIST_MAP_NAME";
  
  /**
   * TODO(Dingwen): Select a proper map to store a type of statemachine according to S and T.
   */
  private StateMachineContextRepository<S, T, StateMachineContext<S, T>> hazelcastStateMachineContextRepository =
      new HazelcastStateMachineContextRepository<>(PERSIST_MAP_NAME);

  @Override
  public void write(StateMachineContext<S, T> context, String contextObj) throws Exception {
    hazelcastStateMachineContextRepository.save(context, contextObj);
  }

  @Override
  public StateMachineContext<S, T> read(String contextObj) throws Exception {
    return hazelcastStateMachineContextRepository.getContext(contextObj);
  }

}
