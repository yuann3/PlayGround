#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <openssl/md5.h>

#define MD5_DIGEST_LENGTH 16

void md5_hash(const char *input, char *output) {
    unsigned char digest[MD5_DIGEST_LENGTH];
    MD5_CTX context;

    MD5_Init(&context);
    MD5_Update(&context, input, strlen(input));
    MD5_Final(digest, &context);

    for (int i = 0; i < MD5_DIGEST_LENGTH; i++) {
        sprintf(&output[i * 2], "%02x", (unsigned int)digest[i]);
    }
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <string>\n", argv[0]);
        return 1;
    }

    char *input = argv[1];
    char output[33];  // 32 characters for MD5 hash + 1 for null terminator

    md5_hash(input, output);

    printf("Input: %s\n", input);
    printf("MD5 Hash: %s\n", output);

    return 0;
}

