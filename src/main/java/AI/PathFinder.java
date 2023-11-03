package AI;

import Main.GamePanel;
import entity.Entity;


import java.util.ArrayList;

public class PathFinder {
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }

    public void instantiateNodes() {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row] = new Node(col, row);
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNode() {
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }

        }
        //restart other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNode(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
        resetNode();
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            int tileNum = gp.tileM.mapTileNumber[gp.currnetMap][col][row];
            if (gp.tileM.tile[tileNum].collision == true) {
                node[col][row].solid = true;
            }
            for (int i = 0; i < gp.iTile[1].length; i++) {
                if (gp.iTile[gp.currnetMap][i] != null && gp.iTile[gp.currnetMap][i].destructible == true) {
                    int itCol = gp.iTile[gp.currnetMap][i].worldX / gp.tileSize;
                    int itRow = gp.iTile[gp.currnetMap][i].worldY / gp.tileSize;
                    node[itCol][itRow].solid = true;
                }
            }
            getCost(node[col][row]);

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }

        }

    }

    public void getCost(Node node) {
        //G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        //H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        //F cost
        node.fCost = node.gCost + node.hCost;

    }
    public boolean search(){
        while(goalReached==false&&step<500){
            int col= currentNode.col;
            int row= currentNode.row;
            //checke current node
            currentNode.checked=true;
            openList.remove(currentNode);
            //open the up node
            if(row-1>=0){
                openNode(node[col][row-1]);
            }
            //open the left node
            if(col-1>=0){
                openNode(node[col-1][row]);
            }
            //open the down node
            if(row+1<gp.maxWorldRow){
                openNode(node[col][row+1]);
            }
            //open the right node
            if(col+1<gp.maxWorldCol){
                openNode(node[col+1][row]);
            }
            //find the best one
            int bestNodeIndex=0;
            int bestNodeCost=999;
            for(int i=0;i<openList.size();i++){
                if(openList.get(i).fCost<bestNodeCost){
                    bestNodeIndex=i;
                    bestNodeCost=openList.get(i).fCost;
                }
                //if F cost is equal, chcecked the G cost
                else if(openList.get(i).fCost==bestNodeCost){
                    if(openList.get(i).gCost<openList.get(bestNodeIndex).gCost){
                        bestNodeIndex=i;
                    }
                }
            }
            //if there is no  node in the openList, end
            if(openList.size()==0){
                break;
            }
            currentNode=openList.get(bestNodeIndex);
            if(currentNode==goalNode){
                goalReached=true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }
    public void  openNode(Node node){
        if(node.open==false&&node.checked==false&&node.solid==false){
            node.open=true;
            node.parent=currentNode;
            openList.add(node);
        }
    }
    public void trackThePath(){
        Node current=goalNode;
        while(current!=startNode){
            pathList.add(0,current);
            current=current.parent;
        }

    }
}
