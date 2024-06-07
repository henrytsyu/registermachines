package com.henrytsyu.registermachines.entities;

public record RMDecrement(int registerNumber, int nextLineSuccess, int nextLineFail) implements RMInstruction {
}
