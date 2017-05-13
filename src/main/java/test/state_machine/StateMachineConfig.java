package test.state_machine;

import java.util.EnumSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import test.state_machine.order.OrderEvents;
import test.state_machine.order.OrderStates;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents>{
  
  private Logger logger = LogManager.getLogger(StateMachineConfig.class.toString());

  
//  @Override
//  public void configure(StateMachineConfigurationConfigurer<States, Events> config)
//          throws Exception {
//      config
//          .withConfiguration()
//              .autoStartup(true)
//              .listener(listener());
//  }

  @Override
  public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states)
          throws Exception {
      states
          .withStates()
              .initial(OrderStates.SI)
                  .states(EnumSet.allOf(OrderStates.class));
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions)
          throws Exception {
      transitions
          .withExternal()
              .source(OrderStates.SI).target(OrderStates.S1).event(OrderEvents.E1)
              .and()
          .withExternal()
              .source(OrderStates.S1).target(OrderStates.S2).event(OrderEvents.E2);
  }

//  @Bean
//  public StateMachineListener<States, Events> listener() {
//      return new StateMachineListenerAdapter<States, Events>() {
//          @Override
//          public void stateChanged(State<States, Events> from, State<States, Events> to) {
//            logger.info("State change to " + to.getId());
//          }
//          
//      };
//  }
  
}
