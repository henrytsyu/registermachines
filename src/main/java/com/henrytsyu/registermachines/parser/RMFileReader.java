package com.henrytsyu.registermachines.parser;

import com.henrytsyu.registermachines.exception.RMException;
import com.henrytsyu.registermachines.exception.RMFileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RMFileReader {
  public static List<String> readLinesFromFile(String filename) throws RMException {
    List<String> lines = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      throw new RMFileException(e);
    }
    return lines;
  }
}
