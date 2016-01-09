package com.mirzaakhena.apps.dp;

import java.io.*;


// all fields in the ScoreMatric class are static, as only one score matrix should be loaded

class ScoreMatrix implements ScoreLUT
	{
	private short[][] sym_matrix;
	private String name = "";		// the name of the matrix is only set in a load-method

	    // the constructors allocate memory for a triangular matrix

	public ScoreMatrix(int size)
		{
		sym_matrix = new short[size][];
		for (int i=0; i<size; i++)	sym_matrix[i] = new short[i+1];
		}

	    // allocate 26 x 26 (!) triangular array for amino acids

	public ScoreMatrix()
		{
		sym_matrix = new short[26][];
		for (int i=0; i<26; i++)	sym_matrix[i] = new short[i+1];
		}

	    // use this method for amino acids only to load a scorematrix of the 
	    // ScoreLUT class in the array sym_matrix[][], which has to be constructed
	    // with a size of 26.

	public void load(String matrixname) throws ErrorInScoreMatrixException
		{
		name = matrixname;
		
		if (sym_matrix.length != 26) 
			throw new ErrorInScoreMatrixException("Error in method load(String matrixname):\nThe matrix-array is not correctly constructed.");

		for (int i=0; i<26; i++)
			for (int j=0; j<=i; j++)
				sym_matrix[i][j] = lookup(matrixname, i, j);		
		}		

	    /*			
	public static void load(String directory, String filename)
		{
		}
	    */

	public boolean loaded() { return((name.equals(""))?false:true); }

	public String name() { return(name); }

	public short sim(char letter1, char letter2) throws ErrorInScoreMatrixException, ArrayIndexOutOfBoundsException
		{
		if (name.equals("")) throw new ErrorInScoreMatrixException("ErrorInScoreMatrixException: error in method sim:\ncannot return similarity value since there is no scorematrix loaded yet.");

		if (letter1<letter2) 
			{
			char buff = letter1;
			letter1=letter2;
			letter2=buff;
			}
		return(sym_matrix[letter1-'A'][letter2-'A']);
		}



	public short lookup(String matrixname, int letter1, int letter2) throws ErrorInScoreMatrixException
		{
		if (matrixname.equalsIgnoreCase("pam250")) return (lookup(PAM250, letter1, letter2));
		if (matrixname.equalsIgnoreCase("blosum62")) return (lookup(BLOSUM62, letter1, letter2));
		else throw new ErrorInScoreMatrixException("ErrorInScoreMatrixException: cannot find the ScoreMatrix " +matrixname+ ".");
		}

	    /* hier muss noch (mehr) overloaded werden


	public static short lookup(short matrix[][], char letter1, char letter2)

	    */

	private short lookup(short[][] scorematrix, int letter1, int letter2)
		{
		int index1 = AMINO_ACIDS.indexOf(letter1+'A');
		int index2 = AMINO_ACIDS.indexOf(letter2+'A');

		if (index1==-1 || index2==-1)	return(0);
		return((index1>index2)?scorematrix[index1][index2]:scorematrix[index2][index1]);
		}
	}
		

// the following interface provides some constants:

interface ScoreLUT 
	{			
	static final String AMINO_ACIDS = "ARNDCQEGHILKMFPSTWYVBZX";
      
	static final short[][] PAM250 = {
		{ 2},
		{-2, 6},
		{ 0, 0, 2},
		{ 0,-1, 2, 4},
		{-2,-4,-4,-5,12},
		{ 0, 1, 1, 2,-5, 4},
		{ 0,-1, 1, 3,-5, 2, 4},
		{ 1,-3, 0, 1,-3,-1, 0, 5},
		{-1, 2, 2, 1,-3, 3, 1,-2, 6},
		{-1,-2,-2,-2,-2,-2,-2,-3,-2, 5},
		{-2,-3,-3,-4,-6,-2,-3,-4,-2, 2, 6},
		{-1, 3, 1, 0,-5, 1, 0,-2, 0,-2,-3, 5},
		{-1, 0,-2,-3,-5,-1,-2,-3,-2, 2, 4, 0, 6},
		{-3,-4,-3,-6,-4,-5,-5,-5,-2, 1, 2,-5, 0, 9},
		{ 1, 0, 0,-1,-3, 0,-1, 0, 0,-2,-3,-1,-2,-5, 6},
		{ 1, 0, 1, 0, 0,-1, 0, 1,-1,-1,-3, 0,-2,-3, 1, 2},
		{ 1,-1, 0, 0,-2,-1, 0, 0,-1, 0,-2, 0,-1,-3, 0, 1, 3},
		{-6, 2,-4,-7,-8,-5,-7,-7,-3,-5,-2,-3,-4, 0,-6,-2,-5,17},
		{-3,-4,-2,-4, 0,-4,-4,-5, 0,-1,-1,-4,-2, 7,-5,-3,-3, 0,10},
		{ 0,-2,-2,-2,-2,-2,-2,-1,-2, 4, 2,-2, 2,-1,-1,-1, 0,-6,-2, 4},
		{ 0,-1, 2, 3,-4, 1, 3, 0, 1,-2,-3, 1,-2,-4,-1, 0, 0,-5,-3,-2, 3},
		{ 0, 0, 1, 3,-5, 3, 3, 0, 2,-2,-3, 0,-2,-5, 0, 0,-1,-6,-4,-2, 2, 3},
		{ 0,-1, 0,-1,-3,-1,-1,-1,-1,-1,-1,-1,-1,-2,-1, 0, 0,-4,-2,-1,-1,-1,-1}
		};


	static final short[][] BLOSUM62 = {
		{ 4},
		{-1, 5},
		{-2, 0, 6},
		{-2,-2, 1, 6},
		{ 0,-3,-3,-3, 9},
		{-1, 1, 0, 0,-3, 5},
		{-1, 0, 0, 2,-4, 2, 5},
		{ 0,-2, 0,-1,-3,-2,-2, 6},
		{-2, 0, 1,-1,-3, 0, 0,-2, 8},
		{-1,-3,-3,-3,-1,-3,-3,-4,-3, 4},
		{-1,-2,-3,-4,-1,-2,-3,-4,-3, 2, 4},
		{-1, 2, 0,-1,-3, 1, 1,-2,-1,-3,-2, 5},
		{-1,-1,-2,-3,-1, 0,-2,-3,-2, 1, 2,-1, 5},
		{-2,-3,-3,-3,-2,-3,-3,-3,-1, 0, 0,-3, 0, 6},
		{-1,-2,-2,-1,-3,-1,-1,-2,-2,-3,-3,-1,-2,-4, 7},
		{ 1,-1, 1, 0,-1, 0, 0, 0,-1,-2,-2, 0,-1,-2,-1, 4},
		{ 0,-1, 0,-1,-1,-1,-1,-2,-2,-1,-1,-1,-1,-2,-1, 1, 5},
		{-3,-3,-4,-4,-2,-2,-3,-2,-2,-3,-2,-3,-1, 1,-4,-3,-2,11},
		{-2,-2,-2,-3,-2,-1,-2,-3, 2,-1,-1,-2,-1, 3,-3,-2,-2, 2, 7},
		{ 0,-3,-3,-3,-1,-2,-2,-3,-3, 3, 1,-2, 1,-1,-2,-2, 0,-3,-1, 4},
		{-2,-1, 3, 4,-3, 0, 1,-1, 0,-3,-4, 0,-3,-3,-2, 0,-1,-4,-3,-3, 4},
		{-1, 0, 0, 1,-3, 3, 4,-2, 0,-3,-3, 1,-1,-3,-1, 0,-1,-3,-2,-2, 1, 4},
		{ 0,-1,-1,-1,-2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-2, 0, 0,-2,-1,-1,-1,-1,-1}
		};	
	}


