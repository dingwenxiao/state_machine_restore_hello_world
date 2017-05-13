package test.state_machine.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachineContextRepository;
import org.springframework.statemachine.kryo.MessageHeadersSerializer;
import org.springframework.statemachine.kryo.StateMachineContextSerializer;
import org.springframework.statemachine.kryo.UUIDSerializer;
import org.springframework.stereotype.Component;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

@Component
public class HazelcastStateMachineContextRepository<S, E>
    implements StateMachineContextRepository<S, E, StateMachineContext<S, E>> {

  HazelcastManagement hazelcastManagement = HazelcastManagement.INSTANCE;

  private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {

    @SuppressWarnings("rawtypes")
    @Override
    protected Kryo initialValue() {
      Kryo kryo = new Kryo();
      kryo.addDefaultSerializer(StateMachineContext.class, new StateMachineContextSerializer());
      kryo.addDefaultSerializer(MessageHeaders.class, new MessageHeadersSerializer());
      kryo.addDefaultSerializer(UUID.class, new UUIDSerializer());
      return kryo;
    }
  };

  public HazelcastStateMachineContextRepository() {
    
  }
  
  public HazelcastStateMachineContextRepository(String metaDataMapName) {
    hazelcastManagement.setMapName(metaDataMapName);;
  }


  private byte[] serialize(StateMachineContext<S, E> context) {
    Kryo kryo = kryoThreadLocal.get();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Output output = new Output(out);
    kryo.writeObject(output, context);
    output.close();
    return out.toByteArray();
  }

  @SuppressWarnings("unchecked")
  private StateMachineContext<S, E> deserialize(byte[] data) {
    if (data == null || data.length == 0) {
      return null;
    }

    Kryo kryo = kryoThreadLocal.get();
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    Input input = new Input(in);
    return kryo.readObject(input, StateMachineContext.class);
  }

  @Override
  public void save(StateMachineContext<S, E> context, String id) {
    hazelcastManagement.persist(id, serialize(context));
  }

  @Override
  public StateMachineContext<S, E> getContext(String id) {
    return deserialize(hazelcastManagement.getMetaData(id));
  }

}
