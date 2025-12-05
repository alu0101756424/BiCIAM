package local_search.complement;

import java.util.ArrayList;
import java.util.List;
import problem.definition.State;

public class TabuSolutions {
    
    public static List<State> listTabu = new ArrayList<>();
    public static int maxelements = 10;

    public TabuSolutions() {
    }

    public List<State> filterNeighbor(List<State> listNeighborhood) throws Exception {
        List<State> listFiltrate = new ArrayList<>();
        if (listTabu == null) {
            listTabu = new ArrayList<>();
        }
        
        for (State neighbor : listNeighborhood) {
            boolean isTabu = false;
            for (State tabuState : listTabu) {
                if (neighbor.Comparator(tabuState)) {
                    isTabu = true;
                    break;
                }
            }
            if (!isTabu) {
                listFiltrate.add(neighbor);
            }
        }
        return listFiltrate;
    }
}