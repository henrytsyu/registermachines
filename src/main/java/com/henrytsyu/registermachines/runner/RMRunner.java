package com.henrytsyu.registermachines.runner;

import com.henrytsyu.registermachines.parser.RMDecrement;
import com.henrytsyu.registermachines.parser.RMIncrement;
import com.henrytsyu.registermachines.parser.RMInstruction;

import java.util.HashMap;
import java.util.Map;

public class RMRunner {
  private final Map<Integer, RMInstruction> instructionMap;
  private int programCounter;
  private final Map<Integer, Integer> state;

  public RMRunner(Map<Integer, RMInstruction> instructionMap) {
    this(instructionMap, new HashMap<>());
  }

  public RMRunner(Map<Integer, RMInstruction> instructionMap, Map<Integer, Integer> state) {
    this.instructionMap = instructionMap;
    this.state = state;
  }

  public int getProgramCounter() {
    return programCounter;
  }

  public Map<Integer, Integer> getState() {
    return state;
  }

  /**
   * @return whether the register machine terminates after this step
   */
  public boolean runStep() {
    RMInstruction instruction = instructionMap.get(programCounter);
    if (instruction instanceof RMIncrement increment) {
      programCounter = increment.nextLine();
      return false;
    }
    if (instruction instanceof RMDecrement decrement) {
      if (state.getOrDefault(decrement.registerNumber(), 0) == 0) {
        programCounter = decrement.nextLineFail();
      } else {
        programCounter = decrement.nextLineSuccess();
      }
      return false;
    }
    // instruction is RMHalt
    return true;
  }
}
