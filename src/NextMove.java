public class NextMove {
  enum MoveType {
    CELL,
    COLUMN
  }

  MoveType type;
  int move;
  double ev;

  NextMove(MoveType moveType, int move, double ev) {
    this.type = moveType;
    this.move = move;
    this.ev = ev;
  }

  public static NextMove openCell(int num, double ev) {
    return new NextMove(MoveType.CELL, num, ev);
  }

  public static NextMove chooseLine(int num, double ev) {
    return new NextMove(MoveType.COLUMN, num, ev);
  }

  @Override
  public String toString() {
    if (type == MoveType.CELL) {
      return "选择第" + move + "号格子，获利期望：" + ev;
    }
    if (move <= 3) {
      return "选择第" + move + "排，获利期望：" + ev;
    }
    if (move <= 6) {
      return "选择第" + (move - 3) + "列，获利期望：" + ev;
    }
    if (move == 7) return "选择左上到右下，获利期望：" + ev;
    return "选择左下到右上，获利期望：" + ev;
  }
}
