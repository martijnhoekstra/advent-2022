package advent

import cats.syntax.all._
import scala.io.Source
import cats.Monoid
import cats.instances.list

object In {
  def example = """$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k""".split("\n").toList
  def src = Source.fromFile("../input")
  def instring = src.iterator.mkString("")
  def lines = src.getLines().toList
}

enum Path:
  case File(size: Int)
  case Directory(paths: Map[String, Path])

given Monoid[Path] with
  def empty = emptyDir
  def combine(p1: Path, p2: Path) = (p1, p2) match
    case (Path.Directory(paths1), Path.Directory(paths2)) =>
      println(s"combining $paths1 and $paths2")
      Path.Directory(paths1 |+| paths2)
    case (a, b) if a == b                                 => a
    case _                                                => throw new Exception(s"it makes no sense to combine $p1 and $p2") // make this lawful by just returning p1

enum Command:
  case CDown(sub: String)
  case CDUp
  case LS(listings: List[Listing])

enum Listing:
  case File(name: String, size: Int)
  case Dir(name: String)

def emptyDir: Path.Directory = Path.Directory(Map.empty)


val commands = In.lines.map {
  case "$ ls"         => Right(Command.LS(Nil))
  case s"dir $name"   => Left(Listing.Dir(name))
  case "$ cd .."      => Right(Command.CDUp)
  case s"$$ cd $name" => Right(Command.CDown(name))
  case s"$size $name" => Left(Listing.File(name, size.toInt))
}.toList


val fullCommands = commands.tail
  .foldLeft(List.empty[Command]) {
    case (Command.LS(files) :: tail, Left(listing)) => Command.LS(listing :: files) :: tail
    case (_, Left(listing))                         => ???
    case (l, Right(command))                        => command :: l
  }
  .reverse


import Path.*
import Command.*

def dirFromListing(ls: Command.LS): Directory = Directory(ls.listings.map {
  case Listing.Dir(name)        => name -> Directory(Map.empty)
  case Listing.File(name, size) => name -> File(size)
}.toMap)

def dirAtPath(path: List[String], contents: Directory): Directory = path match {
  case Nil          => contents
  case head :: tail => dirAtPath(tail, Directory(Map(head -> contents)))
}

val trees = fullCommands.foldLeft((List.empty[String], List(emptyDir))) {
  case ((Nil, _), CDUp)           => throw new Exception("CD'ed up out of root")
  case ((_ :: tail, dirs), CDUp)  => (tail, dirs)
  case ((path, dirs), ls @ LS(_)) => (path, dirAtPath(path, dirFromListing(ls)) :: dirs)
  case ((path, dirs), CDown(down)) => (down :: path, dirs)
}._2.asInstanceOf[List[Path]]

val tree = trees.combineAll //foldMap(identity)

@main
def part1 =
  def sum(tree: Path): (Long, Long) = tree match
    case Path.File(size) => 0L -> size
    case Path.Directory(paths) =>
    
      val childrenSizes = paths.values.toList.map{
        case File(size) => Left(size)
        case dir => Right(sum(dir))
      }

      val fileSize = childrenSizes.collect{ case Left(f) => f}.sum
      val subdirSize = childrenSizes.collect { case Right((_,s)) => s}.sum
      val grandChildren = childrenSizes.collect { case Right((g, _)) => g}.sum

      val ownSize = fileSize + subdirSize
      val reportSize = if (ownSize <= 100000) ownSize + grandChildren else grandChildren
      (reportSize -> ownSize)


  println(sum(tree)._1) // 5596880


@main
def part2 =
  def totalSize(tree: Path): Int = tree match
    case Path.File(size) => size
    case Path.Directory(paths) => paths.values.toList.foldMap(totalSize)
  
  val available = 70000000 - totalSize(tree)
  val toFree = 30000000 - available

  def visit(tree: Path, smallest: Int): (Int, Int) = tree match
    case Path.File(size) => smallest -> size
    case Path.Directory(paths) =>
      val (smallests, sizes) = paths.values.map(visit(_, smallest)).toList.separate
      val size = sizes.sum
      val newSmallest = (size :: smallest :: smallests).filter(_ >= toFree).min
      newSmallest -> size

  val result = visit(tree, Int.MaxValue)

  println(s"the smallest directory able to free up $toFree is ${result._1}")
  



