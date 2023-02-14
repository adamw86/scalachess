package chess
package bitboard

import org.scalacheck.{ Arbitrary, Gen }

object Arbitraries:

  given Arbitrary[File] = Arbitrary(Gen.oneOf(File.all))
  given Arbitrary[Rank] = Arbitrary(Gen.oneOf(Rank.all))
  given Arbitrary[Pos]  = Arbitrary(Gen.oneOf(Pos.all))

  given Arbitrary[Bitboard] = Arbitrary(Gen.long.map(Bitboard(_)))
