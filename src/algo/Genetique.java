package algo;

import algo.util.Echiquier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */
public class Genetique extends Optimisation {
    
    private Generation population = new Generation();
    private Chromosome xMin;
    private Option optionReproduction = Option.RoueBiaiseeOption1;
    private double probaMutation = 0.2;
    private Random rand = new Random();
    
    private enum Option {
        RoueBiaiseeOption1,
        RoueBiaiseeOption2,
    }
    
    public class Generation{
        private List<Chromosome> individus = new ArrayList();
        private int fitnessTotale = 0;
        private Random rand = new Random();
        private int fmin = -1; // fitness min
        private Chromosome xmin; // xmin
        
        public Generation() {}
        
        public Generation(List<Chromosome> chromosomes) {
            individus = chromosomes;
        }

        public List<Chromosome> getIndividus() {
            return individus;
        }

        public int getFitnessTotale() {
            return fitnessTotale;
        }

        public int getFmin() {
            return fmin;
        }

        public Chromosome getXmin() {
            return xmin;
        }

        public void add(Chromosome individu) {
            individus.add(individu);
            fitnessTotale += individu.getFitness();
            setMin(individu);
        }
        
        private void setMin(Chromosome individu) {
            if (fmin < 0 || individu.getFitness() < fmin) {
                fmin = individu.getFitness();
                xmin = individu;
            }
        }

        public int size() {
            return individus.size();
        }
        
        public Chromosome getRandomIndividu() {
            return individus.get(rand.nextInt(size()));
        }
        
        public Double getProportion(Chromosome individu) {
            if (individu.getFitness() == 0) {
                return 1.;
            }
            //return (Double.valueOf(fitnessTotale) / Double.valueOf(individu.getFitness())) / Double.valueOf(fitnessTotale);
            return Double.valueOf((fitnessTotale - individu.getFitness())) / Double.valueOf(fitnessTotale);
        }

        public void sort() {
            this.sort(false);
        }
        
        public void sort(boolean reverse) {
            Collections.sort(individus, new Comparator<Chromosome>() {
                @Override
                public int compare(Chromosome chromosome1, Chromosome chromosome2) {
                    int compare = getProportion(chromosome1).compareTo(getProportion(chromosome2)); // Ordre decroissant de proportion : -

                    if (reverse) {
                        return -compare;
                    }

                    return compare;
                }
            });
        }
    }

    public class Chromosome {
        private final List<Integer> echiquier;
        private final int fitness;

        public Chromosome(List<Integer> echiquier, int fitness) {
            this.echiquier = echiquier;
            this.fitness = fitness;
        }

        public List<Integer> getEchiquier() {
            return echiquier;
        }

        public Integer getFitness() {
            return fitness;
        }
        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 79 * hash + Objects.hashCode(this.echiquier);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Chromosome other = (Chromosome) obj;
            if (this.fitness != other.fitness) {
                return false;
            }
            return Objects.equals(this.echiquier, other.echiquier);
        }
    }

    public Genetique(int n, int nmax) {
        super(n, nmax);
    }

    @Override
    protected void initialisation() {
        System.out.println("Stratégie d'initialisation: Random\n");
        
        if (verbose) {
            System.out.println("Population initiale");
        }

        for (int i = 0; i < n; i++) {
            List<Integer> x = Echiquier.initialisationRandom(n);
            int fitnessX = Echiquier.fitness(x);

            population.add(new Chromosome(x, fitnessX));

            if (verbose) {
                System.out.println("Fitness solution " + i + " : " + fitnessX);
            }
        }
        fmin = population.getFmin();
        xmin = population.getXmin().getEchiquier();
        
        System.out.println("Nb fitness totale: " + population.getFitnessTotale());
        System.out.println("Fitness min: " + fmin);
        System.out.println();
    }

    @Override
    public void run() {
        // Initialisation
        this.initialisation();
        
        while (fmin != 0 && num < 3) { // nmax
            if (verbose) {
                System.out.println("Itération " + num);
                System.out.println();
            }

            // Reproduction
            Generation newPopulation = this.reproduction(population);
            // Croisement
            newPopulation = this.croisement(newPopulation, false); // TODO attributs random
            // Mutation
            newPopulation = this.mutation(newPopulation);
            
            // Affectations
            population = newPopulation;
            if (population.getFmin() < fmin) {
                fmin = population.getFmin();
                xmin = population.getXmin().getEchiquier();
            }

            // Affichage itération
            if (verbose) {
                System.out.println("Generation de taille " + population.size());
                System.out.println("Nb fitness totale: " + population.getFitnessTotale());
                System.out.println("Fitness min: " + fmin);
                System.out.println();
            }
            
            num++;
        }
        
        if (verbose) {
            System.out.println("Solution finale\n");
            Echiquier.afficherEchiquier(xmin);
            System.out.println("Fitness finale : " + fmin);
        }
            
//        }
    }
    
    private double getRandom() {
        Random random = new Random(rand.nextLong());
        return Math.abs(random.nextGaussian());
    }
    
    private Generation reproduction(Generation population) {
        System.out.println("Reproduction\n");
        Generation generation = new Generation();
        
        // Tri
        //population.sort(true);
        population.sort(true);

        int fitnessTotale = population.getFitnessTotale();
        for (int i = 0; i < population.size(); i++) {
            int indiceRetenu = 0;
            //double maxRange = 1.;
            double minRange = 0.;
            double random = getRandom();
            System.out.println("Random = " + random * fitnessTotale);

            for (Chromosome chromosome : population.getIndividus()) {
                //double minRange = maxRange - population.getProportion(chromosome);
                double maxRange = minRange + population.getProportion(chromosome);
                
                if (verbose) {
//                    System.out.println("Max " + maxRange);
//                    System.out.println("Min " + minRange);
                    System.out.println("Range " + indiceRetenu + " : " + minRange * fitnessTotale + " - " + maxRange * fitnessTotale);
                    System.out.println("Fitness chromosome " + indiceRetenu + " : " + chromosome.getFitness());
                    System.out.println("Proportion chromosome " + indiceRetenu + " : " + population.getProportion(chromosome) * fitnessTotale);
                }

                //if (random > minRange && random <= maxRange) {
                if (random >= minRange && random < maxRange) {
                    generation.add(chromosome);
                    //System.out.println("Fitness chromosome retenu : " + chromosome.getFitness());
                    System.out.println("Chromosome retenu : " + indiceRetenu);
                    break;
                }

                //maxRange = minRange;
                minRange = maxRange;
                indiceRetenu++;
            }
            System.out.println();
        }

        return generation;
    }
    
    private Generation croisement(Generation population, boolean random) {
        System.out.println("Croisement\n");
        Generation generation = new Generation();

        for (int i = 0; i < population.size() - 1; i = i + 2) {
            if (verbose) {
                System.out.println("Croisement des individus " + i + " et " + (i+1));
            }

            Chromosome c1, c2;
            if (random) {
                // Aléatoire
                c1 = population.getRandomIndividu();
                c2 = population.getRandomIndividu();
            } else {
                // 2 à 2
                c1 = population.getIndividus().get(i);
                c2 = population.getIndividus().get(i+1);
            }

            croisement(generation, c1, c2);
        }
        
        return generation;
    }
    
    private Generation mutation(Generation population) {
        System.out.println("Mutation\n");
        Generation generation = new Generation();
        
        int i = 0;
        for (Chromosome individu : population.getIndividus()) {
            System.out.println("Mutation chromosome " + i + " de fitness " + individu.getFitness());
            generation.add(mutation(individu));
            i++;
        }

        return generation;
    }
    
    private Chromosome mutation(Chromosome individu) {
        List<Integer> echiquier = new ArrayList(individu.getEchiquier());
        
        Random rand = new Random();
        for (int i = 0; i < echiquier.size(); i++) {
            if (rand.nextDouble() < probaMutation) { // mutation
                int ligne = rand.nextInt(echiquier.size());
                
                if (ligne != i) { // échange de i et ligne
                    if (verbose) {
                        System.out.println("Mutation de " + i + " et " + ligne);
                    }

                    echiquier.set(i, individu.getEchiquier().get(ligne));
                    echiquier.set(ligne, individu.getEchiquier().get(i));
                }
            } else {
                System.out.println("Pas de mutation");
            }
        }
        
        int nouvelleFitness = Echiquier.fitness(echiquier);
        if (verbose) {
            System.out.println("Nouvelle fitness " + nouvelleFitness);
            System.out.println();
        }

        return new Chromosome(echiquier, nouvelleFitness);
    }
    
    /**
     * Croisement en 1 point
     * 
     * - Croisement en plusieurs points à faire par récursivité !
     * @param chromosome1
     * @param chromosome2 
     */
    private void croisement(Generation generation, Chromosome chromosome1, Chromosome chromosome2) {

        if (chromosome1 == chromosome2) { // TODO clone si croisement aléatoire (!= 2 à 2)
            if (verbose) {
                System.out.println("Chromosomes identiques\n");
            }

            generation.add(chromosome1);
            generation.add(chromosome2);
            return;
        }

        if (verbose) {
            System.out.println("Affichage echiquiers avant croisements");
            System.out.println("Echiquie 1");
            Echiquier.afficherEchiquier(chromosome1.getEchiquier());
            System.out.println("Echiquier 2");
            Echiquier.afficherEchiquier(chromosome2.getEchiquier());
        }

        // 1 croisement
        int indexCroisement = rand.nextInt(n);
        if (verbose) {
            System.out.println("Croisement en " + indexCroisement);
        }

        // Echantillons
        List<Integer> echiquier1 = new ArrayList(chromosome1.getEchiquier());
        List<Integer> echiquier2 = new ArrayList(chromosome2.getEchiquier());
        
        // Sous-échantillons
        List<Integer> croisement1 = new ArrayList(echiquier1.subList(indexCroisement, n));
        List<Integer> croisement2 = new ArrayList(echiquier2.subList(indexCroisement, n));
        
        // Croisement
        echiquier1.subList(indexCroisement, n).clear();
        echiquier2.subList(indexCroisement, n).clear();
        echiquier1.addAll(croisement2);
        echiquier2.addAll(croisement1);
        
        int nouvelleFintess1 = Echiquier.fitness(echiquier1);
        int nouvelleFintess2 = Echiquier.fitness(echiquier2);

        // Affichage
        if (verbose) {
            System.out.println("Affichage echiquiers après croisements");
            System.out.println("Echiquier 1");
            Echiquier.afficherEchiquier(echiquier1);
            System.out.println("Echiquier 2");
            Echiquier.afficherEchiquier(echiquier2);
            
            System.out.println("Nouvelle fitness echiquier 1 : " + nouvelleFintess1);
            System.out.println("Nouvelle fitness echiquier 2 : " + nouvelleFintess2);
            System.out.println();
        }

        // Ajout des chromosomes générés à la population
        generation.add(new Chromosome(echiquier1, nouvelleFintess1));
        generation.add(new Chromosome(echiquier2, nouvelleFintess2));
    }
}

