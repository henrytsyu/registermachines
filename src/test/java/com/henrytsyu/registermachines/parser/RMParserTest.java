package com.henrytsyu.registermachines.parser;

import com.henrytsyu.registermachines.exception.RMException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RMParserTest {
  private final RMParser parser = new RMParser();

  @Test
  public void parsingInvalidLineThrowsException() {
    assertThrows(RMException.class, () -> parser.parseLine("Hello World"));
  }

  @Test
  public void parsesLabel() throws RMException {
    RMLine line = parser.parseLine("L0:HALT");
    assertEquals(new RMLabel(0), line.label());
  }

  @Test
  public void parsesHalt() throws RMException {
    RMLine line = parser.parseLine("L0:HALT");
    assertEquals(new RMHalt(), line.instruction());
  }

  @Test
  public void parsesIncrement() throws RMException {
    RMLine line = parser.parseLine("L0:R0+>L0");
    assertEquals(new RMIncrement(0, 0), line.instruction());
  }

  @Test
  public void parsesDecrement() throws RMException {
    RMLine line = parser.parseLine("L0:R0->L0,L1");
    assertEquals(new RMDecrement(0, 0, 1), line.instruction());
  }

  @Test
  public void parsesLines() throws RMException {
    List<RMLine> lines = parser.parseLines(List.of("L0:R0->L0,L1", "L1:R1+>L2", "L2:HALT"));
    assertEquals(new RMLabel(0), lines.get(0).label());
    assertEquals(new RMDecrement(0, 0, 1), lines.get(0).instruction());
    assertEquals(new RMLabel(1), lines.get(1).label());
    assertEquals(new RMIncrement(1, 2), lines.get(1).instruction());
    assertEquals(new RMLabel(2), lines.get(2).label());
    assertEquals(new RMHalt(), lines.get(2).instruction());
  }

  @Test
  public void parsesLinesFromFile() throws RMException {
    Map<Integer, RMInstruction> instructionMap = parser.parseFromFile("src/test/resources/simple.rm");
    assertEquals(new RMDecrement(0, 0, 1), instructionMap.get(0));
    assertEquals(new RMIncrement(1, 2), instructionMap.get(1));
    assertEquals(new RMHalt(), instructionMap.get(2));
  }
}
