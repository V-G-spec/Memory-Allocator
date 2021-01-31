# Memory-Allocator

## About

Java implementation of a Dynamic Memory Allocation system. In simple terms, memory allocation is the reservation of portions of the Computer memory for execution of processes. Thus, this system will be running on our machines and it will give out memories to the programs as requested by them.

The system maintains two Data structures (Linked List for a first-fit allocation and BS Tree/AVL Tree for a best-fit allocation). One is to represent the allocated memory while the other is to denote the memory that is free. The former uses ```Key``` to store the starting address of the memory block while the latter uses it to store the size of memory block.


## Using makefile

```make all```

To compile all .java files

```make clean```

To remove the generated .class files

## Using run.sh

```run.sh {input_file} {output_file}```

Example:
```run.sh test.in res.out```

Both arguments are optional, inputfile is the file containing the test cases and output file is where you want the result to be written into.
In the case any argument is missing, console is used for input or output.

## Running

```Allocate```: Allocates memory of a certain size
```Free```: Frees up memory block starting at a particular address (Denoted by an integer)
```Defragment```: Checks for free blocks that are next to each other and combines them into larger free blocks.

## Format of input

```
number of test cases

size

number of commands

command1

command2

...

size (next test case)

number of commands

command1

command2

...
```

## Format of commands

```Allocate Size```

```Free Address```
```Defragment 0```

## Author

Vansh Gupta: https://github.com/V-G-spec

## License

Copyright -2020 - Indian Institute of Technology, Delhi
Part of course COL106: Data Structures & Algorithms (Taught by Rahul Garg and Rijurekha Sen)
