package test.state_machine.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.kryo.MessageHeadersSerializer;
import org.springframework.statemachine.kryo.StateMachineContextSerializer;
import org.springframework.statemachine.kryo.UUIDSerializer;
import org.springframework.stereotype.Component;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

@Component
public class HazelcastStateMachineContextRepository<S, E> {

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


  public byte[] serialize(StateMachineContext<S, E> context) {
    Kryo kryo = kryoThreadLocal.get();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Output output = new Output(out);
    kryo.writeObject(output, context);
    output.close();
    return out.toByteArray();
  }

  @SuppressWarnings("unchecked")
  public StateMachineContext<S, E> deserialize(byte[] data) {
    if (data == null || data.length == 0) {
      return null;
    }
    
    Kryo kryo = kryoThreadLocal.get();
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    Input input = new Input(in);
    return kryo.readObject(input, StateMachineContext.class);
  }

}
