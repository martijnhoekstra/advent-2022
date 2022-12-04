package advent

import cats.syntax.all._
import cats.data.NonEmptyList
import scala.io.Source
import cats.Monoid

given Monoid[List[Int]] with
  override def combine(x: List[Int], y: List[Int]): List[Int] = (x ::: y).sorted.reverse.take(3)
  override def empty: List[Int] = Nil

@main
def thething2 = 
  val it = Source.fromResource("calories").getLines.foldLeft(NonEmptyList.of(0)){
    case (tail, "") => 0 :: tail
    case (NonEmptyList(head, tail), n) => NonEmptyList(head + n.toInt, tail)
  }.foldMap(List(_)).sum 
  println(it)

