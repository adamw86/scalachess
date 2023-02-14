package chess
package bitboard

import munit.ScalaCheckSuite
import org.scalacheck.Prop.forAll
import org.scalacheck.Prop.propBoolean

import Arbitraries.given
import Bitboard.*

class OpaqueBitboardTest extends ScalaCheckSuite:

  test("the result of addPos should contain the added pos") {
    forAll { (bb: Bitboard, pos: Pos) =>
      assertEquals((bb.addPos(pos).contains(pos)), true)
    }
  }

  test("pos.bb.singleSquare == Some(pos)") {
    forAll { (pos: Pos) =>
      assertEquals(pos.bb.singleSquare, Some(pos))
    }
  }

  test("count has the same value as occupiedSquares.size") {
    forAll { (bb: Bitboard) =>
      assertEquals(bb.count, bb.occupiedSquares.size)
    }
  }

  test("moreThanOne should be true when count > 1") {
    forAll { (bb: Bitboard) =>
      assertEquals(bb.moreThanOne, bb.count > 1)
    }
  }

  test("if moreThanOne then first is defined") {
    forAll { (bb: Bitboard) =>
      bb.moreThanOne ==> bb.first.isDefined
    }
  }

  test("singleSquare should be defined when count == 1") {
    forAll { (bb: Bitboard) =>
      assertEquals(bb.singleSquare.isDefined, bb.count == 1)
    }
  }

  test("fold removeFirst should return empty") {
    forAll { (bb: Bitboard) =>
      assertEquals(bb.fold(bb)((b, _) => b.removeFirst), Bitboard.empty)
    }
  }

  test("bitboard created by set of pos should contains and only contains those pos") {
    forAll { (xs: Set[Pos]) =>
      val bb = Bitboard(xs)
      assertEquals(xs.forall(bb.contains), true)
      assertEquals(xs.size, bb.count)
    }
  }

  test("apply set of pos should be the same as using addPos") {
    forAll { (xs: Set[Pos]) =>
      val bb  = Bitboard(xs)
      val bb2 = xs.foldLeft(Bitboard.empty)(_ addPos _)
      assertEquals(bb, bb2)
    }
  }

  test("nonEmpty bitboard should have at least one square") {
    forAll { (bb: Bitboard) =>
      assertEquals(bb.nonEmpty, bb.first.isDefined)
    }
  }

  test("first should be the minimum of occupiedSquares") {
    forAll { (bb: Bitboard) =>
      assertEquals(bb.first, bb.occupiedSquares.minByOption(_.value))
    }
  }
