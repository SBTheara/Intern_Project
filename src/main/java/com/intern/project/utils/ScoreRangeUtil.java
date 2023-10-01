package com.intern.project.utils;

import com.intern.project.dto.ScoreRange;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScoreRangeUtil {
  public static ScoreRange scoreRange(String options) {
    switch (options) {
      case "10-50" -> {
        return ScoreRange.builder().minScore(10).maxScore(50).build();
      }
      case "50-100" -> {
        return ScoreRange.builder().minScore(50).maxScore(100).build();
      }
      default -> {
        return new ScoreRange();
      }
    }
  }
}
