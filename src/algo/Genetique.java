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
    
    private enum Option {
        RoueBiaiseeOption1,
        RoueBiaiseeOption2,
    }
    
    public class Generation{
        private List<Chromosome> individus = new ArrayList();
        private int fitnessTotale = 0;
        private Random rand = new Random();
        
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

        public void add(Chromosome individu) {
            individus.add(individu);
            fitnessTotale += individu.getFitness();
        }
        
        public void remove(Chromosome individu) {
            //int fitness = individu.getFitness();
            individus.remove(individu);
            fitnessTotale -= individu.getFitness();
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
                    
                    if (reverse)
                        return -compare;
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
            if (!Objects.equals(this.echiquier, other.echiquier)) {
                return false;
            }
            return true;
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

        int fitnessMin = -1;
        for (int i = 0; i < n; i++) {
            List<Integer> x = Echiquier.initialisationRandom(n);
            int fitnessX = Echiquier.fitness(x);
            Chromosome individu = new Chromosome(x, fitnessX);
            if (fitnessMin < 0 || fitnessX < fitnessMin) {
                fitnessMin = fitnessX;
                xMin = individu;
            }

            population.add(new Chromosome(x, fitnessX));

//            if (verbose) {
                System.out.println("Fitness solution " + i + " : " + fitnessX);
//            }
        }
        fmin = fitnessMin;
        
        System.out.println("Nb fitness totale: " + population.getFitnessTotale());
        System.out.println("Fitness min: " + fmin);
        System.out.println();
    }

    @Override
    public void run() {
        // Initialisation
        this.initialisation();
        
//        while (fmin != 0 && num < nmax) {
            // Reproduction
            Generation newPopulation = this.reproduction(population);
            // Croisement
            this.croisement(newPopulation);
            // Mutation
            
            num++;
//        }
        
        
        // 1. Génération de n solutions aléatoires : sans vérifier l'égalité
        // 2. Reproduction : 
        //      Calcul de la fitness de chaque solution
        //      Attribuer à chaque solution un pourcentage : (somme des fitness - fitness)/somme des fitness totale
        //      Sélection des solutions selon une roulette aléatoire lancé "option" fois selon
        

        
        
    }
    
    private Generation reproduction(Generation population) {
        Generation generation = new Generation();
        Random rand = new Random();
        
        // Tri
        population.sort(true);
        
        // TODO use option
        int noption;
        switch (optionReproduction) {
            case RoueBiaiseeOption1 :
                noption = 1;
                break;
            case RoueBiaiseeOption2 :
                noption = population.size();
                break;
        }

        
        double maxRange = 1.;
        for (int i = 0; i < population.size(); i++) {
            
            double random = rand.nextDouble();
            int indiceRetenu = 0;
            System.out.println("Random = "+ random * population.getFitnessTotale());

            for (Chromosome chromosome : population.getIndividus()) {
                double minRange = maxRange - population.getProportion(chromosome);
                
                if (verbose) {
                    System.out.println("Max " + maxRange);
                    System.out.println("Min " + minRange);
                    System.out.println("Fitness chromosome " + indiceRetenu + " : " + chromosome.getFitness());
                    System.out.println("Proportion chromosome " + indiceRetenu + " : " + population.getProportion(chromosome));
                }

                if (random >= minRange && random < maxRange) {
                    generation.add(chromosome);
                    System.out.println("Fitness chromosome retenu : " + chromosome.getFitness());
                    break;
                }

                maxRange = minRange;
                indiceRetenu++;
            }
            
            if (verbose) {
                System.out.println("Chromosome retenu : " + indiceRetenu);
            }
        }
        
        return generation;
    }
    
    private Generation croisement(Generation population) {
        
        Generation generation = new Generation();
        Chromosome c1, c2;
        for (int i = 0; i < population.size(); i = i + 2) {
            
            // Aléatoire
//            c1 = population.getRandomIndividu();
//            c2 = population.getRandomIndividu();
            
            // 2 à 2
            c1 = population.getIndividus().get(i);
            c2 = population.getIndividus().get(i+1);
            
            croisement(generation, c1, c2);
        }
        
        
        return generation;
    }
    
    private void mutation(Generation population) {
        
    }
    
    /**
     * Croisement en 1 point
     * 
     * - Croisement en plusieurs points à faire par récursivité !
     * @param chromosome1
     * @param chromosome2 
     */
    private void croisement(Generation generation, Chromosome chromosome1, Chromosome chromosome2) {

        System.out.println("Croisement");
        System.out.println("affichage echiquiers avant croisements");
        System.out.println("echiquie1");
        Echiquier.afficherEchiquier(chromosome1.getEchiquier());
        System.out.println("echiquier2");
        Echiquier.afficherEchiquier(chromosome2.getEchiquier());
        
        if (chromosome1 == chromosome2) { // TODO clone si croisement aléatoire (!= 2 à 2)
            System.out.println("Chromosomes identiques");
            generation.add(chromosome1);
            generation.add(chromosome2);
            return;
        }
        
        // 1 croisement
        Random rand = new Random();
        int size = chromosome1.getEchiquier().size();
        int indexCroisement = rand.nextInt(size);
        System.out.println("croisement en " + indexCroisement);

        // Echantillon
        List<Integer> echiquier1 = new ArrayList(chromosome1.getEchiquier());
        List<Integer> echiquier2 = new ArrayList(chromosome2.getEchiquier());
        List<Integer> croisement1 = new ArrayList(echiquier1.subList(indexCroisement, size - 1));
        List<Integer> croisement2 = new ArrayList(echiquier2.subList(indexCroisement, size - 1));
        
        // Croisement
        echiquier1.subList(indexCroisement, size - 1).clear();
        echiquier2.subList(indexCroisement, size - 1).clear();
        echiquier1.addAll(croisement2);
        echiquier2.addAll(croisement1);

        
        System.out.println("affichage echiquiers après croisements");
        System.out.println("echiquier1");
        Echiquier.afficherEchiquier(echiquier1);
        System.out.println("echiquier2");
        Echiquier.afficherEchiquier(echiquier2);
        System.out.println();
        
        System.out.println("affichage chromosomes après croisements");
        Echiquier.afficherEchiquier(chromosome1.getEchiquier());
        Echiquier.afficherEchiquier(chromosome2.getEchiquier());
        
        generation.add(new Chromosome(echiquier1, Echiquier.fitness(echiquier1)));
        generation.add(new Chromosome(echiquier2, Echiquier.fitness(echiquier2)));
    }
}

