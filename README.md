# NQueen
The N-queens problem is a classic combinatorial optimization problem that involves placing n queens on an n × n chessboard so that no two queens attack each other. Two queens are said to attack each other if they share the same row, column, or diagonal. The problem has been studied for centuries and is considered to be a benchmark for testing new optimization algorithms.
Algorithm
The genetic algorithm implemented in this project uses the following steps:
  1:  Generate an initial population of chromosomes. A chromosome is a representation of a solution to the N-queens problem. In this case, a chromosome is a list of n integers, where each integer represents the row on which a queen is placed.
  2:  Evaluate the fitness of each chromosome. The fitness of a chromosome is a measure of how good the solution represented by the chromosome is. In this case, the fitness of a chromosome is the number of pairs of queens that are attacking each other.
  3:  Select a subset of chromosomes to be parents. The parents are the chromosomes that will be used to create the next generation of chromosomes. The chromosomes with the highest fitness are more likely to be selected as parents.
  4:  Create the next generation of chromosomes. The next generation of chromosomes is created by crossing over the parents and then mutating the offspring. Cross-over is a process of exchanging genes between two chromosomes. Mutation is a process of randomly changing a gene in a chromosome.
  5:  Repeat steps 2-4 until a solution is found or a maximum number of generations is reached.
multiple migration operators are used and the result of it are documented.
