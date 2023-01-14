import static org.junit.Assert.*;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.lang.Math;

public class CalculatorTest {

  @org.junit.Before
  public void setUp() throws Exception {
    Calculator.init();
  }

  @org.junit.After
  public void tearDown() throws Exception {
  }

  @Test
  public void calculate_4filled() {
    Board board = new Board();
    board.set(1, 1);
    board.set(5, 2);
    board.set(9, 3);
    board.set(3, 9);
    NextMove nextMove = Calculator.calculate(board, true);
    assertEquals(nextMove.move, 7);
    assertDouble(nextMove.ev, 10000);
  }

  @Test
  public void ev_allFilled() {
    assertDouble(Calculator.calcEv(ImmutableSet.of(1, 2, 3), ImmutableSet.of(4, 5,6)), 10000);
    assertDouble(Calculator.calcEv(ImmutableSet.of(1, 9, 2), ImmutableSet.of(4, 5,6)), 108);
  }

  @Test
  public void ev_missing_num() {
    assertDouble(Calculator.calcEv(ImmutableSet.of(1, 2), ImmutableSet.of(3, 4)), 5018);
    assertDouble(Calculator.calcEv(ImmutableSet.of(7, 8), ImmutableSet.of(1, 9)), 1836);
  }

  private void assertDouble(double a, double b) {
    assertTrue(Math.abs(a - b) / Math.min(a, b) < 1e-6);
  }
}