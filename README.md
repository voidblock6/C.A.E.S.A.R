# C.E.A.S.A.R.

## About
C.E.A.S.A.R. is an encryption editor/sandbox. Choose how you want to encrypt your message, and the program generates a key, config, and output.

You have full control over how it is encrypted, the length of your key, the amount of rounds done for each encryption, and the specific shift for the Caesar cipher.

## Config and key
The program creates a config when encrypting. The config shows the steps done to encrypt your message.

- `C001`: Caesar cipher, shifted by 1 (Caesar shift is done with all characters from 1–255)
- `X`: XOR Encryption
- `V`: Vigenère Cipher

When decrypting, you will be able to input this config, and the program will know how it was encrypted. This config is safe to share, since decrypting without a key is near impossible.

The key can be between 8 and 64 characters long and can consist of the following characters:
`ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()`~_-=+[{]}/?.>,<`

> **NOTE:** At the end of encryption, the output is converted to Base64. This is because not all characters between 1 and 255 are visible or able to be copy and pasted.

## Progress
This project is very much still a work in progress.

**Currently added content includes:**
* XOR Encryption
* Vigenère Cipher
* Caesar Cipher
* Encryption
* Config and key generation

**Work in progress and planned features:**
* 
* Presets
* Salts
* Morse code encryption/decryption
* Binary encryption

## Questions and suggestions
Feel free to suggest new features or ask questions in my Discord: https://discord.gg/7Cjdrp2FDX

Note: A Windows executable (.exe) and a runnable .jar will be released once more features are complete. If you want to test the program right now, run the project directly inside an IDE (e.g., IntelliJ IDEA).




