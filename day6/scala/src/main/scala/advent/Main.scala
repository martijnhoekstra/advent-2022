package advent

import cats.syntax.all._
import scala.io.Source
import cats.Monoid

object In {
  def src = Source.fromFile("../input")
  def instring = src.iterator.mkString("")
  def lines = src.getLines().toList
}

@main
def part1 =
  val startIndex = In.src.iterator.sliding(4).zipWithIndex.collectFirst {
    case (l, i) if l.toSet.size == 4 => i 
  }
  println(startIndex.get + 4)

@main
def part2 =
  val startIndex = In.src.iterator.sliding(14).zipWithIndex.collectFirst {
    case (l, i) if l.toSet.size == 14 => i 
  }
  println(startIndex.get + 14)