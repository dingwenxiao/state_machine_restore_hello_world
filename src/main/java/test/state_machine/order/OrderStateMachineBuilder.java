package test.state_machine.order;

import java.util.EnumSet;

import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;

public class OrderStateMachineBuilder {

  public static StateMachine<OrderStates, OrderEvents> buildMachine() throws Exception {
    Builder<OrderStates, OrderEvents> builder = StateMachineBuilder.builder();

    builder.configureConfiguration().withConfiguration().autoStartup(true)
        .beanFactory(new StaticListableBeanFactory()).taskExecutor(new SyncTaskExecutor())
        .taskScheduler(new ConcurrentTaskScheduler());

    builder.configureStates().withStates()
    .initial(OrderStates.SI)
    .end(OrderStates.S2)
        .states(EnumSet.allOf(OrderStates.class));;

    builder.configureTransitions().withExternal()
    .source(OrderStates.SI)
    .target(OrderStates.S1)
        .event(OrderEvents.E1).action(new OrderAction());

    return builder.build();
  }
}
