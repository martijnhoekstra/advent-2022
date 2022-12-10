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

case class Pos(private val pos: (Int, Int)) extends AnyVal:
  def row = pos._1
  def col = pos._2
  def +(m: Move) = Pos((row + m.row, col + m.col))
  def -(that: Pos) = Move((row - that.row, col - that.col))

case class Move(private val pos: (Int, Int)) extends AnyVal:
  def +(that: Move): Move = Move((pos._1 + that.pos._1, pos._2 + that.pos._2))
  def -(that: Move): Move = Move((pos._1 - that.pos._1, pos._2 - that.pos._2))
  def unary_- : Move = Move((-pos._1, -pos._2))
  def row = pos._1
  def col = pos._2

given [A]: Monoid[A => A] with
  val empty = identity
  def combine(first: A => A, second: A => A) = AndThen(first).andThen(second)

val moves: List[Move] = In.lines.flatMap { case s"$dir $n" =>
  List.fill(n.toInt) {
    dir match
      case "L" => Move((0, -1))
      case "R" => Move((0, 1))
      case "U" => Move((1, 0))
      case "D" => Move((-1, 0))
  }
}

given Monoid[Move] with
  val empty = Move((0, 0))
  def combine(first: Move, second: Move) = first + second

@main
def part1 =

  case class Solution(visited: Set[Pos], headPosition: Pos, tailDispacement: Move)

  def doMove(move: Move): Solution => Solution = (s: Solution) => {
    // if we move opposite to the tail displacement, the tail moves.
    // if the tail moves, its displacement becomes the opposite of the move
    // if the tail doesn't move, it's displacement becomes the previous displacement - the move
    val newDisplacement: Move =
      if move.row == -s.tailDispacement.row && move.row != 0 || move.col == -s.tailDispacement.col && move.col != 0 then -move
      else s.tailDispacement - move
    val newHead = s.headPosition + move
    val tailPostion = newHead + newDisplacement
    Solution(s.visited + tailPostion, newHead, newDisplacement)
  }

  val allMoves = moves.foldMap(doMove)
  val endState = allMoves(Solution(Set(Pos(0, 0)), Pos(0, 0), Move(0, 0)))
  println(endState.visited.size)

@main
def part2 =
  case class Solution(visited: Set[Pos], rope: NonEmptyList[Pos])

  def closeEnough(leader: Pos, follower: Pos) =
    val dif = (leader - follower)
    dif.col.abs <= 1 && dif.row.abs <= 1

  def moveCloser(leader: Pos, follower: Pos) =
    def closer(l: Int, f: Int) = if l > f then f + 1 else if l < f then f - 1 else f
    def closerRow = closer(leader.row, follower.row)
    def closerCol = closer(leader.col, follower.col)
    Pos(closerRow, closerCol)

  def doMove(move: Move): Solution => Solution = (s: Solution) =>
    def rec(remainder: List[Pos], moved: NonEmptyList[Pos]): Solution =
      remainder match
        case Nil => Solution(s.visited.incl(moved.head), moved.reverse)
        case next :: tail =>
          val moveTo = if closeEnough(moved.head, next) then next else moveCloser(moved.head, next)
          rec(tail, moveTo :: moved)
    val newHead = s.rope.head + move
    rec(s.rope.tail, NonEmptyList.of(newHead))

  val move = moves.foldMap(doMove)
  val result = move(Solution(Set.empty, NonEmptyList.fromListUnsafe(List.fill(10)(Pos(0,0)))))
  println(result._1.size)