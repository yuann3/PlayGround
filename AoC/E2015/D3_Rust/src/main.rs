use std::{collections::HashMap, fs};

#[derive(Hash, Clone, PartialEq, Eq)]
struct Cordinate {
    x: i32,
    y: i32,
}

fn main() {
    let input = fs::read_to_string("input.txt").expect("Fail to read file");

    let mut santa_cordinate = Cordinate { x: 0, y: 0 };
    let mut robot_cordinate = Cordinate { x: 0, y: 0 };
    let mut visited = HashMap::new();

    visited.insert(santa_cordinate.clone(), 1);
    visited.insert(robot_cordinate.clone(), 1);

    for (i, c) in input.chars().enumerate() {
        if i % 2 == 0 {
            match c {
                '^' => santa_cordinate.y += 1,
                'v' => santa_cordinate.y -= 1,
                '>' => santa_cordinate.x += 1,
                '<' => santa_cordinate.x -= 1,
                _ => continue,
            }
            *visited.entry(santa_cordinate.clone()).or_insert(0) += 1;
        } else {
            match c {
                '^' => robot_cordinate.y += 1,
                'v' => robot_cordinate.y -= 1,
                '>' => robot_cordinate.x += 1,
                '<' => robot_cordinate.x -= 1,
                _ => continue,
            }
            *visited.entry(robot_cordinate.clone()).or_insert(0) += 1;
        }
    }
    println!("House visited: {}", visited.len());
}
