package com.henrytsyu.registermachines.parser;

import com.henrytsyu.registermachines.exception.RMException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RMFileReaderTest {
  @Test
  public void readFailsForIncorrectFilename() {
    assertThrows(RMException.class, () -> RMFileReader.readLinesFromFile("src/test/resources/example.txt"));
  }

  @Test
  public void readsLinesFromCorrectFilename() throws RMException {
    assertEquals(List.of("Hello World", "My name is Henry"), RMFileReader.readLinesFromFile("src/test/resources/example.rm"));
  }
}