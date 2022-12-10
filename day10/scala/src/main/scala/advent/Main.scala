package advent

import cats.syntax.all._
import scala.io.Source
import cats.Monoid
import cats.data.AndThen
import cats.data.NonEmptyList

object In:
  def src = Source.fromFile("../input")
  def instring = src.iterator.mkString("")
  def lines = src.getLines().toList
  def example = List(
    "noop",
    "addx 3",
    "addx -5"
  )

val states = In.lines.foldMap {
  case "noop"     => List(0)
  case s"addx $d" => List(0, d.toInt)
}

val steps = states
  .scanLeft(1) { case (x, d) =>
    x + d
  }
  .zipWithIndex
  .map { case (x, i) => (x, i + 1) }

@main
def part1 =
  val score = steps.collect { case (x, i) if i == 20 || (i - 20) % 40 == 0 => x * i }.sum
  println(score)

@main
def part2 =
  steps
    .map(_._1)
    .grouped(40)
    .map(_.zipWithIndex.map { case (x, i) => if i <= x + 1 && i >= x - 1 then "#" else "." }.mkString(""))
    .foreach(println)
