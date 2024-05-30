package com.henrytsyu.registermachines.runner;

import com.henrytsyu.registermachines.parser.RMDecrement;
import com.henrytsyu.registermachines.parser.RMIncrement;
import com.henrytsyu.registermachines.parser.RMInstruction;

public class RMRunner {

  public boolean runInstruction(RMInstruction instruction) {
    if (instruction instanceof RMIncrement increment) {
      return false;
    } else if (instruction instanceof RMDecrement decrement) {
      return false;
    } else {
      // instruction instanceof RMHalt
      return true;
    }
  }
}
