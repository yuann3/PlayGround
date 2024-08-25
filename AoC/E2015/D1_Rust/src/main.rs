use std::fs;

fn main() {
    let input = fs::read_to_string("/Users/yuan/Developer/PlayGround/AoC/E2015/D1_Rust/input.txt")
            .expect("NG");

    let mut floor: i32 = 0;
    let mut position: usize = 0;
    let mut basement = false;

    for (i, c) in input.chars().enumerate(){
        match c {
            '(' => floor += 1,
            ')' => floor -= 1,
            _ => continue,
        }

        position = 1 + i;
        if floor == -1 && !basement {
            println!("Enter the basement at position {}", position);
            basement = true;
        }
    }
    // part one
    println!("Final Floor {}", floor);
}
