use std::{fs, u8};
use std::collections::HashSet;

fn part1() {
    let file_path = "../input";

    let contents = fs::read_to_string(file_path)
        .expect("Should have been able to read the file");

    let total: u32 = contents.lines().map(|line| {
      let at = line.len() / 2;
      let (first, second) = line.split_at(at);
      let front_compartment:HashSet<&u8> = HashSet::from_iter(first.as_bytes());
      let hind_compartment: HashSet<&u8> = HashSet::from_iter(second.as_bytes());
      let intersection = front_compartment.intersection(&hind_compartment);
      intersection.map(|b| priority(**b)).sum::<u32>()
    }).sum();



    println!("sum of prorities was {}", total);

}

fn part2() {
    let file_path = "../input";

    let contents = fs::read_to_string(file_path)
        .expect("Should have been able to read the file");

    let line_iter = contents.lines();

    let total: u32 = &line_iter.chunks(3).map(|line| {
      let at = line.len() / 2;
      let (first, second) = line.split_at(at);
      let front_compartment:HashSet<&u8> = HashSet::from_iter(first.as_bytes());
      let hind_compartment: HashSet<&u8> = HashSet::from_iter(second.as_bytes());
      let intersection = front_compartment.intersection(&hind_compartment);
      intersection.map(|b| priority(**b)).sum::<u32>()
    }).sum();
}

fn main() {
    part1();
    part2();
}

fn priority(byte: u8) -> u32 {
    // Uppercase item types A through Z have priorities 27 through 52.
    if byte < 91 {
        return (byte as u32) - 65 + 27;
    }
    // Lowercase item types a through z have priorities 1 through 26.
    else {
        return (byte as u32) - 97 + 1;
    }
}
