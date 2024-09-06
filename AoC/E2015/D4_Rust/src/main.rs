use md5::{Digest, Md5};

fn calculate_md5(input: &str) -> String {
    let mut hasher = Md5::new();
    hasher.update(input);
    let result = hasher.finalize();
    format!("{:x}", result)
}

#[allow(dead_code, unused_variables)]
fn main() {
    let input = "ckczppom";
    let mut number: i32 = 1;

    loop {
        let result = input.to_owned() + &number.to_string();
        let hash = calculate_md5(&result);

        if hash.starts_with("000000") {
            break;
        }
        number += 1;
    }

    println!("Answer: {}", number);
}
