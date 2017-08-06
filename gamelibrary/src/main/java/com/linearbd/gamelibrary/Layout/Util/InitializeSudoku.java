package com.linearbd.gamelibrary.Layout.Util;


import com.linearbd.gamelibrary.Layout.Model.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Genius 03 on 8/5/2017.
 */

public class InitializeSudoku implements Runnable {
    private List<Element> elementList;
    private int level;
    private Random random;
    private Thread thread;

    private int[][] puzzle =
                    {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                    {6, 7, 2, 1, 9, 5, 3, 4, 8},
                    {1, 9, 8, 3, 4, 2, 5, 6, 7},
                    {8, 5, 9, 7, 6, 1, 4, 2, 3},
                    {4, 2, 6, 8, 5, 3, 7, 9, 1},
                    {7, 1, 3, 9, 2, 4, 8, 5, 6},
                    {9, 6, 1, 5, 3, 7, 2, 8, 4},
                    {2, 8, 7, 4, 1, 9, 6, 3, 5},
                    {3, 4, 5, 2, 8, 6, 1, 7, 9}};

    public InitializeSudoku(List<Element> elementList, int level) {
        this.elementList = elementList;
        this.level = level;
        this.random = new Random();

        thread = new Thread(this);
        thread.start();

    }

    public void initBoard(){
        int i=0;
        int rowId=-1;
        int colId=-1;

        // Interchange the Board
        interChange();

        while (i<level){
            rowId=random.nextInt(9);
            colId=random.nextInt(9);

            if(i==0){
                Element element = getElementByRowandColumnId(rowId,colId);
                element.setValue(String.valueOf(puzzle[rowId][colId]));

            }else{

                if(hasValue(rowId,colId)){
                    continue;
                }

                Element element = getElementByRowandColumnId(rowId,colId);
                element.setValue(String.valueOf(puzzle[rowId][colId]));
                //setValue(element);

            }
            i++;

        }

    }

    public void interChange(){
        int rowChangeCounter=0;
        int colChangeCounter=0;
        int rowId=random.nextInt(9);
        int colId=random.nextInt(9);

        // Row Change Two Times
        while (rowChangeCounter<2){
            validRowChange(rowId);
            rowId=random.nextInt(9);
            rowChangeCounter++;
        }

        // Column Change Change Two Times
        while (colChangeCounter<2){
            validColChange(colId);
            colId=random.nextInt(9);
            colChangeCounter++;
        }
    }

    private void validRowChange(int rowId){
        int interChangeId=-1;

        if(rowId<=2){

            if(rowId==2){
                interChangeId=rowId-1;
            }else{
                interChangeId=rowId+1;
            }

        }else if(rowId<=5){
            if(rowId==5){
                interChangeId=rowId-1;
            }else{
                interChangeId=rowId+1;
            }
        }else{
            if(rowId==8){
                interChangeId=rowId-1;
            }else{
                interChangeId=rowId+1;
            }
        }

        rowInterchange(rowId,interChangeId);
    }

    private void validColChange(int colId){
        int interChangeId=-1;

        if(colId<=2){

            if(colId==2){
                interChangeId=colId-1;
            }else{
                interChangeId=colId+1;
            }

        }else if(colId<=5){
            if(colId==5){
                interChangeId=colId-1;
            }else{
                interChangeId=colId+1;
            }
        }else{
            if(colId==8){
                interChangeId=colId-1;
            }else{
                interChangeId=colId+1;
            }
        }

        colInterchange(colId,interChangeId);
    }


    private void setValue(Element element){
        int initialValue= random.nextInt(9)+1;
        boolean loopStarter= true;

        while (loopStarter){
            if(isValueAlreadyInTheBlock(element,initialValue) || isValueAlreadyInTheRow(element,initialValue) || isValueAlreadyInTheColumn(element,initialValue)){
                initialValue=(initialValue+1);
                if(initialValue!=9){
                    initialValue=initialValue%9;
                }
                continue;
            }
            element.setValue(String.valueOf(initialValue));
            loopStarter=false;
        }
    }

    private boolean isValueAlreadyInTheBlock(Element element,int value){
        boolean retBool = false;
        List<Element> blockElementList = getBlockList(element.getBlockId());

        for(Element x:blockElementList){
            if(x.getValue().equals(String.valueOf(value))){
                retBool=true;
                break;
            }
        }

        return retBool;
    }
    private boolean isValueAlreadyInTheRow(Element element,int value){
        boolean retBool = false;
        List<Element> rowElementList = getRowList(element.getRowId());

        for(Element x:rowElementList){
            if(x.getValue().equals(String.valueOf(value))){
                retBool=true;
                break;
            }
        }

        return retBool;
    }
    private boolean isValueAlreadyInTheColumn(Element element,int value){
        boolean retBool = false;
        List<Element> columnElementList = getColumnList(element.getColumnId());

        for(Element x:columnElementList){
            if(x.getValue().equals(String.valueOf(value))){
                retBool=true;
                break;
            }
        }

        return retBool;
    }

    private List<Element> getBlockList(int blockId){
        List<Element> retList = new ArrayList<>();
        int counter=0;

        for(Element x:elementList){
            if(x.getBlockId()==blockId){
                retList.add(x);
                counter++;
                if(counter==9){
                    break;
                }
            }
        }

        return retList;
    }
    private List<Element> getRowList(int rowId){
        List<Element> retList = new ArrayList<>();
        int counter=0;

        for(Element x:elementList){
            if(x.getRowId()==rowId){
                retList.add(x);
                counter++;
                if(counter==9){
                    break;
                }
            }
        }

        return retList;
    }
    private List<Element> getColumnList(int colId){
        List<Element> retList = new ArrayList<>();
        int counter=0;

        for(Element x:elementList){
            if(x.getColumnId()==colId){
                retList.add(x);
                counter++;
                if(counter==9){
                    break;
                }
            }
        }

        return retList;
    }

    private boolean hasValue(int rowId,int colId){
        boolean retBool= false;
        Element element = getElementByRowandColumnId(rowId,colId);
        if(!element.getValue().equals("")){
            retBool=true;
        }

        return retBool;
    }

    private Element getElementByRowandColumnId(int rowId,int columnId){
        Element element=null;
        for(Element x:elementList){
            if(x.getRowId()==rowId && x.getColumnId()==columnId){
                element=x;
                break;
            }
        }
        return element;
    }


    private int[] getRowById(int rowIndex){

        int[] row = new int[9];

        for (int i=0;i<puzzle[rowIndex].length;i++){
            row[i]=puzzle[rowIndex][i];
        }

        return row;

    }

    public void rowInterchange(int a, int b){
        int[] aVal=getRowById(a);
        int[] bVal=getRowById(b);

        for(int i=0;i<puzzle.length;i++){
            if(i==a){
                puzzle[i]=bVal;
            }

            if(i==b){
                puzzle[i]=aVal;
            }
        }

    }

    public int[] getColumnById(int colIndex){
        int[] col = new int[9];

        for (int i=0;i<puzzle[colIndex].length;i++){
            col[i]=puzzle[i][colIndex];
            //System.out.print(puzzle[i][colIndex]+" ");
        }

        return col;
    }

    public void colInterchange(int a,int b){
        int[] aVal=getColumnById(a);
        int[] bVal=getColumnById(b);

        for(int i=0;i<aVal.length;i++){
            puzzle[i][a]=bVal[i];
        }

        for(int i=0;i<bVal.length;i++){
            puzzle[i][b]=aVal[i];
        }
    }

    public void printArray(){
        for(int i=0;i<puzzle.length;i++){

            for(int j=0;j<puzzle[i].length;j++){
                System.out.print(puzzle[i][j]+" ");
            }
            System.out.println();
        }
    }

    @Override
    public void run() {
        initBoard();
    }
}
