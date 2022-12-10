use std::{fs, collections::VecDeque};

fn input() -> String {
   return fs::read_to_string("../input").expect("Should have been able to read the file");
}

fn read_instructions() -> ([VecDeque<char>;9], Vec<(u32, usize, usize)>){
  let inp = input();
  let mut parts = inp.split("\n\n");
  let drawing = parts.next().unwrap();
  let instruction_block = parts.next().unwrap();
  let mut state: [VecDeque<char>; 9] = Default::default();
  let mut lines = drawing.lines().rev();
  while let Some(line) = lines.next() {
    let chars: Vec<char> = line.chars().collect();
    for i in 0..8 {
      let char_index = (i * 4) + 1;
      if char_index < chars.len() {
        let letter = chars[char_index];
        if letter.is_alphabetic() {
          state[i].push_back(letter);
        }
      }
    }
  }
  
  let instructions: Vec<(u32, usize, usize)> = instruction_block.lines().map(|instruction| -> (u32, usize, usize) {
    let mut parts = instruction.split(" ");
    parts.next();
    let n = parts.next().and_then(|part| part.parse::<u32>().ok()).unwrap();
    parts.next();
    let from = parts.next().and_then(|part| part.parse::<usize>().ok()).unwrap();
    parts.next();
    let to = parts.next().and_then(|part| part.parse::<usize>().ok()).unwrap();
    return (n, from, to);
  }).collect();
  return (state, instructions);

}

fn part1() {
  let (mut state, instructions) = read_instructions();
  for (n, from, to) in instructions {
    for _ in 1..n {
      let f = state[from - 1].pop_front().unwrap();
      state[to - 1].push_front(f);
    }
  }
  print!("the result is");
  for d in state {
    print!("{}", d[0]);
  }
  println!();
}

fn part2() {

    let total: u32 = 1;

    println!("result 2 was {}", total);

}

fn main() {
    part1();
    part2();
}

