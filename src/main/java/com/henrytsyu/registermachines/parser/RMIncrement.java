package com.henrytsyu.registermachines.parser;

public record RMIncrement(int registerNumber, int nextLine) implements RMInstruction {
}
