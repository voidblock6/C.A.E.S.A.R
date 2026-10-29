# C.E.A.S.A.R.

## About
C.E.A.S.A.R. is an encryption editor/sandbox. Choose how you want to encrypt your message, and the program generates a key, config, and output.
You can choose how it is encrypted, the length of your key, the amount of rounds done for each encryption, and more things.

## Config and key
The program creates a config when encrypting. The config shows the steps done to encrypt your message. Here are a few examples:

- `C001`: Caesar cipher, shifted by 1 (Caesar shift is done with all characters from 1–255)
- `X`: XOR Encryption
- `V`: Vigenère Cipher


When decrypting, you will be able to input this config, and the program will know how it was encrypted. This config is safe to share, since decrypting without a key is near impossible.

The key can be between 8 and 64 characters long and can consist of the following characters:
`ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()`~_-=+[{]}/?.>,<`

> **NOTE:** It recommend to convert  the output to base64 as a last step when ecnrypting. This is because not all possible byte values are copyable charachters.

## Progress
This project is very much still a work in progress, some things may be broken or work bad at times. Please give feedback for anything that could be improved!

**Currently added content includes:**
* XOR Encryption
* Vigenère Cipher (byte)
* Caesar Cipher (byte)
* Atbash Chiper
* Base64 encoding
* Config and key generation
* Encryption
* Ddecryption

**Planned and WIP features**
* Salts
* Copy and paste buttons for input and output fields
* Affine Chiper
* Presets (Rot13, etc)
* Vignere Chiper (alphabetical)
* Ceasar Chiper (alphabetical)
* Rail fence Chiper
* Morse code
* App themes (light/dark mode, colors)
* Base2, Base3, Base4, Base8, Base10, Base16, Base32
* Custom encryption order
* Improve UI



## Questions and suggestions
Feel free to suggest new features or ask questions in my Discord: https://discord.gg/7Cjdrp2FDX

Note: A Windows executable (.exe) and a runnable .jar will be released once more features are complete. If you want to test the program right now, run the project directly inside an IDE (e.g., IntelliJ IDEA).




