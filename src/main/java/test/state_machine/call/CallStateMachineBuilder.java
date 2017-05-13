package test.state_machine.call;

import java.util.EnumSet;

import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;

public class CallStateMachineBuilder {

  public static StateMachine<CallStates, CallEvents> buildMachine() throws Exception {
    Builder<CallStates, CallEvents> builder = StateMachineBuilder.builder();

    builder.configureConfiguration()
        .withConfiguration()
          .autoStartup(true)
            .beanFactory(new StaticListableBeanFactory())
            .taskExecutor(new SyncTaskExecutor())
            .taskScheduler(new ConcurrentTaskScheduler());

    builder.configureStates()
        .withStates()
            .initial(CallStates.SI)
            .end(CallStates.S2)
            .states(EnumSet.allOf(CallStates.class));;

    builder.configureTransitions()
        .withExternal()
            .source(CallStates.SI)
            .target(CallStates.S1)
            .event(CallEvents.E1)
            .action(new CallAction());

    return builder.build();
}
}
