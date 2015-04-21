package com.anioutkazharkova.gameoflifecanvas;

import java.util.Random;

public class LifeModel
{
    // состояния клетки
    private static final Byte CELL_ALIVE = 1; // клетка жива
    private static final Byte CELL_DEAD = 0; // клетки нет
    
    // константы для количества соседей
    private static final Byte NEIGHBOURS_MIN = 2; // минимальное число соседей для живой клетки
    private static final Byte NEIGHBOURS_MAX = 3; // максимальное число соседей для живой клетки
    private static final Byte NEIGHBOURS_BORN = 3; // необходимое число соседей для рождения клетки
    
    private static int mCols; // количество столбцов на карте
    private static int mRows; // количество строк на карте
    private Byte[][] mCells; // расположение очередного поколения на карте. 
                            //Каждая ячейка может содержать либо CELL_ACTIVE, либо CELL_DEAD
    
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
     * Инициализация первого поколения случайным образом
     * @param cellsNumber количество клеток в первом поколении
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
     * Переход к следующему поколению
     */
    public void next()
    {
    	alives=0;
    	dead=0;
        Byte[][] tmp = new Byte[mRows][mCols];
        
        // цикл по всем клеткам
        for (int i = 0; i < mRows; ++i)
            for (int j = 0; j < mCols; ++j)
            {
                // вычисляем количество соседей для каждой клетки
                int n = 
                    itemAt(i-1, j-1) + itemAt(i-1, j) + itemAt(i-1, j+1) +
                    itemAt(i, j-1) + itemAt(i, j+1) +
                    itemAt(i+1, j-1) + itemAt(i+1, j) + itemAt(i+1, j+1);
                
                tmp[i][j] = mCells[i][j];
                if (isCellAlive(i, j))
                {
                    // если клетка жива, а соседей у нее недостаточно или слишком много, клетка умирает
                    if (n < NEIGHBOURS_MIN || n > NEIGHBOURS_MAX)
                        {tmp[i][j] = CELL_DEAD;
                        dead+=1;
                        }
                }
                else
                {
                    // если у пустой клетки ровно столько соседей, сколько нужно, она оживает 
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
     * @return Размер поля
     */
    public int getCount()
    {
        return mCols * mRows;
    }
    
    /**
     * @param row Номер строки
     * @param col Номер столбца
     * @return Значение ячейки, находящейся в указанной строке и указанном столбце
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
     * @param row Номер строки
     * @param col Номер столбца
     * @return Жива ли клетка, находящаяся в указанной строке и указанном столбце
     */
    public Boolean isCellAlive(int row, int col)
    {
        return itemAt(row, col) == CELL_ALIVE;
    }

    /**
     * @param position Позиция (для клетки [row, col], вычисляется как row * mCols + col)
     * @return Жива ли клетка, находящаяся в указанной позиции
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