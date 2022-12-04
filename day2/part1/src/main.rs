use std::fs;

fn main() {
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
