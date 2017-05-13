package test.state_machine.serialize;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

public class InMemoryStateMachinePersist<S, T> implements StateMachinePersist<S, T, String> {

  //@Autowired
  private HazelcastStateMachineContextRepository<S, T> hazelcastStateMachineContextRepository = new HazelcastStateMachineContextRepository<>();

  private Map<String, byte[]> inMemoryHashMap = new HashMap<>();

  @Override
  public void write(StateMachineContext<S, T> context, String contextObj) throws Exception {
    byte[] contextMeta = hazelcastStateMachineContextRepository.serialize(context);
    inMemoryHashMap.put(contextObj, contextMeta);
  }

  @Override
  public StateMachineContext<S, T> read(String contextObj) throws Exception {
    byte[] contextMeta = inMemoryHashMap.get(contextObj);
    return hazelcastStateMachineContextRepository.deserialize(contextMeta);
  }

}
