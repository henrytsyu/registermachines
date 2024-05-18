package com.henrytsyu.registermachines.parser;

import com.henrytsyu.registermachines.exception.RMException;
import com.henrytsyu.registermachines.exception.RMInstructionException;
import com.henrytsyu.registermachines.exception.RMLabelException;


public class RMParser {
  public RMLine parseLine(String line) throws RMException {
    String[] labelInstruction = line.split(":");
    if (labelInstruction.length != 2) throw new RMException();

    RMLabel label = parseLabel(labelInstruction[0]);
    RMInstruction instruction = parseInstruction(labelInstruction[1]);
    return new RMLine(label, instruction);
  }

  private RMLabel parseLabel(String label) throws RMException {
    int lineNumber = Integer.parseInt(label.substring(1));
    if (label.charAt(0) != 'L' || lineNumber < 0) throw new RMLabelException();
    return new RMLabel(lineNumber);
  }

  private RMInstruction parseInstruction(String instruction) throws RMException {
    if (instruction.equals("HALT")) return new RMHalt();

    // Splits instruction into operation (Rn[+-]) and targets (Li,Lj)
    String[] operationTargets = instruction.split(">");
    if (operationTargets.length != 2) throw new RMInstructionException();

    // Parses register number and operand
    String operation = operationTargets[0];
    int registerNumber = Integer.parseInt(operation.substring(1, operation.length() - 1));
    if (operation.charAt(0) != 'R' || registerNumber < 0) throw new RMInstructionException();
    char operand = operation.charAt(operation.length() - 1);

    if (operand == '+') {
      // Parses jump target for increment instruction
      RMLabel label = parseLabel(operationTargets[1]);
      return new RMIncrement(registerNumber, label.lineNumber());
    }

    if (operand == '-') {
      String[] targets = operationTargets[1].split(",");
      RMLabel labelSuccess = parseLabel(targets[0]);
      RMLabel labelFail = parseLabel(targets[1]);
      return new RMDecrement(registerNumber, labelSuccess.lineNumber(), labelFail.lineNumber());
    }

    throw new RMInstructionException();
  }
}
