package com.hossi.spring6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SortTest {
  Sort sort;

  @BeforeEach
  void beforeEach() {
    sort = new Sort();
    System.out.println(this);
  }

  @Test
  void sort1() {
    // 준비 (given)
    // Sort sort = new Sort();

    // 실행 (when)
    List<String> list = sort.sortByLength(Arrays.asList("c", "aa", "bbb"));

    // 검증 (then)
    Assertions.assertThat(list).isEqualTo(List.of("bbb", "aa", "c"));
  }

  @Test
  void sort2() {
    // 준비 (given)
    // Sort sort = new Sort();

    // 실행 (when)
    List<String> list = sort.sortByLength(Arrays.asList("b", "aa", "bbb"));

    // 검증 (then)
    Assertions.assertThat(list).isEqualTo(List.of("bbb", "aa", "b"));
  }
}
