package problem.extension;

import java.util.ArrayList;
import java.util.List;
import problem.definition.State;

public class MetricasMultiobjetivo {

    public double CalcularMedia(ArrayList<Double> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Double val : values) {
            sum += val;
        }
        return sum / values.size();
    }

    public double tasaError(List<State> current, List<State> truePareto) {
        if (current == null || current.isEmpty()) {
            return 0.0;
        }
        int errorCount = 0;
        for (State s : current) {
            boolean found = false;
            if (truePareto.contains(s)) {
                found = true;
            }
            if (!found) {
                errorCount++;
            }
        }
        return (double) errorCount / current.size();
    }

    public double dispersion(List<State> solutions) {
        if (solutions == null || solutions.size() < 2) {
            return 0.0;
        }
        
        double sum = 0;
        int count = 0;
        for (State s : solutions) {
            if (s.getEvaluation() != null && !s.getEvaluation().isEmpty()) {
                sum += s.getEvaluation().get(0);
                count++;
            }
        }
        if (count == 0) return 0.0;
        double mean = sum / count;
        
        double sumSqDiff = 0;
        for (State s : solutions) {
            if (s.getEvaluation() != null && !s.getEvaluation().isEmpty()) {
               double diff = s.getEvaluation().get(0) - mean;
               sumSqDiff += diff * diff;
            }
        }
        
        return sumSqDiff / count;
    }
}
