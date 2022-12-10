package advent

import cats.syntax.all._
import scala.io.Source
import scala.collection.immutable.BitSet
import scala.collection.immutable.ArraySeq

object In {
  def src = Source.fromFile("../input")
  def instring = src.iterator.mkString("")
  def lines = src.getLines().toList
}

val heights = ArraySeq.tabulate(100 * 99 - 1)(i => Character.digit(In.instring(i), 10))

def height(row: Int, col: Int) = heights(index(row, col))
def index(row: Int, col: Int) = col + (100 * row)

@main
def part1 =
  def scanRow(row: Int): Set[Int] =
    val there = (0 until 99)
      .foldLeft(-1 -> BitSet()) { case ((max, seen), col) =>
        val h = height(row, col)
        if h > max then h -> seen.incl(index(row, col))
        else max -> seen
      }
      ._2
    def back(col: Int, seen: Set[Int], max: Int): Set[Int] =
      if seen.contains(index(row, col)) then seen
      else
        val h = height(row, col)
        if h > max then back(col - 1, seen.incl(index(row, col)), h)
        else back(col - 1, seen, max)
    back(98, there, -1)

  def scanCol(col: Int): Set[Int] =
    val there = (0 until 99)
      .foldLeft(-1 -> BitSet()) { case ((max, seen), row) =>
        val h = height(row, col)
        if h > max then h -> seen.incl(index(row, col))
        else max -> seen
      }
      ._2
    def back(row: Int, seen: Set[Int], max: Int): Set[Int] =
      println(s"back $row, $col")
      if seen.contains(index(row, col)) then seen
      else
        val h = height(row, col)
        if h > max then back(row - 1, seen.incl(index(row, col)), h)
        else back(row - 1, seen, max)
    back(98, there, -1)

  val visible = (0 until 99).toList.foldMap(i => scanRow(i).union(scanCol(i)))
  println(s"size of the set is ${visible.size}")

@main
def part2 =
  def scenery(row: Int, col: Int) = {
    val ownHeight = height(row, col)
    val right = (col + 1 until 99).map(c => height(row, c))
    val left = (0 until col).reverse.map(c => height(row, c))
    val up = (0 until row).reverse.map(r => height(r, col))
    val down = (row + 1 until 99).map(r => height(r, col))
    List(right, left, up, down)
      .map { line =>
        val last = line.indexWhere(_ >= ownHeight)
        if (last == -1) line.length
        else last + 1
      }
      .map(_.toLong)
      .product

  }
  val sceneries = for {
    row <- 1 until 98 // prune the borders, they're always 0
    col <- 1 until 98
  } yield (row, col, scenery(row, col))
  println(sceneries.maxBy(_._3))
