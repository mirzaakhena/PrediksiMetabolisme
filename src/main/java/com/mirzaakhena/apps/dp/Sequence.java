package com.mirzaakhena.apps.dp;

import java.io.*;

class Sequence
	{
	protected int maxSeqLength = 10000;
	protected char [] letters;
	protected String id;
	protected int length;

	public Sequence()
		{
		letters	 = new char[maxSeqLength];
		id	 = new String();
		}

	public Sequence(int maxLength)
	        {
		maxSeqLength = maxLength;
		letters	 = new char[maxSeqLength];
		id	 = new String();
		}

	    // note: there are 26 letters accepted by insLetter(char letter)

	public void insLetter(char letter) throws ErrorInSequenceException
		{
		if (length < maxSeqLength)
			{
			if (letter >= 'A' && letter <= 'Z')	letters[length++] = letter;
			else throw new ErrorInSequenceException("ErrorInSequenceException: Illegal character '" +letter+ "' in the sequence " + id);
			}
		else throw new ErrorInSequenceException("ErrorInSequenceException: Sequence "+id+" is too long.\nmaxSeqLength = "+maxSeqLength);
		}

	  // the index of a sequence must be element of { 1, 2, ..., length_of_seq }

	public char getLetter(int index) throws ErrorInSequenceException
		{
		if (index <= getLength())	return(letters[index-1]);
		else throw new ErrorInSequenceException("ErrorInSequenceException: bad call to Sequence.getLetter(int index): index is out of bounds.");
		}

	public void printSequence() 
		{
		for (int i=0; i<length; i++)	System.out.print(letters[i]);
		System.out.println();
		}

	public void printID() {System.out.println(id);}
	public int getLength() {return (length);}
	}


