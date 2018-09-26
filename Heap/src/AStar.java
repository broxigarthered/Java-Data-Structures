import java.util.*;

public class AStar {

    private char[][] map;

    public AStar(char[][] map) {
        this.map = map;
    }

    // Heuristic function, calculating the distance between the current cell and the goal, moving horizontally and
    // vertically ONLY. Abs(a.x - b.x + a.y - b.y)
    public static int getH(Node current, Node goal) {
        int deltaX = Math.abs(current.getCol() - goal.getCol());
        int deltaY = Math.abs(current.getRow() - goal.getRow());

        return deltaX + deltaY;
    }

    public Iterable<Node> getPath(Node start, Node goal) {
        PriorityQueue<Node> open = new PriorityQueue<>();
        open.enqueue(start);
        Map<Node, Node> parent = new LinkedHashMap<>();
        Map<Node, Integer> cost = new LinkedHashMap<>();

        parent.put(start, null);
        cost.put(start, 0);

        /*
        •	while OPEN is not empty:
o	current = remove highest priority item from OPEN
o	if current is the goal  break
o	for each neighbor of current (up, right, down, left):
♣	new cost = COST[current] + 1
♣	if neighbor is not in COST or new cost < COST[neighbor]
•	COST[neighbor] = new cost
•	neighbor.F = new cost + HCost(neighbor, goal)
•	OPEN  neighbor
•	PARENT[neighbor] = current

         */

        while (open.size() != 0){
            Node current = open.dequeue();
            if(current == goal)break;


            // put neighbours
            // check if they are legit
            // FCost = GCost + HCost
//            GCost – the distance from current to the start
//            HCost – the distance from current to the destination

            for (Node neighbour : this.getNeighbours(current)) {
                int newGCost = cost.get(current)+ 1;

                if(!cost.containsKey(neighbour) || newGCost < cost.get(neighbour)){
                    cost.put(neighbour, newGCost);
                    neighbour.setF(newGCost + getH(neighbour, goal));
                    open.enqueue(neighbour);
                    parent.put(neighbour, current);
                }
            }
        }

        // get the path from the dictionary
        ArrayDeque<Node> result = new ArrayDeque<Node>();
        if(!parent.containsKey(goal)){
            result.push(start);
        } else {
            result.push(goal);
        }

        return result;
    }

    private List<Node> getNeighbours(Node node){
        List<Node> neighbours = new ArrayList<>();
        Node upNode = getUpNode(node);
        if(upNode != null) neighbours.add(upNode);
        Node rightNode = getRightNode(node);
        if(rightNode != null) neighbours.add(rightNode);
        Node downNode = getDownNode(node);
        if(downNode != null) neighbours.add(downNode);
        Node leftNode = getLeftNode(node);
        if(leftNode != null)neighbours.add(leftNode);

        return neighbours;
    }

    private Node getLeftNode(Node node) {
        if(node.getCol() - 1 >= 0) return new Node(node.getRow(), node.getCol() - 1);
        return null;
    }

    private Node getDownNode(Node node) {
        if(node.getRow() + 1 < this.map.length) return new Node(node.getRow() + 1, node.getCol());
        return null;
    }

    private Node getRightNode(Node node) {
        if(node.getCol() + 1 < this.map.length) return new Node(node.getRow(), node.getCol() + 1);
        return null;
    }

    private Node getUpNode(Node node) {
        if(node.getRow()-1 >= 0) return new Node(node.getRow()-1, node.getCol());
        return null;
    }
}
