use std::fs;

fn part1() {
    let file_path = "../input";
    println!("In file {}", file_path);

    let contents = fs::read_to_string(file_path)
        .expect("Should have been able to read the file");

    let it: i32 = contents.lines().map(|next| match next {
        "A X" => 4,
        "A Y" => 8,
        "A Z" => 3,
        "B X" => 1,
        "B Y" => 5,
        "B Z" => 9,
        "C X" => 7,
        "C Y" => 2,
        "C Z" => 6,
        &_ => todo!()
    }).sum();
    println!("contents was {}", it);

}

fn part2() {
    let file_path = "../input";

    let contents = fs::read_to_string(file_path)
        .expect("Should have been able to read the file");

    let it: i32 = contents.lines().map(|next| match next {
        "A X" => 0 + 3,
        "A Y" => 3 + 1,
        "A Z" => 6 + 2,
        "B X" => 0 + 1,
        "B Y" => 3 + 2,
        "B Z" => 6 + 3,
        "C X" => 0 + 2,
        "C Y" => 3 + 3,
        "C Z" => 6 + 1,
        &_ => todo!()
    }).sum();
    println!("contents was {}", it);
}

def main() {
    part1();
    part2();
}
