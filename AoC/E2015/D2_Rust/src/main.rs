#![allow(dead_code)]
#![allow(unused_variables)]

use core::str;
use std::fs;

struct Prism {
    length: i32,
    width: i32,
    height: i32,
}

impl Prism {
    fn new(length: i32, width: i32, height: i32) -> Prism {
        Prism{length, width, height}
    }

    fn area(&self) -> i32{
        let side1_area = self.length * self.width;
        let side2_area = self.height * self.width;
        let side3_area = self.height * self.length;

        let mut smallest: i32 = side1_area;
        if side2_area < smallest {
            smallest = side2_area;
        }
        if side3_area < smallest {
            smallest = side3_area;
        }
        smallest + 2 * (side1_area + side2_area + side3_area) 
    }

    fn ribbon_length(&self) -> i32 {
        // 2x3x4 ribbon = 2*(2+3)
        // row = 2x3x4 = 24 feet;
        let smallest = std::cmp::min(self.length, std::cmp::min(self.width, self.height));
        let lagerst= std::cmp::max(self.length, std::cmp::max(self.width,self.height));
        let middle = self.length + self.width + self.height - smallest - lagerst;

        let bow = self.length * self.width * self.height;

        2 * (smallest + middle) + bow
    }
}

fn parse_line(line: &str) -> Option<Prism> {
    // split the line by 'x', and turn it to collection (Vec)
    let part: Vec<&str> = line.split('x').collect();

    if part.len() != 3 {
        return None;
    }

    let length = part[0].parse().ok()?;
    let width= part[1].parse().ok()?;
    let height= part[2].parse().ok()?;

    Some(Prism::new(length, width, height))
}

fn main() {
    let input = fs::read_to_string("input.txt")
        .expect("error");

    let mut total_area = 0;
    let mut total_ribbon = 0;
    for (line_number, line) in input.lines().enumerate() {
        match parse_line(line) {
            Some(prism) => {
                let area = prism.area();
                let ribbon = prism.ribbon_length();
                println!("No.{} | {}x{}x{}, area: {}", 
                    line_number + 1, prism.length, prism.width, prism.height, area
                );
                total_area += area;
                total_ribbon += ribbon;
                println!("Total Area = {}", total_area);
                println!("Total Ribbon= {}", total_ribbon);
            }
            None => {
                println!("Error in line {}:{}", line_number + 1,line);
            }
        }
    }
}
