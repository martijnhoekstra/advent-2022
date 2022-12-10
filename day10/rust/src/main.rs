use std::fs;

fn input() -> String {
   return fs::read_to_string("../input").expect("Should have been able to read the file");
}

fn part1() {

    let total: u32 = input().lines().map(|line| {
      let mut parts = line.split(',').map(|part| part.split('-').map(|num| num.parse::<u32>().unwrap()));
      let mut first = parts.next().unwrap();
      let s1 = first.next().unwrap();
      let e1 = first.next().unwrap();
      let mut second = parts.next().unwrap();
      let s2 = second.next().unwrap();
      let e2 = second.next().unwrap();
      
      if s1 <= s2 && e1 >= e2 {1}
      else if s2 <= s1 && e2 >= e1 {1}
      else {0}

    }).sum();

    println!("result 1 was {}", total);

}

fn part2() {

    let total: u32 = input().lines().map(|line| {
      let mut parts = line.split(',').map(|part| part.split('-').map(|num| num.parse::<u32>().unwrap()));
      let mut first = parts.next().unwrap();
      let s1 = first.next().unwrap();
      let e1 = first.next().unwrap();
      let mut second = parts.next().unwrap();
      let s2 = second.next().unwrap();
      let e2 = second.next().unwrap();
      
      if e1 < s2 {0}
      else if e2 < s1 {0}
      else {1}

    }).sum();

    println!("result 2 was {}", total);

}

fn main() {
    part1();
    part2();
}

