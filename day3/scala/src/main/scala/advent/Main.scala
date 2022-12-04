package advent

import cats.syntax.all._
import cats.data.NonEmptyList
import scala.io.Source
import cats.Monoid

def in = Source.fromFile("../input")

case class Backpack(first: String, second: String):
  def overlap = first.toSet.intersect(second.toSet)
    
def part1 =
  val it = in.getLines().toList.foldMap(line =>
    val at = line.length / 2
    val (f,s) = line.splitAt(at)
    Backpack(f, s).overlap.toList.foldMap(priority)
  )
  println(it)


@main
def part2 = 
  val it = in.getLines().grouped(3).toList.foldMap{
    case Seq(a, b, c) => a.toSet.intersect(b.toSet).intersect(c.toSet).toList.foldMap(priority)
  }
  println(it)
  
def priority(ch: Char) = if (ch >= 'a' && ch <= 'z') ch - 'a' + 1 else ch - 'A' + 27
