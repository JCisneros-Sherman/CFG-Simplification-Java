# CFG-Simplification-Java

Simplification of Context-Free Grammars
This program reads a CFG from a txt file, simplify it by removing ε-rules and useless rules,
and print out the simplified equivalent CFG. 

For example, given the following CFG in a txt file (0 denotes empty string and “-” denotes arrow head “→”):

S-aA|aBB

A-aaA|0

B-bB|bbC

C-B

After processing, your program should print out the following simplified equivalent CFG:

S-aA|a

A-aaA|aa
