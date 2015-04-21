package com.anioutkazharkova.gameoflifecanvas;

import java.util.Random;

public class LifeModel
{
    // ��������� ������
    private static final Byte CELL_ALIVE = 1; // ������ ����
    private static final Byte CELL_DEAD = 0; // ������ ���
    
    // ��������� ��� ���������� �������
    private static final Byte NEIGHBOURS_MIN = 2; // ����������� ����� ������� ��� ����� ������
    private static final Byte NEIGHBOURS_MAX = 3; // ������������ ����� ������� ��� ����� ������
    private static final Byte NEIGHBOURS_BORN = 3; // ����������� ����� ������� ��� �������� ������
    
    private static int mCols; // ���������� �������� �� �����
    private static int mRows; // ���������� ����� �� �����
    private Byte[][] mCells; // ������������ ���������� ��������� �� �����. 
                            //������ ������ ����� ��������� ���� CELL_ACTIVE, ���� CELL_DEAD
    
    int alives=0;
    int dead=0;
	private int mCellsNumber=0;
    
    public LifeModel(int rows, int cols, int cellsNumber)
    {
        mCols = cols;
        mRows = rows;
        mCells = new Byte[mRows][mCols];
        mCellsNumber=Math.min(cellsNumber,mRows*mCols);
        initValues(mCellsNumber);
    }
    
    /**
     * ������������� ������� ��������� ��������� �������
     * @param cellsNumber ���������� ������ � ������ ���������
     */
    private void initValues(int cellsNumber)
    {
        for (int i = 0; i < mRows; ++i)
            for (int j = 0; j < mCols; ++j)
                mCells[i][j] = CELL_DEAD;
        
        Random rnd = new Random(System.currentTimeMillis());
        for (int i = 0; i < cellsNumber; i++)
        {
            int cc=0;
            int cr=0;
            do
            {
                cc = rnd.nextInt(mCols-1);
                cr = rnd.nextInt(mRows-1);
            }
            while (isCellAlive(cr, cc));
            mCells[cr][cc] = CELL_ALIVE;       
        }
    }
    
    /**
     * ������� � ���������� ���������
     */
    public void next()
    {
    	alives=0;
    	dead=0;
        Byte[][] tmp = new Byte[mRows][mCols];
        
        // ���� �� ���� �������
        for (int i = 0; i < mRows; ++i)
            for (int j = 0; j < mCols; ++j)
            {
                // ��������� ���������� ������� ��� ������ ������
                int n = 
                    itemAt(i-1, j-1) + itemAt(i-1, j) + itemAt(i-1, j+1) +
                    itemAt(i, j-1) + itemAt(i, j+1) +
                    itemAt(i+1, j-1) + itemAt(i+1, j) + itemAt(i+1, j+1);
                
                tmp[i][j] = mCells[i][j];
                if (isCellAlive(i, j))
                {
                    // ���� ������ ����, � ������� � ��� ������������ ��� ������� �����, ������ �������
                    if (n < NEIGHBOURS_MIN || n > NEIGHBOURS_MAX)
                        {tmp[i][j] = CELL_DEAD;
                        dead+=1;
                        }
                }
                else
                {
                    // ���� � ������ ������ ����� ������� �������, ������� �����, ��� ������� 
                    if (n == NEIGHBOURS_BORN)
                        {tmp[i][j] = CELL_ALIVE;    
                        alives+=1;
                        }
                }
            }
        mCells = tmp;
        if (alives==0)
        {
        	initValues(new Random().nextInt(mCellsNumber));
        }
    }
    
    /**
     * @return ������ ����
     */
    public int getCount()
    {
        return mCols * mRows;
    }
    
    /**
     * @param row ����� ������
     * @param col ����� �������
     * @return �������� ������, ����������� � ��������� ������ � ��������� �������
     */
    private Byte itemAt(int row, int col)
    {
    	try{
        if (row < 0 || row >= mRows || col < 0 || col >= mCols)
            return 0;
        else   
        return mCells[row][col];
    	}
    	catch(Exception e)
    	{
    		return 0;
    	}
    }
    
    /**
     * @param row ����� ������
     * @param col ����� �������
     * @return ���� �� ������, ����������� � ��������� ������ � ��������� �������
     */
    public Boolean isCellAlive(int row, int col)
    {
        return itemAt(row, col) == CELL_ALIVE;
    }

    /**
     * @param position ������� (��� ������ [row, col], ����������� ��� row * mCols + col)
     * @return ���� �� ������, ����������� � ��������� �������
     */
    public Boolean isCellAlive(int position)
    {
        int r = position / mCols;
        int c = position % mCols;

        return isCellAlive(r,c);
    }
    public void changeAlive(int position)
    {
    	 int r = position / mCols;
         int c = position % mCols;
         mCells[r][c]=((itemAt(r,c)==CELL_ALIVE)?CELL_DEAD:CELL_ALIVE);
        // return itemAt(r, c) == CELL_ALIVE;
    }
}