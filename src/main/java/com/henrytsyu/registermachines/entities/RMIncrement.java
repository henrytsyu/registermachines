package com.henrytsyu.registermachines.entities;

public record RMIncrement(int registerNumber, int nextLine) implements RMInstruction {
}
