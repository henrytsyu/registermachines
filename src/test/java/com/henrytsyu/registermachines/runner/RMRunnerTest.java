package com.henrytsyu.registermachines.runner;

import com.henrytsyu.registermachines.parser.RMDecrement;
import com.henrytsyu.registermachines.parser.RMHalt;
import com.henrytsyu.registermachines.parser.RMIncrement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RMRunnerTest {
  private final RMRunner runner = new RMRunner();

  @Test
  public void runningHaltTerminates() {
    boolean halted = runner.runInstruction(new RMHalt());
    assertTrue(halted);
  }

  @Test
  public void runningIncrementOrDecrementDoesNotTerminate() {
    boolean incrementHalted = runner.runInstruction(new RMIncrement(0, 0));
    assertFalse(incrementHalted);
    boolean decrementHalted = runner.runInstruction(new RMDecrement(0, 0, 0));
    assertFalse(decrementHalted);
  }
}
