package com.henrytsyu.registermachines.runner;

import com.henrytsyu.registermachines.entities.RMDecrement;
import com.henrytsyu.registermachines.entities.RMIncrement;
import com.henrytsyu.registermachines.entities.RMInstruction;

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

  public int getRegisterValue(int registerNumber) {
    return state.getOrDefault(registerNumber, 0);
  }

  /**
   * @return whether the register machine terminates after this step
   */
  public boolean runStep() {
    // Erroneous halt
    if (!instructionMap.containsKey(programCounter)) return true;
    RMInstruction instruction = instructionMap.get(programCounter);
    if (instruction instanceof RMIncrement increment) {
      int reg = increment.registerNumber();
      state.put(reg, state.getOrDefault(reg, 0) + 1);
      programCounter = increment.nextLine();
      return false;
    }
    if (instruction instanceof RMDecrement decrement) {
      if (state.getOrDefault(decrement.registerNumber(), 0) == 0) {
        programCounter = decrement.nextLineFail();
      } else {
        int reg = decrement.registerNumber();
        // Register value is positive
        state.put(reg, state.get(reg) - 1);
        programCounter = decrement.nextLineSuccess();
      }
      return false;
    }
    // Instruction is RMHalt
    return true;
  }
}
