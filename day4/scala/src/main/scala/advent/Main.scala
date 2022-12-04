package advent

import cats.syntax.all._
import cats.data.NonEmptyList
import scala.io.Source
import cats.Monoid

def in = Source.fromFile("../input")
def lines = in.getLines().toList

@main
def part1 =
  val it = lines.foldMap(line =>
    val Array(Array(s1, e1), Array(s2, e2)) = line.split(',').map(part => part.split('-').map(_.toInt))
    if (s1 <= s2 && e1 >= e2) 1
    else if (s2 <= s1 && e2 >= e1) 1
    else 0
  )
  println(it)

@main
def part2 =
  val it = lines.foldMap(line =>
    val Array(Array(s1, e1), Array(s2, e2)) = line.split(',').map(part => part.split('-').map(_.toInt))
    if (e1 < s2) 0
    else if (e2 < s1) 0
    else 1
  )
  println(it)
