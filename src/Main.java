import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Calculator.init();
    Board board = new Board();
    while (true) {
      System.out.println("输入棋盘");
      Scanner scanner = new Scanner(System.in);
      for (int i = 0; i < 9; i++) {
        int x = scanner.nextInt();
        board.set(i + 1, x);
      }

      System.out.println("\n最佳:\n" + Calculator.calculate(board, true));
    }
  }
}
