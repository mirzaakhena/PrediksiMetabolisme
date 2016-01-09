package com.mirzaakhena.apps.dp;
import java.io.*;

class Alignment
	{
	private static int CHARACTERS_PER_ROW = 60;	// modify this to adjust the output
	protected Sequence seq1, seq2;
	protected GappedSequence gappedSeq1, gappedSeq2;
	protected String colons = "";
	protected String scorematrix;
	protected ScoreMatrix scoreMatrix;
	protected int alignmentScore = -88888888;

	    // the edit matrix and auxiliary matrices

	protected int[][] L, E, F;

	public Alignment(Sequence sequence1, Sequence sequence2, String matrix) throws ErrorInScoreMatrixException, ErrorInSequenceException
		{
		seq1 = sequence1;		
		seq2 = sequence2;		
		gappedSeq1 = new GappedSequence(seq1.getLength()+seq2.getLength());
		gappedSeq2 = new GappedSequence(seq1.getLength()+seq2.getLength());
		gappedSeq1.id = seq1.id.toString();
		gappedSeq2.id = seq2.id.toString();
		colons = new String();
		scorematrix = matrix;
		scoreMatrix = new ScoreMatrix();
		scoreMatrix.load(matrix);
		}

	public Alignment(Sequence sequence1, Sequence sequence2) throws ErrorInScoreMatrixException, ErrorInSequenceException
		{
		this (sequence1, sequence2, "pam250");
		}


	    // for a nonsense alignment, call this method:

	public void fillEditMatrix()
		{
		int i, j;
		int l1 = seq1.getLength() + 1;
		int l2 = seq2.getLength() + 1;

		// l1 and l2 are the sequence lengths + 1
		// and the edit matrix is an l1 x l2 - matrix:

		L = new int [l1][l2];
		}

	    // for a global alignment with linear gap costs, call this method:

	public void fillEditMatrix(int gapPenalty) throws ErrorInSequenceException, ErrorInScoreMatrixException
		{
		int i, j;
		int l1 = seq1.getLength() + 1;
		int l2 = seq2.getLength() + 1;

		// l1 and l2 are the sequence lengths + 1
		// and the edit matrix is an l1 x l2 - matrix:

		L = new int [l1][l2];

		    /**************************************************************** 
		     *****	                                             ********
		     *****	insert here the method,  		     ********
		     *****      which fills in the edit matrix               ********
		     *****	for a global alignment                       ********
		     *****	with linear gap costs                        ********
		     **************************************************************/
		
		// initialization of array L...

		L[0][0] = 0;
		}


	    // for a global alignment with linear affine gap costs, call this method

	public void fillEditMatrix(int openGapPenalty, int extensionGapPenalty) 
		{
		    /**************************************************************** 
		     *****	                                             ********
		     *****	insert here the method,  		     ********
		     *****      which fills in the edit matrix               ********
		     *****	for a local  alignment                       ********
		     *****	with linear affine gap costs                 ********
		     **************************************************************/
		}


							

	public void backTracking(int g) throws ErrorInScoreMatrixException, ErrorInSequenceException
		{
		int i = L.length - 1;
		int j = L[0].length - 1;

		     /*************************************************************** 
		     *****	this is a dummy backtracking,                ********
		     *****	CORRECT IT				     ********
		     *****                                                   ********
		     ***************************************************************/

		while (i>0 && j>0)
			{
			align(seq1.getLetter(i--), seq2.getLetter(j--));
			}
		if (i!=0)	while (i!=0)	align(seq1.getLetter(i--), '-');
		if (j!=0)	while (j!=0)	align('-', seq2.getLetter(j--));
		}


	public void align(char letter1, char letter2) throws ErrorInScoreMatrixException, ErrorInSequenceException
		{
		short sim;
		if (letter1 != '-' && letter2 != '-')	sim = scoreMatrix.sim(letter1, letter2);
		else	sim = -1;
		gappedSeq1.insLetter(letter1);
		gappedSeq2.insLetter(letter2);

	       	if (letter1==letter2)	colons = ':' + colons;
		else if (sim>=0)	colons = '.' + colons;
		else			colons = ' ' + colons;
		}
		

        public int getLength() { return(seq1.getLength()); }

	public void print() throws ErrorInSequenceException
		{
		int printedCharacters = 0;
		int length = gappedSeq1.getLength();

		System.out.println("\nAlignmentScore = " + alignmentScore); 

		while (printedCharacters < length)
			{
			String row1, row2, row3;
			row1=row2=row3="";
			for (int i=printedCharacters; i<length && (i-printedCharacters) < CHARACTERS_PER_ROW; i++)
				{
				row1 += gappedSeq1.getLetter(i+1); 
				row2 += colons.charAt(i);
				row3 += gappedSeq2.getLetter(i+1); 
				}
			row1 += "   ";
			row3 += "   ";
			row1 += gappedSeq1.id;
			row3 += gappedSeq2.id;
			printedCharacters += CHARACTERS_PER_ROW;
			System.out.print("\n" + row1 + "\n" + row2 + "\n" + row3 + "\n");
			}
	        }		


	protected int max(int a, int b, int c)
		{
		if (c>b && c>a)	return(c);
		else return((b>a)?b:a);
		}

	protected int max(int a, int b) { return((b>a)?b:a); }
	}

