# Virtual memory algorithms comparison
## Description
 Given a sequence of memory requests, compare different memory handling algorithms (First In First Out, Least Recently Used, Optimal) and output their results and total number of memory replacements
## Usage: 
    ./gradlew runJar -Pargv="[INPUT FILE] [OPTIONS...]"

#### Options
    -s // Only output the number of memory replacements

#### Input file template
    [Number of pages 1] [RAM size 1]
    [Memory requests sequence 1]
    [Number of pages 2] [RAM size 2]
    [Memory requests sequence 2]
    ...
All entries must not end with extra spaces, file must not end with newline

#### Output format
Result sequence for each algorithm consists of [Number of pages] integers, -1 if no replacement was made on that request, or X if page X was replaced

Output for each entry (input sequence) is
    
    [Result sequence FIFO]
    [Result sequence LRU]
    [Result sequence Opt]
    [Replacements FIFO] [Replacements LRU] [Replacements OPT]

If running with -s flag:

    [Replacements FIFO] [Replacements LRU] [Replacements OPT]

#### Example running
From root directory run

    ./gradlew runJar -Pargv="./data/tinyexample_1.in"



More example files of different sizes can be found in /data directory. For bigger examples running with -s is strongly recommended

    tinyexample_1.in // Sequence length up to 50
    tinyexample_2.in
    tinyexample_3.in
    smallexample_1.in // Sequence length up to 1000
    ...
    smallexample_5.in
    example_1.in // Sequence length up to 5*10^5
    ...
    example_10.in
    bigexample_1.in // Sequence length of 10^7
    bigexample_2.in // Can take over 30 seconds to compute

## Testing:
    ./gradlew test
Runs unit tests