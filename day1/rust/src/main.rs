use std::fs;

fn part1() {
    let file_path = "../input";

    let contents = fs::read_to_string(file_path)
        .expect("Should have been able to read the file");
    
    let mut group = 0;
    let mut max = 0;
    contents.lines().for_each(|line| {
        if line.is_empty() {
            if group > max {
                max = group;
            }
            group = 0;
        } else {
            group += line.parse::<i32>().unwrap();
        }
    });
    if group > max {
        max = group;
    } 
    
    println!("max was {}", max);
}


fn part2() {
    let file_path = "calories";
    println!("In file {}", file_path);

    let contents = fs::read_to_string(file_path)
        .expect("Should have been able to read the file");
    
    let mut max1:i64 = 0;
    let mut max2:i64 = 0;
    let mut max3:i64 = 0;
    let mut group:i64 = 0;
    contents.lines().for_each(|line| {
        if line.is_empty() {
            if group > max1 {
                max3 = max2;
                max2 = max1;
                max1 = group;
            }
            else if group > max2 {
                max3 = max2;
                max2 = group;
            }
            else if group > max3 {
                max3 = group;
            }
            group = 0;
        } else {
            group += line.parse::<i64>().unwrap();
        }
    });
    if group > max1 {
        max3 = max2;
        max2 = max1;
        max1 = group;
    }
    else if group > max2 {
        max3 = max2;
        max2 = group;
    }
    else if group > max3 {
        max3 = group;
    }
    
    println!("max was {}", max1 + max2 + max3);
}

fn main() {
    part2();
}