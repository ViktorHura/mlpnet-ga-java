import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class Trainer {
int popsize;
int elite;
int cutoff;
int tournamentsize;
double mutationrate;
int inputs;
int hiddens;
int outputs;
Organism[] population;

public Trainer(Network net, int pops, int el, int cutf, int tournsize, double mutrate){
        population = new Organism[pops];
        popsize = pops;
        elite = el;
        cutoff = cutf;
        tournamentsize = tournsize;
        mutationrate = mutrate;

        inputs = net.inputs;
        hiddens = net.hiddens;
        outputs = net.outputs;
}

public void Train(Network net, int maxgen, boolean reset){
        if (reset == true) {
                this.CreateInitialPopulation();
        }

        for (int generation = 0; generation < maxgen; generation++) {



                for (int i = 0; i < popsize; i++) {
                        population[i].CalcFitness(net);
                }

                System.out.println("Gen: "+generation+  " Bfit: "+ population[0].fitness);
                this.SortPopulation();





                this.NaturalSelection();






        }

        this.SortPopulation();
        net.inputWeights = population[0].inputWeights;
        net.hiddenWeights = population[0].hiddenWeights;

}





public void CreateInitialPopulation(){
        for (int i = 0; i < popsize; i++) {
                Organism o = new Organism(inputs, hiddens, outputs);
                population[i] = o;
        }
}

public void NaturalSelection(){
        Organism[] nextPopulation = new Organism[popsize];


        for (int i =0; i< popsize; i++) {
                if (i < elite) {
                        nextPopulation[i] = population[i];
                        continue;
                }else{
                        Organism a = this.TournamentSelection();
                        Organism b = this.TournamentSelection();

                        Organism child = a.Crossover(b);
                        child.Mutate(mutationrate);

                        child.fitness = 0.0;

                        nextPopulation[i] = child;

                }

        }

        population = nextPopulation;



}

public void SortPopulation(){
        Arrays.sort(population, new Comparator<Organism>() {
                        public int compare(Organism o1, Organism o2) {
                                return Double.valueOf(o2.fitness).compareTo(Double.valueOf(o1.fitness)); //sort highest fitness
                        }
                });

}

public Organism TournamentSelection(){

        Organism[] tournament = new Organism[tournamentsize];

        for (int n = 0; n < tournamentsize; n++) {
                int i = (int)(Math.random() * (popsize - cutoff));
                tournament[n] = population[i];

        }

        int bestint = 0;
        double bestfit = tournament[0].fitness;

        for (int n = 0; n < tournamentsize; n++) {
                if (tournament[n].fitness > bestfit) {
                        bestint = n;
                        bestfit = tournament[n].fitness;
                }
        }

        return tournament[bestint];

}


}
