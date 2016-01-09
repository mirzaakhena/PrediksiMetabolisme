package com.mirzaakhena.apps.dp;

import java.io.*;

public class Align
	{
        private static FastaSequence seq1 = new FastaSequence();
        private static FastaSequence seq2 = new FastaSequence();
	private static String filename = new String("");	
	private static String scorematrix = new String("pam250");	
	private static int openGapPenalty = -12;
	private static int extensionGapPenalty = -3;
	private static Alignment alignment;


	public static void main(String[] argv)
		{
		int a = argv.length;

		try
	        {
		    // read some command-line arguments

		if (a>4 || a==0)	Usage();
		if (a==4)	extensionGapPenalty = Integer.parseInt(argv[3]);
		if (a>=3)	openGapPenalty = Integer.parseInt(argv[2]);
		if (a>=2)	scorematrix = argv[1];
		if (a>=1)	filename = argv[0];

		   // read the first two sequences of the specified fasta-file		

      		seq1.readFasta(filename,1);
		seq2.readFasta(filename,2);

		   // create the Alignment-object alignment				       

		alignment = new Alignment(seq1, seq2, scorematrix);

		   // call method fillEditMatrix() of object alignment

		alignment.fillEditMatrix();

		/* for a global alignment with 
		i) linear, 
		ii) linear affine gap costs, call	*/
	        //   alignment.fillEditMatrix(openGapPenalty);	
	        //   alignment.fillEditMatrix(openGapPenalty, extGapPenalty);	
		 

		   // call method backTracking() of object alignment

		alignment.backTracking(openGapPenalty);

		   // print alignment 

		alignment.print();
		}



		catch (NumberFormatException e) {System.out.println(e.getMessage() + " is not an integer.\n");Usage();}
		catch (ErrorInSequenceException e)	{System.out.println(e.getMessage());}
		catch (ErrorInScoreMatrixException e)	{System.out.println(e.getMessage());}
    		catch (FileNotFoundException e)	{System.out.println("File not found: " + e.getMessage());}
      		catch (IOException e){System.out.println("IOException: " + e.getMessage());}
		catch (ArrayIndexOutOfBoundsException e){System.out.println("ArrayIndexOutOfBoundsException: " + e.getMessage());}
		}

	public static void Usage()
		{
		System.out.println("usage: java Align <fasta-file with two protein sequences>");
		System.out.println("\t\t[scoring matrix (default = pam250)]");
		System.out.println("\t\t[opening gap penalty (default = 12)]");
		System.out.println("\t\t[extending gap penalty (default = 3)]");
		System.exit(1);
		}
	}







		
