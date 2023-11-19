package AI.Genetic_NQueens;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



public class Genetics {
	public static double fitnessFunction(ArrayList<Integer> state) ////// evaluate number of pairwise threats
	{
		double eval = 0;
		for (int i = 0; i < state.size(); i++) {
			for (int j = i + 1; j < state.size(); j++) {
				if (state.get(i) == state.get(j) || Math.abs(state.get(i) - state.get(j)) == Math.abs(i - j))
					eval++;
			}
		}
		return 1 / eval;
	}

	public static ArrayList<ArrayList<Integer>> randomPopulation(int chromosomeSize, int populationSize) {
		ArrayList<Integer> sample = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> randomPopulation = new ArrayList<ArrayList<Integer>>(0);
		for (int i = 0; i < chromosomeSize; i++)
			sample.add(i);
		for (int i = 0; i < populationSize; i++) {
			Collections.shuffle(sample);
			sample = (ArrayList<Integer>) sample.clone();
			randomPopulation.add(sample);
		}
		return randomPopulation;
	}

	public static ArrayList<Integer> mutation(ArrayList<Integer> chromosome) {
		Random rand = new Random();
		int firstPoint = rand.nextInt(chromosome.size());
		int secondPoint = rand.nextInt(chromosome.size());
		Collections.swap(chromosome, firstPoint, secondPoint);
		return chromosome;
	}

	public static ArrayList<Integer> crossover(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		Random rand = new Random();
		int crossoverPoint = rand.nextInt(parent1.size() - 2) + 1;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < parent1.size(); i++)
			if (i < crossoverPoint)
				temp.add(parent1.get(i));
			else
				temp.add(parent2.get(i));
		return temp;

	}

	public static void crossover(ArrayList<ArrayList<Integer>> population, double propability) {
		Collections.shuffle(population);
		Random rand = new Random();
		double chance;
		int populationSize = population.size();
		for (int i = 0; i < populationSize; i++) {
			chance = rand.nextDouble();
			if (chance < propability) {
				population.add(crossover(population.get(i), population.get(i + 1)));
			}
		}
	}

	public static void mutation(ArrayList<ArrayList<Integer>> population, double propability) {
		Random rand = new Random();
		double chance;
		for (int i = 0; i < population.size() - 1; i++) {
			chance = rand.nextDouble();
			if (chance < propability) {
				population.set(i, mutation(population.get(i)));
			}
		}
	}

	public static ArrayList<ArrayList<Integer>> rouletteWheelSelection(ArrayList<ArrayList<Integer>> population,
			int n) {
		/// returns a population of size n by roulette wheel selection
		ArrayList<ArrayList<Integer>> newPopulation = new ArrayList<ArrayList<Integer>>();
		double totalFitness = 0;
		for (ArrayList<Integer> chromosome : population) {
			totalFitness = totalFitness + fitnessFunction(chromosome);
		}

		ArrayList<Double> relativeFitness = new ArrayList<Double>();
		for (ArrayList<Integer> chromosome : population) {
			relativeFitness.add(fitnessFunction(chromosome) / totalFitness);
		}
		ArrayList<Double> propablities = new ArrayList<Double>(relativeFitness);

		double sum = 0;
		for (double a : relativeFitness)
			sum = sum + a;
		for (int i = 1; i < relativeFitness.size(); i++) {
			propablities.set(i, propablities.get(i - 1) + relativeFitness.get(i));
		}
		int fittest = 0;
		for (int i = 0; i < propablities.size(); i++) {
			if (propablities.get(i) > propablities.get(fittest)) {
				fittest = i;
			}
		}
		return newPopulation;
	}

	public static ArrayList<ArrayList<Integer>> bestSelection(ArrayList<ArrayList<Integer>> population, int n) { ///// best
																													///// selection
																													///// using
																													///// optimized
																													///// bubble
																													///// sort
		boolean swapped = true;
		ArrayList<Double> fitness = new ArrayList<Double>();
		for (ArrayList<Integer> chromosome : population) {
			fitness.add(fitnessFunction(chromosome));
		}
		while (swapped) {
			swapped = false;
			for (int i = 0; i < population.size() - 1; i++) {
				if (fitness.get(i) < fitness.get(i + 1)) {
					Collections.swap(population, i, i + 1);
					Collections.swap(fitness, i, i + 1);
					swapped = true;
				}
			}
		}
		ArrayList<ArrayList<Integer>> newpop = new ArrayList<ArrayList<Integer>>(population.subList(0, n));
		return newpop;
	}

	public static void migration(ArrayList<ArrayList<ArrayList<Integer>>> populations, double probability, int flag,int number) {
		
		/*System.out.println("before all2");
		for(int k=0;k<populations.size();k++)
			System.out.println(populations.get(k).size());
		*/
		Random rand = new Random();
		
		double chance = 0;
		int counter = 0;
		if (flag == 1)
		{
			int destination = 0;
			for (int j=0;j<populations.size();j++) 
			{
				for (int i=0;i<populations.get(j).size();i++) 
				{
					chance = rand.nextDouble();
					if (chance < probability) {
						destination = rand.nextInt(populations.size() - 1);
						populations.get((destination+counter) % populations.size()).add(populations.get(j).get(i));
						populations.get(j).remove(i);
					}
				}
				counter++;
			}
		} 
		
		
		
		
		else if (flag == 2) 
		{
			int destination;
			ArrayList<Double> fitnessAverages = new ArrayList<Double>();
			for (ArrayList<ArrayList<Integer>> local : populations) 
			{
				double sum = 0;
				for (ArrayList<Integer> chromosome : local) 
				{
					sum = sum + fitnessFunction(chromosome);
				}
				fitnessAverages.add(sum/local.size());
			}
			boolean swapped = true;
			while (swapped)
			{
				swapped = false;
				for (int i = 0; i < fitnessAverages.size() - 1; i++) 
				{
					if (fitnessAverages.get(i) < fitnessAverages.get(i + 1)) 
					{
						Collections.swap(populations, i, i + 1);
						Collections.swap(fitnessAverages, i, i + 1);
						swapped = true;
					}
				}
			}
			for(ArrayList<ArrayList<Integer>> local:populations)
			{
				while (swapped)
				{
					swapped = false;
					for (int i = 0; i < local.size() - 1; i++) 
					{
						if (fitnessFunction(local.get(i)) < fitnessFunction(local.get(i + 1))) 
						{
							Collections.swap(local, i, i + 1);
							swapped = true;
						}
					}
				}
			}
			destination=0;
			counter=0;
			
			for (ArrayList<ArrayList<Integer>> local : populations) 
			{
				for (int i=number;i<local.size();i++)
				{
					boolean migrationCommitment=false;
					chance = rand.nextDouble();
					if (chance < probability) 
					{
						destination=counter+1;
						while( destination<populations.size())
						{
							if (fitnessFunction(local.get(i))>fitnessAverages.get(destination))
							{
								populations.get(destination).add(populations.get(counter).get(i));
								populations.get(counter).remove(i);

								migrationCommitment=true;
								break;
							}
							destination++;
						}
					}
					if(!migrationCommitment)
						break;
				}
				counter++;
				
			}
		}

		 else if (flag == 3) 
			{
				int destination;
				ArrayList<Double> fitnessAverages = new ArrayList<Double>();
				for (ArrayList<ArrayList<Integer>> local : populations) 
				{
					double sum = 0;
					for (ArrayList<Integer> chromosome : local) 
					{
						sum = sum + fitnessFunction(chromosome);
					}
					fitnessAverages.add(sum/local.size());
				}
				boolean swapped = true;
				while (swapped)
				{
					swapped = false;
					for (int i = 0; i < fitnessAverages.size() - 1; i++) 
					{
						if (fitnessAverages.get(i) > fitnessAverages.get(i + 1)) 
						{
							Collections.swap(populations, i, i + 1);
							Collections.swap(fitnessAverages, i, i + 1);
							swapped = true;
						}
					}
				}
				for(double p:fitnessAverages)
					System.out.print(p+" ");
				System.out.println();
				for(int j=0;j<populations.size();j++)
				{
					while (swapped)
					{
						swapped = false;
						for (int i = 0; i < populations.get(j).size() - 1; i++) 
						{
							if (fitnessFunction(populations.get(j).get(i)) < fitnessFunction(populations.get(j).get(i + 1))) 
							{
								Collections.swap(populations.get(j), i, i + 1);
								swapped = true;
							}
						}
					}
				}
				destination=0;
				counter=0;
				for (ArrayList<ArrayList<Integer>> local : populations) 
				{
					
					for (int i=0;i<number/2;i++)
					{
						boolean migrationCommitment=false;
						chance = rand.nextDouble();
						if (chance < probability) 
						{
							destination=populations.size()-1;
							while( destination>counter)
							{
								if ( fitnessFunction(local.get(i)) > fitnessFunction(populations.get(destination).get(100)) )
								{
									populations.get(destination).add(populations.get(counter).get(i));
									populations.get(counter).remove(i);
									migrationCommitment=true;
									break;
								}
								destination--;
							}
						}
						if(!migrationCommitment)
							break;
					}
					counter++;
				}
			}
	}
	public static void printPopualation(ArrayList<ArrayList<Integer>> population)
	{
		for(ArrayList<Integer> chromosome: population)
		{
			System.out.println(chromosome+" fitness= "+ 1/fitnessFunction(chromosome));
		}	
	}
	public static double average(ArrayList<ArrayList<Integer>> population)
	{
		double sum=0;
		for(ArrayList<Integer> chromosome: population)
		{
			sum=1/fitnessFunction(chromosome)+sum;
		}	
		return sum/population.size();
	}
	public static ArrayList<ArrayList<Integer>> Genetic(int chromosomeSize,int populationSize,int generationCount) throws IOException
	{
		ArrayList<ArrayList<Integer>> population=new ArrayList<ArrayList<Integer>>();
		BufferedWriter br = new BufferedWriter(new FileWriter("myfile.csv"));
		StringBuilder sb = new StringBuilder();
		population=randomPopulation(chromosomeSize, 5*populationSize);
    	for(int i=0;i<generationCount;i++)
    	{
    		sb.append(i);
		sb.append(',');
		sb.append(average(population));
    		population=Genetics.bestSelection(population,generationCount);
    		java.util.Collections.shuffle(population);
    		Genetics.crossover(population,1);
    		Genetics.mutation(population, 0.1);
    		sb.append("\n");
    	}
    	br.write(sb.toString());
		br.close();
    	return population;
	}
	
	public static ArrayList<ArrayList<Integer>> parallelGeneticInteractive(int chromosomeSize, int islandCount,int populationSize, int generationCount, int interval, int flag,double migrationProbabilty,double mutationChance) throws IOException {
		ArrayList<ArrayList<ArrayList<Integer>>> islands = new ArrayList<ArrayList<ArrayList<Integer>>>();
		BufferedWriter br = new BufferedWriter(new FileWriter("myfile.csv"));
		StringBuilder sb = new StringBuilder();
		ArrayList<ArrayList<Integer>> evaluatePopulation=new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < islandCount; i++)
			islands.add(randomPopulation(chromosomeSize, 5 * populationSize));
		for (int iterationCount = 0; iterationCount < generationCount; iterationCount++)
		{
			evaluatePopulation.clear();
			double average=0;
			sb.append(iterationCount);
			sb.append(',');
			for(int i=0;i<islandCount;i++)
				for(int j=0;j<populationSize;j++)
					evaluatePopulation.add(islands.get(i).get(j));
			evaluatePopulation=bestSelection(evaluatePopulation,populationSize)	;
		sb.append(average(evaluatePopulation));
			for (int i = 0; i < islands.size(); i++) 
			{
				if (iterationCount % interval == 0 && i==0)
					Genetics.migration(islands,migrationProbabilty, flag,populationSize );
				islands.set(i, Genetics.bestSelection(islands.get(i), populationSize));
				java.util.Collections.shuffle(islands.get(i));
				Genetics.crossover(islands.get(i), 2);
				Genetics.mutation(islands.get(i), mutationChance);
			}
			sb.append("\n");
		}
		br.write(sb.toString());
		br.close();
		
		
		ArrayList<ArrayList<Integer>> lastPopulation=new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<islandCount;i++)
			for(int j=0;j<populationSize;j++)
				lastPopulation.add(islands.get(i).get(j));
		lastPopulation=bestSelection(lastPopulation,populationSize)	;
		return lastPopulation;
		
	}
	public static ArrayList<ArrayList<Integer>> parallelGeneticIsolated(int chromosomeSize,int islandCount,int populationSize,int generationCount,double mutationChance) throws IOException
	{
		ArrayList<ArrayList<ArrayList<Integer>>> islands = new ArrayList<ArrayList<ArrayList<Integer>>>();
		BufferedWriter br = new BufferedWriter(new FileWriter("myfile.csv"));
		StringBuilder sb = new StringBuilder();
		ArrayList<ArrayList<Integer>> evaluatePopulation=new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < islandCount; i++)
			islands.add(randomPopulation(chromosomeSize, 5 * populationSize));
		for (int iterationCount = 0; iterationCount < generationCount; iterationCount++)
		{
			evaluatePopulation.clear();
			sb.append(iterationCount);
			sb.append(',');
		
				for(int i=0;i<islandCount;i++)
					for(int j=0;j<populationSize;j++)
						evaluatePopulation.add(islands.get(i).get(j));
				evaluatePopulation=bestSelection(evaluatePopulation,populationSize)	;
			sb.append(average(evaluatePopulation));
			for (int i = 0; i < islands.size(); i++) 
			{
				
				islands.set(i, Genetics.bestSelection(islands.get(i), populationSize));
				java.util.Collections.shuffle(islands.get(i));
				Genetics.crossover(islands.get(i), 2);
				Genetics.mutation(islands.get(i), mutationChance);
			}
			sb.append("\n");
		}
		br.write(sb.toString());
		br.close();
		
		
		ArrayList<ArrayList<Integer>> lastPopulation=new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<islandCount;i++)
			for(int j=0;j<populationSize;j++)
				lastPopulation.add(islands.get(i).get(j));
		lastPopulation=bestSelection(lastPopulation,populationSize)	;
		return lastPopulation;
	}
}
