package com.henrytsyu.registermachines.runner;

import com.henrytsyu.registermachines.entities.RMDecrement;
import com.henrytsyu.registermachines.entities.RMHalt;
import com.henrytsyu.registermachines.entities.RMIncrement;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RMRunnerTest {
  private final RMRunner runnerEmpty = new RMRunner(Collections.emptyMap());
  private final RMRunner runnerHalt = new RMRunner(Map.ofEntries(Map.entry(0, new RMHalt())));
  private final RMRunner runnerIncrement = new RMRunner(
      Map.ofEntries(Map.entry(0, new RMIncrement(0, 1))));
  private final RMRunner runnerDecrementSuccess = new RMRunner(
      Map.ofEntries(Map.entry(0, new RMDecrement(0, 1, 2))),
      Map.ofEntries(Map.entry(0, 1)));
  private final RMRunner runnerDecrementFail = new RMRunner(
      Map.ofEntries(Map.entry(0, new RMDecrement(0, 1, 2))));

  @Test
  public void programCounterStartsAtZero() {
    assertEquals(0, runnerEmpty.getProgramCounter());
  }

  @Test
  public void erroneousHaltTerminates() {
    assertTrue(runnerEmpty.runStep());
  }

  @Test
  public void runningHaltTerminates() {
    assertTrue(runnerHalt.runStep());
  }

  @Test
  public void runningIncrementDoesNotTerminate() {
    assertFalse(runnerIncrement.runStep());
  }

  @Test
  public void runningDecrementDoesNotTerminate() {
    assertFalse(runnerDecrementSuccess.runStep());
  }

  @Test
  public void runningHaltDoesNotChangeProgramCounter() {
    int programCounterOld = runnerHalt.getProgramCounter();
    runnerHalt.runStep();
    assertEquals(programCounterOld, runnerHalt.getProgramCounter());
  }

  @Test
  public void runningIncrementChangesProgramCounter() {
    runnerIncrement.runStep();
    assertEquals(1, runnerIncrement.getProgramCounter());
  }

  @Test
  public void runningDecrementOnPositiveRegisterChangesProgramCounterToSuccess() {
    runnerDecrementSuccess.runStep();
    assertEquals(1, runnerDecrementSuccess.getProgramCounter());
  }

  @Test
  public void runningDecrementOnZeroRegisterChangesProgramCounterToFail() {
    runnerDecrementFail.runStep();
    assertEquals(2, runnerDecrementFail.getProgramCounter());
  }
}
