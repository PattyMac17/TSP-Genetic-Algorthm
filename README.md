# TSP-Genetic-Algorthm
Java Genetic Algorithm to Solve Traveling Salesman Problem
Project Title: Using a Genetic Algorithm to Solve the Traveling Salesman Problem
Name: Patrick McLean
Date: 12/8/22

Accepts user input for grid size, number of cities, population size, generation cap, mutation rate, and convergence cutoff. User input is gathered via java.util.Scanner. Will catch any invalid inputs and continue to prompt user for correct input until satisfied. Uses a simple try catch for efficiency and my sanity

Features of the algorithm:

1) Random population initialization*
2) Evaluation of potential solution chromosomes
3) Evolution loop
	a) Selection: both roulette wheel selection and tournament selection
	b) Crossover: ordered crossover (in order to “visit” each city once and only once)
	c) Swap mutation: also meant to maintain one visit per city
4) Termination Condition: either reach a max number of generations of converge to a min value
5) Visual representation of optimal solution

*user prompt allows selection between a pre-programmed set of up to 10 cities or random generation of cities. In either case, cities used will be printed terminal. For more than 10 cities, program automatically generates random cities.

Note: If you choose not to execute the algorithm, it will skip the genetic algorithm and exit
