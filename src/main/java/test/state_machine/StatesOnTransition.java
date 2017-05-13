package test.state_machine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.statemachine.annotation.OnTransition;

import test.state_machine.order.OrderStates;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnTransition
public @interface StatesOnTransition {

    OrderStates[] source() default {};

    OrderStates[] target() default {};
}
