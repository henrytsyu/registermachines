package com.henrytsyu.registermachines.parser;

public record RMDecrement(int registerNumber, int nextLineSuccess, int nextLineFail) implements RMInstruction {
}
