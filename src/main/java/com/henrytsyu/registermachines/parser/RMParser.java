package com.henrytsyu.registermachines.parser;

import com.henrytsyu.registermachines.entities.*;
import com.henrytsyu.registermachines.exception.RMDuplicateLineException;
import com.henrytsyu.registermachines.exception.RMException;
import com.henrytsyu.registermachines.exception.RMInstructionException;
import com.henrytsyu.registermachines.exception.RMLabelException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RMParser {
  public Map<Integer, RMInstruction> parseFromFile(String filename) throws RMException {
    return linesToMap(parseLines(RMFileReader.readLinesFromFile(filename)));
  }

  private Map<Integer, RMInstruction> linesToMap(List<RMLine> lines) throws RMException {
    Map<Integer, RMInstruction> map = new HashMap<>();
    for (RMLine line : lines) {
      int lineNumber = line.label().lineNumber();
      if (map.containsKey(lineNumber)) throw new RMDuplicateLineException();
      map.put(lineNumber, line.instruction());
    }
    return map;
  }

  public List<RMLine> parseLines(List<String> lines) throws RMException {
    List<RMLine> rmLines = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
      try {
        rmLines.add(parseLine(lines.get(i)));
      } catch (RMException e) {
        System.err.println("Syntax error on line " + (i + 1));
        throw e;
      }
    }
    return rmLines;
  }

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
      // Parses jump targets for decrement instruction
      String[] targets = operationTargets[1].split(",");
      RMLabel labelSuccess = parseLabel(targets[0]);
      RMLabel labelFail = parseLabel(targets[1]);
      return new RMDecrement(registerNumber, labelSuccess.lineNumber(), labelFail.lineNumber());
    }

    throw new RMInstructionException();
  }
}
