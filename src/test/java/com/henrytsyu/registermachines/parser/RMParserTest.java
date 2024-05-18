package com.henrytsyu.registermachines.parser;

import com.henrytsyu.registermachines.exception.RMException;
import org.junit.jupiter.api.Test;

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
}
