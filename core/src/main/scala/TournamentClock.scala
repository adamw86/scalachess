package chess

import cats.syntax.all.*

import Clock.{ LimitSeconds, IncrementSeconds }

case class TournamentClock(limitSeconds: LimitSeconds, incrementSeconds: IncrementSeconds):

  def toClockConfig: Option[Clock.Config] =
    Clock.Config(limitSeconds, incrementSeconds).some

object TournamentClock:

  object parse:

    private val cleanRegex = "(move|minutes|minute|min|m|seconds|second|sec|s|'|\"|/)".r

    private def make(a: Int, b: Int) =
      val limit = LimitSeconds(if a > 180 then a else a * 60)
      TournamentClock(limit, IncrementSeconds(b))

    def apply(str: String): Option[TournamentClock] =
      cleanRegex
        .replaceAllIn(str.toLowerCase, "")
        .replace(" ", "")
        .split('+') match
        case Array(a)    => a.toIntOption.map(make(_, 0))
        case Array(a, b) => (a.toIntOption, b.toIntOption).mapN(make)
        case _           => none
