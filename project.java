#include <iostream>
#include <fstream>
#include <sstream>
#include <iomanip>
#include <openssl/sha.h>

using namespace std;

string toHexString(unsigned char* hash) {
    stringstream ss;
    for (int i = 0; i < SHA256_DIGEST_LENGTH; i++) {
        ss << hex << setw(2) << setfill('0') << (int)hash[i];
    }
    return ss.str();
}

void computeSHA256(const string& filePath) {
    ifstream file(filePath, ios::binary);
    if (!file) {
        cerr << "Error opening file!" << endl;
        return;
    }

    SHA256_CTX sha256Context;
    SHA256_Init(&sha256Context);

    char buffer[1024];
    while (file.read(buffer, sizeof(buffer))) {
        SHA256_Update(&sha256Context, buffer, file.gcount());
    }
    SHA256_Update(&sha256Context, buffer, file.gcount());
    
    unsigned char hash[SHA256_DIGEST_LENGTH];
    SHA256_Final(hash, &sha256Context);

    cout << "SHA-256 hash of the file: " << toHexString(hash) << endl;
}

int main() {
    string filePath = "C:\\Users\\pavan\\OneDrive\\Documents\\mark.txt";  // Provide your file path here
    computeSHA256(filePath);
    return 0;
}
