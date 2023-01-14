import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.Collections;

class Board {
  private ArrayList<Integer> data;

  Board() {
    data = new ArrayList<Integer>(Collections.nCopies(9, 0));
  }

  Board(ArrayList<Integer> data) {
    this.data = new ArrayList<>(data);
  }

  Board copy() {
    return new Board(this.data);
  }

  int filledNumberCounts() {
    return (int) data.stream().filter(v -> v > 0).count();
  }

  /**
   * 还有哪些数字是没有被翻开的
   */
  ImmutableSet<Integer> getAvailable() {
    ImmutableSet.Builder<Integer> builder = ImmutableSet.builder();
    for (int i = 1; i <= 9; i++) {
      if (!data.contains(i)) builder.add(i);
    }
    return builder.build();
  }

  /**
   *  1~3, row 1 ~ 3
   *  4~6, column 1 ~ 3
   *  7, main diagonal
   *  8, another diagonal
   * */
  ImmutableSet<Integer> getLine(int k) {
    ImmutableSet<Integer> line = getLine_(k);
    return line.stream().filter(c -> c >0 ).collect(ImmutableSet.toImmutableSet());
  }

  ImmutableSet<Integer> getLine_(int k) {
    if (k <= 3) {
      return ImmutableSet.of(get(k*3 - 2), get(k*3 - 1), get(k*3));
    }
    if (k <= 6) {
      k -= 3;
      return ImmutableSet.of(get(0+k), get(3+k), get(6+k));
    }
    if (k == 7) {
      return ImmutableSet.of(get(1), get(5), get(9));
    }
    return ImmutableSet.of(get(3), get(5), get(7));
  }

  int get(int idx){
    return data.get(idx - 1);
  }

  int get(int idx, int idy) {
    return data.get(idx * 3 + idy - 4);
  }

  void set(int idx, int value) {
    data.set(idx - 1, value);
  }

  void set(int idx, int idy, int value) {
    data.set(idx * 3 + idy - 4, value);
  }
}
