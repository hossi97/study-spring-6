package com.hossi.spring6;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sort {

  public List<String> sortByLength(List<String> list) {
    list.sort((o1, o2) -> o2.length() - o1.length());
    return list;
  }
}
