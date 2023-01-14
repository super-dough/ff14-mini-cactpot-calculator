import com.google.common.collect.ImmutableSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Calculator {
  public static HashMap<Integer, Integer> scoreTable;

  public static void init() {
    scoreTable = new HashMap<>();
    try {
      BufferedReader in = new BufferedReader(new FileReader("score_table.txt"));
      String str;

      while ((str = in.readLine()) != null) {
        String[] s = str.split(" ");
        scoreTable.put(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
      }

    } catch (IOException e) {
      System.err.println(e);
    }
  }

  /**
   * 计算并打印当前盘面每一步的期望
   *
   * @param board
   * @param outer 是否是最外面一层递归。我们只打印最外一层递归的每一步的期望
   * @return
   */
  public static NextMove calculate(Board board, boolean outer) {
    int filledNumber = board.filledNumberCounts();
    ImmutableSet<Integer> avas = board.getAvailable();
    int k = 0;
    double maxEv = 0;

    // 如果已经翻开了4个数字，现在计算每一条直线的期望
    if (filledNumber == 4) {
      for (int i = 1; i <= 8; i++) {
        double ev = calcEv(board.getLine(i), avas);
        if (outer) System.out.println(NextMove.chooseLine(i, ev));
        if (ev > maxEv) {
          maxEv = ev;
          k = i;
        }
      }
      return NextMove.chooseLine(k, maxEv);
    }

    // 翻数字阶段，递归调用calculate
    for (int i = 1; i <= 9; i++) {
      if (board.get(i) == 0) {
        double evSum = 0;
        Board newBoard = board.copy();
        for (int c : board.getAvailable()) {
          newBoard.set(i, c);
          NextMove nextMove = calculate(newBoard, false);
          evSum += nextMove.ev;
          newBoard.set(i, 0);
        }

        double ev = evSum / board.getAvailable().size();
        if (outer) System.out.println(NextMove.openCell(i, ev));
        if (ev > maxEv) {
          maxEv = ev;
          k = i;
        }
      }
    }
    return NextMove.openCell(k, maxEv);
  }

  /**
   * 计算一条直线的期望
   *
   * @param filled 这条直线上已有的数字
   * @param candidates 空白的格子里，可以填的数字
   * @return
   */
  static double calcEv(ImmutableSet<Integer> filled, ImmutableSet<Integer> candidates) {
    if (filled.size() > 3) {
      throw new RuntimeException();
    }
    if (filled.size() == 3) {
      return scoreTable.get(filled.stream().reduce(0, (a, b) -> a + b));
    }
    int k = candidates.size();
    double ret = 0;
    for (Integer c : candidates) {
      ret +=
          calcEv(
              ImmutableSet.<Integer>builder().addAll(filled).add(c).build(),
              candidates.stream()
                  .filter(cc -> !cc.equals(c))
                  .collect(ImmutableSet.toImmutableSet()));
    }
    return ret / k;
  }
}
