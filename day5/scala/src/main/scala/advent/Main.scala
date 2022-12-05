package advent

import cats.syntax.all._
import scala.io.Source
import cats.Monoid
import cats.data.State

def in = Source.fromFile("../input")
def instring = in.iterator.mkString("")
def lines = in.getLines().toList

val Array(drawing, moveblock) = instring.split("\n\n")

def parseRow(row: String): List[Option[Char]] = row.grouped(4).map(crate => Option(crate(1)).filter(ch => Character.isLetter(ch))).toList.padTo(3, None)

val state = drawing.split('\n').toList.init.map(parseRow).transpose.map(_.collect { case Some(x) => x })

def readState(state: Vector[List[Char]]) = state
  .map(_.headOption.getOrElse(" "))
  .mkString("")

def moves = moveblock.split('\n').toList.map { case s"move $n from $from to $to" =>
  (n.toInt, from.toInt - 1, to.toInt - 1)
}

def performMove(n: Int, ifrom: Int, ito: Int) = (state: Vector[List[Char]]) =>
  val from = state(ifrom)
  val to = state(ito)
  val (taken, remaining) = from.splitAt(n)
  state.updated(ifrom, remaining).updated(ito, taken ::: to)

//what's the problem?
given [A]: Monoid[A => A] with
  def empty = identity
  def combine(first: A => A, second: A => A) = first.andThen(second)

@main
def part1 =
  val op = moves
    .flatMap { case (n, from, to) => List.fill(n)(1, from, to) }
    .foldMap(performMove.tupled)
  println(readState(op(state.toVector)))

@main
def part2 =
  val op = moves.foldMap(performMove.tupled)
  val endstate = op(state.toVector)
  println(readState(endstate))
